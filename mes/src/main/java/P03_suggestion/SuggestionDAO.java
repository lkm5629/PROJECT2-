package P03_suggestion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import P03_suggestion.SuggestionDTO;

public class SuggestionDAO {

    private Connection getConn() {
        Connection conn = null;
        try {
            Context ctx = new InitialContext();
            DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
            conn = dataFactory.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    // 전체 건수 조회
    public int selectTotal() {
        int totalCount = 0;
        String sql = "SELECT COUNT(*) cnt FROM suggestion";
        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) totalCount = rs.getInt("cnt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalCount;
    }

    // 목록 조회 (페이지네이션)
    public List<SuggestionDTO> selectList(SuggestionDTO dto) {
        List<SuggestionDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM ("
                   + "  SELECT rownum AS rnum, s.* FROM ("
                   + "    SELECT boardno, title, ctime, emp_id, complete"
                   + "    FROM suggestion"
                   + "    ORDER BY boardno DESC"
                   + "  ) s"
                   + ") WHERE rnum >= ? AND rnum <= ?";
        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, dto.getStart());
            ps.setInt(2, dto.getEnd());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SuggestionDTO row = new SuggestionDTO();
                    row.setBoardno(rs.getString("boardno"));
                    row.setTitle(rs.getString("title"));
                    row.setCtime(rs.getDate("ctime"));
                    row.setEmpId(rs.getString("emp_id"));
                    row.setComplete(rs.getInt("complete"));
                    list.add(row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 단건 조회 (user_info JOIN → ename 포함)
    public SuggestionDTO selectOne(String boardno) {
        SuggestionDTO dto = null;
        String sql = "SELECT s.boardno, s.title, s.content, s.ctime, s.mtime,"
                   + "       s.views, s.emp_id, s.complete, u.ename"
                   + " FROM suggestion s"
                   + " LEFT JOIN user_info u ON s.emp_id = u.emp_id"
                   + " WHERE s.boardno = ?";
        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, boardno);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dto = new SuggestionDTO();
                    dto.setBoardno(rs.getString("boardno"));
                    dto.setTitle(rs.getString("title"));
                    dto.setContent(rs.getString("content"));
                    dto.setCtime(rs.getDate("ctime"));
                    dto.setMtime(rs.getDate("mtime"));
                    dto.setViews(rs.getInt("views"));
                    dto.setEmpId(rs.getString("emp_id"));
                    dto.setComplete(rs.getInt("complete"));
                    dto.setEname(rs.getString("ename"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }

    // 조회수 +1
    public void updateViews(String boardno) {
        String sql = "UPDATE suggestion SET views = views + 1 WHERE boardno = ?";
        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, boardno);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 답변완료 처리 (complete 0 → 1)
    public int updateComplete(String boardno) {
        int result = 0;
        String sql = "UPDATE suggestion SET complete = 1, mtime = SYSDATE WHERE boardno = ?";
        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, boardno);
            result = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 등록
    public int insert(SuggestionDTO dto) {
        int result = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConn();
            conn.setAutoCommit(false);

            // boardno 채번
            ps = conn.prepareStatement(
                "SELECT 'sugg_' || sugg_seq.nextval AS boardno FROM dual"
            );
            rs = ps.executeQuery();
            String boardno = null;
            if (rs.next()) boardno = rs.getString("boardno");
            rs.close();
            ps.close();

            // INSERT
            ps = conn.prepareStatement(
                "INSERT INTO suggestion (boardno, title, content, ctime, mtime, views, emp_id, complete, deleted)"
              + " VALUES (?, ?, ?, SYSDATE, SYSDATE, 0, ?, 0, 'N')"
            );
            ps.setString(1, boardno);
            ps.setString(2, dto.getTitle());
            ps.setString(3, dto.getContent());
            ps.setString(4, dto.getEmpId());

            result = ps.executeUpdate();
            conn.commit();

        } catch (Exception e) {
            try { if (conn != null) conn.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } finally {
            try { if (rs   != null) rs.close();   } catch (Exception e) { e.printStackTrace(); }
            try { if (ps   != null) ps.close();   } catch (Exception e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return result;
    }

    // 삭제
    public int delete(String boardno) {
        int result = 0;
        String sql = "DELETE FROM suggestion WHERE boardno = ?";
        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, boardno);
            result = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 댓글 등록
    public int insertComment(String boardno, String content) {
        int result = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConn();
            conn.setAutoCommit(false);

            // comno 채번
            ps = conn.prepareStatement(
                "SELECT 'cmt_' || comment_seq.nextval AS comno FROM dual"
            );
            rs = ps.executeQuery();
            String comno = null;
            if (rs.next()) comno = rs.getString("comno");
            rs.close();
            ps.close();

            // INSERT — ctime/mtime: MM-DD HH24:MI 형식
            ps = conn.prepareStatement(
                "INSERT INTO comment_info (comno, content, ctime, mtime, boardno)"
              + " VALUES (?, ?, TO_DATE(TO_CHAR(SYSDATE, 'MM-DD HH24:MI'), 'MM-DD HH24:MI'),"
              + "         TO_DATE(TO_CHAR(SYSDATE, 'MM-DD HH24:MI'), 'MM-DD HH24:MI'), ?)"
            );
            ps.setString(1, comno);
            ps.setString(2, content);
            ps.setString(3, boardno);

            result = ps.executeUpdate();
            conn.commit();

        } catch (Exception e) {
            try { if (conn != null) conn.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } finally {
            try { if (rs   != null) rs.close();   } catch (Exception e) { e.printStackTrace(); }
            try { if (ps   != null) ps.close();   } catch (Exception e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return result;
    }
}