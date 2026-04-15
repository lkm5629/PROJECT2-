package P06_prod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

    // ─── 목록 조회 (페이지네이션 + JOIN) ─────────────────────────────
    public List<ProdDTO> selectAll(ProdDTO prodDTO) {
        List<ProdDTO> list = new ArrayList<>();

        String sql =
            "SELECT * FROM ( " +
            "  SELECT rownum AS rnum, p.* FROM ( " +
            "    SELECT pp.plan_id, " +
            "           pp.plan_qty, " +
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
                    dto.setPlanId(   rs.getString("plan_id")   );
                    dto.setPlanQty(  rs.getInt("plan_qty")     );
                    dto.setPlanSdate(rs.getDate("plan_sdate")  );
                    dto.setPlanEdate(rs.getDate("plan_edate")  );
                    dto.setStatus(   rs.getInt("status")       );
                    dto.setItemId(   rs.getString("item_id")   );
                    dto.setEmpId(    rs.getString("emp_id")    );
                    dto.setItemName( rs.getString("item_name") );
                    dto.setEname(    rs.getString("ename")     );
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("ProdDAO list.size: " + list.size());
        return list;
    }

    // ─── 전체 건수 ────────────────────────────────────────────────
    public int selectTotal() {
        int totalCount = 0;

        String sql = "SELECT COUNT(*) cnt FROM production_plan";

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
                    dto.setPlanId(   rs.getString("plan_id")   );
                    dto.setPlanQty(  rs.getInt("plan_qty")     );
                    dto.setPrevQty(  rs.getInt("prev_qty")     );
                    dto.setPlanSdate(rs.getDate("plan_sdate")  );
                    dto.setPlanEdate(rs.getDate("plan_edate")  );
                    dto.setStatus(   rs.getInt("status")       );
                    dto.setItemId(   rs.getString("item_id")   );
                    dto.setEmpId(    rs.getString("emp_id")    );
                    dto.setItemName( rs.getString("item_name") );
                    dto.setEname(    rs.getString("ename")     );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    }
}