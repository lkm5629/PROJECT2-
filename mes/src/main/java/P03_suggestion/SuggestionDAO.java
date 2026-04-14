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

    // ── 커넥션 풀 ─────────────────────────────────────────────────────────
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

    // ── 전체 건수 조회 ────────────────────────────────────────────────────
    public int selectTotal() {
        int totalCount = 0;

        String sql = "SELECT COUNT(*) cnt FROM suggestion";

        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                totalCount = rs.getInt("cnt");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalCount;
    }

    // ── 목록 조회 (페이지네이션) ──────────────────────────────────────────
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
                    row.setBoardno(rs.getInt("boardno"));
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

    // ── 단건 조회 ─────────────────────────────────────────────────────────
    public SuggestionDTO selectOne(int boardno) {
        SuggestionDTO dto = null;

        String sql = "SELECT * FROM suggestion WHERE boardno = ?";

        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, boardno);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dto = new SuggestionDTO();
                    dto.setBoardno(rs.getInt("boardno"));
                    dto.setTitle(rs.getString("title"));
                    dto.setContent(rs.getString("content"));
                    dto.setCtime(rs.getDate("ctime"));
                    dto.setMtime(rs.getDate("mtime"));
                    dto.setViews(rs.getInt("views"));
                    dto.setEmpId(rs.getString("emp_id"));
                    dto.setComplete(rs.getInt("complete"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }

    // ── 조회수 +1 ─────────────────────────────────────────────────────────
    public void updateViews(int boardno) {
        String sql = "UPDATE suggestion SET views = views + 1 WHERE boardno = ?";

        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, boardno);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ── 등록 ─────────────────────────────────────────────────────────────
    // boardno는 시퀀스(SEQ_SUGGESTION) 자동 생성 — 실제 시퀀스명으로 변경하세요
    public int insert(SuggestionDTO dto) {
        int result = 0;

        String sql = "INSERT INTO suggestion (boardno, title, content, ctime, mtime, views, emp_id, complete)"
                   + " VALUES (SEQ_SUGGESTION.NEXTVAL, ?, ?, SYSDATE, SYSDATE, 0, ?, 0)";

        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dto.getTitle());
            ps.setString(2, dto.getContent());
            ps.setString(3, dto.getEmpId());

            result = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // ── 삭제 ─────────────────────────────────────────────────────────────
    public int delete(int boardno) {
        int result = 0;

        String sql = "DELETE FROM suggestion WHERE boardno = ?";

        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, boardno);
            result = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}