package P03_suggestion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

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

    public List<SuggestionDTO> selectList(SuggestionDTO dto) {
        List<SuggestionDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM ("
                   + "  SELECT rownum AS rnum, s.* FROM ("
                   + "    SELECT s.boardno, s.title, s.ctime, s.emp_id, s.complete, s.views, u.ename"
                   + "    FROM suggestion s"
                   + "    LEFT JOIN user_info u ON s.emp_id = u.emp_id"
                   + "    ORDER BY s.boardno DESC"
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
                    row.setViews(rs.getInt("views"));
                    row.setEname(rs.getString("ename"));
                    list.add(row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public SuggestionDTO selectOne(String boardno) {
        SuggestionDTO dto = null;
        String sql = "SELECT s.boardno, s.title, s.content, s.ctime, s.mtime,"
                   + "       s.views, s.emp_id, s.complete, s.origin_name, s.save_name, u.ename"
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
                    dto.setOriginName(rs.getString("origin_name"));
                    dto.setSaveName(rs.getString("save_name"));
                    dto.setEname(rs.getString("ename"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }

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

    public int insert(SuggestionDTO dto) {
        int result = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConn();
            conn.setAutoCommit(false);

            ps = conn.prepareStatement(
                "SELECT 'sugg_' || sugg_seq.nextval AS boardno FROM dual"
            );
            rs = ps.executeQuery();
            String boardno = null;
            if (rs.next()) boardno = rs.getString("boardno");
            rs.close();
            ps.close();

            ps = conn.prepareStatement(
                "INSERT INTO suggestion"
              + " (boardno, title, content, ctime, mtime, views, emp_id, complete, deleted, origin_name, save_name)"
              + " VALUES (?, ?, ?, SYSDATE, SYSDATE, 0, ?, 0, 'N', ?, ?)"
            );
            ps.setString(1, boardno);
            ps.setString(2, dto.getTitle());
            ps.setString(3, dto.getContent());
            ps.setString(4, dto.getEmpId());
            ps.setString(5, dto.getOriginName());
            ps.setString(6, dto.getSaveName());

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

    // ── 댓글 목록 조회 (CONNECT BY 계층 순서) ──────────────────────────────
    public List<CommentDTO> selectCommentList(String boardno) {
        List<CommentDTO> list = new ArrayList<>();

        // START WITH parent_comno IS NULL  → 루트 댓글(원댓글)부터 시작
        // CONNECT BY PRIOR comno = parent_comno  → 부모→자식 방향으로 재귀
        // ORDER SIBLINGS BY ctime  → 같은 부모를 가진 형제 댓글끼리 시간순 정렬
        // LEVEL - 1  → Oracle 의사컬럼 LEVEL은 1부터 시작하므로 -1 해서 depth로 사용
        String sql =
            "SELECT " +
            "    comno, " +
            "    parent_comno, " +
            "    content, " +
            "    ctime, " +
            "    boardno, " +
            "    LEVEL - 1 AS depth " +
            "FROM comment_info " +
            "WHERE boardno = ? " +
            "START WITH parent_comno IS NULL " +
            "CONNECT BY PRIOR comno = parent_comno " +
            "ORDER SIBLINGS BY ctime";

        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, boardno);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CommentDTO dto = new CommentDTO();
                    dto.setComno(rs.getString("comno"));
                    dto.setParentComno(rs.getString("parent_comno"));
                    dto.setContent(rs.getString("content"));
                    dto.setCtime(rs.getDate("ctime"));
                    dto.setBoardno(rs.getString("boardno"));
                    dto.setDepth(rs.getInt("depth"));
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ── 댓글 등록 (parentComno 추가) ──────────────────────────────────────
    public int insertComment(String boardno, String content, String parentComno) {
        int result = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConn();
            conn.setAutoCommit(false);

            ps = conn.prepareStatement(
                "SELECT 'cmt_' || comment_seq.nextval AS comno FROM dual"
            );
            rs = ps.executeQuery();
            String comno = null;
            if (rs.next()) comno = rs.getString("comno");
            rs.close();
            ps.close();

            ps = conn.prepareStatement(
                "INSERT INTO comment_info " +
                "    (comno, content, ctime, mtime, boardno, parent_comno) " +
                "VALUES (?, ?, SYSDATE, SYSDATE, ?, ?)"
            );
            ps.setString(1, comno);
            ps.setString(2, content);
            ps.setString(3, boardno);
            ps.setString(4, parentComno); // 원댓글이면 null, 대댓글이면 부모 comno

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