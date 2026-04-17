package P06_prod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ProdDAO {

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

    /* ── 목록 조회 (페이지네이션 + JOIN) ──────────────────── */
    public List<ProdDTO> selectAll(ProdDTO prodDTO) {
        List<ProdDTO> list = new ArrayList<>();

        String sql =
            "SELECT * FROM ( " +
            "  SELECT rownum AS rnum, p.* FROM ( " +
            "    SELECT pp.plan_id, " +
            "           pp.plan_qty, " +
            "           pp.prev_qty, " +
            "           pp.plan_sdate, " +
            "           pp.plan_edate, " +
            "           pp.status, " +
            "           pp.item_id, " +
            "           pp.emp_id, " +
            "           i.item_name, " +
            "           u.ename " +
            "    FROM   production_plan pp " +
            "    JOIN   item      i ON pp.item_id = i.item_id " +
            "    JOIN   user_info u ON pp.emp_id  = u.emp_id " +
            "    ORDER  BY pp.plan_sdate DESC " +
            "  ) p " +
            ") " +
            "WHERE rnum >= ? AND rnum <= ?";

        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, prodDTO.getStart());
            ps.setInt(2, prodDTO.getEnd());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProdDTO dto = new ProdDTO();
                    dto.setPlanId(   rs.getString("plan_id")  );
                    dto.setPlanQty(  rs.getInt("plan_qty")    );
                    dto.setPrevQty(  rs.getInt("prev_qty")    );
                    dto.setPlanSdate(rs.getDate("plan_sdate") );
                    dto.setPlanEdate(rs.getDate("plan_edate") );
                    dto.setStatus(   rs.getInt("status")      );
                    dto.setItemId(   rs.getString("item_id")  );
                    dto.setEmpId(    rs.getString("emp_id")   );
                    dto.setItemName( rs.getString("item_name"));
                    dto.setEname(    rs.getString("ename")    );

                    int planQty = dto.getPlanQty();
                    int prevQty = dto.getPrevQty();
                    dto.setProgressPct(planQty > 0 ? (int)(prevQty * 100.0 / planQty) : 0);

                    list.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("ProdDAO list.size: " + list.size());
        return list;
    }

    /* ── 전체 건수 ────────────────────────────────────────── */
    public int selectTotal() {
        int totalCount = 0;
        String sql = "SELECT COUNT(*) cnt FROM production_plan";
        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) totalCount = rs.getInt("cnt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalCount;
    }

    /* ── 상세 조회 ────────────────────────────────────────── */
    public ProdDTO selectOne(String planId) {
        ProdDTO dto = null;

        String sql =
            "SELECT pp.plan_id, " +
            "       pp.plan_qty, " +
            "       pp.prev_qty, " +
            "       pp.plan_sdate, " +
            "       pp.plan_edate, " +
            "       pp.status, " +
            "       pp.item_id, " +
            "       pp.emp_id, " +
            "       i.item_name, " +
            "       u.ename " +
            "FROM   production_plan pp " +
            "JOIN   item      i ON pp.item_id = i.item_id " +
            "JOIN   user_info u ON pp.emp_id  = u.emp_id " +
            "WHERE  pp.plan_id = ?";

        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, planId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dto = new ProdDTO();
                    dto.setPlanId(   rs.getString("plan_id")  );
                    dto.setPlanQty(  rs.getInt("plan_qty")    );
                    dto.setPrevQty(  rs.getInt("prev_qty")    );
                    dto.setPlanSdate(rs.getDate("plan_sdate") );
                    dto.setPlanEdate(rs.getDate("plan_edate") );
                    dto.setStatus(   rs.getInt("status")      );
                    dto.setItemId(   rs.getString("item_id")  );
                    dto.setEmpId(    rs.getString("emp_id")   );
                    dto.setItemName( rs.getString("item_name"));
                    dto.setEname(    rs.getString("ename")    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }

    /* ── 대분류 목록 (GROUP_INFO JOIN) ───────────────────── */
    public List<Map<String, String>> selectGroupList() {
        List<Map<String, String>> list = new ArrayList<>();

        String sql =
            "SELECT DISTINCT i.g_id, g.itemgroup_name " +
            "FROM   item i " +
            "JOIN   group_info g ON i.g_id = g.g_id " +
            "ORDER  BY g.itemgroup_name";

        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, String> map = new HashMap<>();
                map.put("gId",           rs.getString("g_id"));
                map.put("itemgroupName", rs.getString("itemgroup_name"));
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /* ── 소분류 전체 목록 (JS 필터링용) ─────────────────── */
    public List<Map<String, String>> selectItemList() {
        List<Map<String, String>> list = new ArrayList<>();

        String sql =
            "SELECT item_id, item_name, g_id, unit, spec " +
            "FROM   item " +
            "ORDER  BY item_name";

        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, String> map = new HashMap<>();
                map.put("itemId",   rs.getString("item_id")  );
                map.put("itemName", rs.getString("item_name"));
                map.put("gId",      rs.getString("g_id")     );
                map.put("unit",     rs.getString("unit") != null ? rs.getString("unit") : "");
                map.put("spec",     rs.getString("spec") != null ? rs.getString("spec") : "");
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /* ── 담당자 검색 (auth >= 2, DEPT JOIN, 페이지네이션) ── */
    public List<Map<String, String>> selectEmpList(String keyword, int start, int end) {
        List<Map<String, String>> list = new ArrayList<>();

        String sql =
            "SELECT * FROM ( " +
            "  SELECT rownum AS rnum, p.* FROM ( " +
            "    SELECT u.emp_id, u.ename, d.dept_name " +
            "    FROM   user_info u " +
            "    JOIN   dept d ON u.dept_no = d.dept_no " +
            "    WHERE  u.auth >= 2 " +
            "    AND    (u.ename LIKE ? OR u.emp_id LIKE ?) " +
            "    ORDER  BY u.ename " +
            "  ) p " +
            ") " +
            "WHERE rnum >= ? AND rnum <= ?";

        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setInt(3, start);
            ps.setInt(4, end);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, String> map = new HashMap<>();
                    map.put("empId",    rs.getString("emp_id")   );
                    map.put("ename",    rs.getString("ename")    );
                    map.put("deptName", rs.getString("dept_name"));
                    list.add(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /* ── 담당자 검색 건수 ────────────────────────────────── */
    public int selectEmpTotal(String keyword) {
        int total = 0;

        String sql =
            "SELECT COUNT(*) cnt " +
            "FROM   user_info u " +
            "JOIN   dept d ON u.dept_no = d.dept_no " +
            "WHERE  u.auth >= 2 " +
            "AND    (u.ename LIKE ? OR u.emp_id LIKE ?)";

        try (Connection conn = getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) total = rs.getInt("cnt");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }
}