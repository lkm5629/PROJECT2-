package P05_stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Stock2DAO {

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

    // 전체 건수
    public int selectTotal(Stock2DTO dto) {
        int totalCount = 0;

        String gId     = dto.getFilterGId();
        String keyword = dto.getFilterKeyword();
        String kwLike  = (keyword != null && !keyword.isEmpty()) ? "%" + keyword + "%" : null;

        StringBuilder sql = new StringBuilder(
            "SELECT COUNT(*) cnt " +
            "FROM stock s " +
            "JOIN item it ON s.item_id = it.item_id " +
            "WHERE s.deleted = 'N' "
        );
        List<Object> params = new ArrayList<>();

        if (gId != null && !gId.isEmpty()) {
            sql.append("AND it.g_id = ? ");
            params.add(gId);
        }
        if (kwLike != null) {
            sql.append("AND (it.item_name LIKE ? OR it.item_id LIKE ?) ");
            params.add(kwLike);
            params.add(kwLike);
        }
        String filterStock = dto.getFilterStock();
        if ("normal".equals(filterStock)) {
            sql.append("AND s.stock_no >= it.safe_qty ");
        } else if ("lack".equals(filterStock)) {
            sql.append("AND s.stock_no < it.safe_qty ");
        }

        try (
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(sql.toString());
        ) {
            for (int i = 0; i < params.size(); i++) ps.setObject(i + 1, params.get(i));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) totalCount = rs.getInt("cnt");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalCount;
    }

    // 목록 조회 (페이징)
    public List<Stock2DTO> select(Stock2DTO dto) {
        List<Stock2DTO> list = new ArrayList<>();

        String gId     = dto.getFilterGId();
        String keyword = dto.getFilterKeyword();
        String kwLike  = (keyword != null && !keyword.isEmpty()) ? "%" + keyword + "%" : null;

        StringBuilder innerSql = new StringBuilder(
            "SELECT s.stock_id, s.stock_no, it.safe_qty, s.deleted, " +
            "it.item_id, it.item_name, it.g_id, it.spec, it.unit " +
            "FROM stock s " +
            "JOIN item it ON s.item_id = it.item_id " +
            "WHERE s.deleted = 'N' "
        );
        List<Object> params = new ArrayList<>();

        if (gId != null && !gId.isEmpty()) {
            innerSql.append("AND it.g_id = ? ");
            params.add(gId);
        }
        if (kwLike != null) {
            innerSql.append("AND (it.item_name LIKE ? OR it.item_id LIKE ?) ");
            params.add(kwLike);
            params.add(kwLike);
        }
        String filterStock = dto.getFilterStock();
        if ("normal".equals(filterStock)) {
            innerSql.append("AND s.stock_no >= it.safe_qty ");
        } else if ("lack".equals(filterStock)) {
            innerSql.append("AND s.stock_no < it.safe_qty ");
        }
        innerSql.append("ORDER BY s.stock_id ");

        String sql =
            "SELECT * FROM ( " +
            "    SELECT rownum AS rnum, e.* FROM ( " + innerSql + ") e " +
            ") WHERE rnum >= ? AND rnum <= ?";

        params.add(dto.getStart());
        params.add(dto.getEnd());

        try (
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            for (int i = 0; i < params.size(); i++) ps.setObject(i + 1, params.get(i));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Stock2DTO d = new Stock2DTO();
                    d.setStock_id(rs.getString("stock_id"));
                    d.setStock_no(rs.getInt("stock_no"));
                    d.setSafe_qty(rs.getInt("safe_qty"));
                    d.setDeleted(rs.getString("deleted"));
                    d.setItem_id(rs.getString("item_id"));
                    d.setItem_name(rs.getString("item_name"));
                    d.setG_id(rs.getString("g_id"));
                    d.setSpec(rs.getString("spec"));
                    d.setUnit(rs.getString("unit"));
                    list.add(d);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 분류별 목록
    public List<Stock2DTO> selectGroupList() {
        List<Stock2DTO> list = new ArrayList<>();
        try (
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT g_id FROM item GROUP BY g_id ORDER BY g_id"
            );
            ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                Stock2DTO d = new Stock2DTO();
                d.setG_id(rs.getString("g_id"));
                list.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 요약 카드: 전체 품목 수
    public int selectTotalCount() {
        int cnt = 0;
        try (
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(*) cnt FROM stock WHERE deleted = 'N'"
            );
            ResultSet rs = ps.executeQuery();
        ) {
            if (rs.next()) cnt = rs.getInt("cnt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cnt;
    }

    // 요약 카드: 정상재고 (stock_no >= safe_qty)
    public int selectNormalCount() {
        int cnt = 0;
        try (
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(*) cnt " +
                "FROM stock s " +
                "JOIN item it ON s.item_id = it.item_id " +
                "WHERE s.deleted = 'N' AND s.stock_no >= it.safe_qty"
            );
            ResultSet rs = ps.executeQuery();
        ) {
            if (rs.next()) cnt = rs.getInt("cnt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cnt;
    }

    // 요약 카드: 부족재고 (stock_no < safe_qty)
    public int selectLackCount() {
        int cnt = 0;
        try (
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(*) cnt " +
                "FROM stock s " +
                "JOIN item it ON s.item_id = it.item_id " +
                "WHERE s.deleted = 'N' AND s.stock_no < it.safe_qty"
            );
            ResultSet rs = ps.executeQuery();
        ) {
            if (rs.next()) cnt = rs.getInt("cnt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cnt;
    }

    // 안전재고 수정
    public void updateSafeNo(String itemId, int safeQty) {
        try (
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE item SET safe_qty = ? WHERE item_id = ?"
            );
        ) {
            ps.setInt(1, safeQty);
            ps.setString(2, itemId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}