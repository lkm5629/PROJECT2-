package P03_notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class NoticeDAO {

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

    // ── 목록 조회 (페이지네이션 + 제목 검색) ──────
    // ③ user_info JOIN해서 ename 가져오기
    public List<NoticeDTO> selectAllNotice(NoticeDTO noticeDTO) {
        List<NoticeDTO> list = new ArrayList<>();

        String keyword = noticeDTO.getKeyword();
        boolean hasKeyword = (keyword != null && !keyword.trim().isEmpty());

        String sql = "SELECT * FROM ("
                   + "  SELECT rownum AS rnum, n.* FROM ("
                   + "    SELECT a.boardno, a.title, a.content, a.ctime, a.mtime, a.views,"
                   + "           a.emp_id, u.ename"
                   + "    FROM announcement a"
                   + "    LEFT JOIN user_info u ON a.emp_id = u.emp_id"
                   + (hasKeyword ? " WHERE LOWER(a.title) LIKE LOWER(?)" : "")
                   + "    ORDER BY a.ctime DESC"
                   + "  ) n"
                   + ") WHERE rnum >= ? AND rnum <= ?";

        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int idx = 1;
            if (hasKeyword) {
                ps.setString(idx++, "%" + keyword.trim() + "%");
            }
            ps.setInt(idx++, noticeDTO.getStart());
            ps.setInt(idx,   noticeDTO.getEnd());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    NoticeDTO dto = new NoticeDTO();
                    dto.setBoardno( rs.getString("boardno") );
                    dto.setTitle(   rs.getString("title") );
                    dto.setContent( rs.getString("content") );
                    dto.setCtime(   rs.getDate("ctime") );
                    dto.setMtime(   rs.getDate("mtime") );
                    dto.setViews(   rs.getInt("views") );
                    dto.setEmpId(   rs.getString("emp_id") );
                    dto.setEname(   rs.getString("ename") );  // ③ 작성자 이름
                    list.add(dto);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("NoticeDAO list.size: " + list.size());
        return list;
    }

    // ── 전체 건수 (검색 포함) ─────────────────────
    public int selectNoticeTotal(String keyword) {
        int totalCount = 0;

        boolean hasKeyword = (keyword != null && !keyword.trim().isEmpty());

        String sql = "SELECT count(*) cnt FROM announcement"
                   + (hasKeyword ? " WHERE LOWER(title) LIKE LOWER(?)" : "");

        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (hasKeyword) {
                ps.setString(1, "%" + keyword.trim() + "%");
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalCount = rs.getInt("cnt");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalCount;
    }

    // ── 단건 조회 (ename JOIN) ────────────────────
    public NoticeDTO selectOneNotice(String boardno) {
        NoticeDTO dto = null;

        String sql = "SELECT a.boardno, a.title, a.content, a.ctime, a.mtime, a.views,"
                   + "       a.emp_id, u.ename"
                   + " FROM announcement a"
                   + " LEFT JOIN user_info u ON a.emp_id = u.emp_id"
                   + " WHERE a.boardno = ?";

        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, boardno);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dto = new NoticeDTO();
                    dto.setBoardno( rs.getString("boardno") );
                    dto.setTitle(   rs.getString("title") );
                    dto.setContent( rs.getString("content") );
                    dto.setCtime(   rs.getDate("ctime") );
                    dto.setMtime(   rs.getDate("mtime") );
                    dto.setViews(   rs.getInt("views") );
                    dto.setEmpId(   rs.getString("emp_id") );
                    dto.setEname(   rs.getString("ename") );  // ③ 작성자 이름
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }

    // ── 등록 ─────────────────────────────────────
    public int insertNotice(NoticeDTO noticeDTO) {
        int result = 0;

        String sql = "INSERT INTO announcement (boardno, title, content, ctime, mtime, views, emp_id)"
                   + " VALUES (announcement_seq.nextval, ?, ?, sysdate, sysdate, 0, ?)";

        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, noticeDTO.getTitle());
            ps.setString(2, noticeDTO.getContent());
            ps.setString(3, noticeDTO.getEmpId());

            result = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // ── 수정 ─────────────────────────────────────
    public int updateNotice(NoticeDTO noticeDTO) {
        int result = 0;

        String sql = "UPDATE announcement"
                   + " SET title = ?, content = ?, mtime = sysdate"
                   + " WHERE boardno = ?";

        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, noticeDTO.getTitle());
            ps.setString(2, noticeDTO.getContent());
            ps.setString(3, noticeDTO.getBoardno());

            result = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // ── 삭제 ─────────────────────────────────────
    public int deleteNotice(String boardno) {
        int result = 0;

        String sql = "DELETE FROM announcement WHERE boardno = ?";

        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, boardno);
            result = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // ── 조회수 +1 ────────────────────────────────
    public void updateViews(String boardno) {
        String sql = "UPDATE announcement SET views = views + 1 WHERE boardno = ?";

        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, boardno);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}