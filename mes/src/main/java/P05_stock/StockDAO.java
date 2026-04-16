package P05_stock;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class StockDAO {

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
    
    

    public int selectTotal(StockDTO stockDTO) {
        int totalCount = 0;
        try (
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(*) cnt FROM io i " +
                "LEFT JOIN vendor v ON i.vendor_id = v.vendor_id " +
                "JOIN item it       ON i.item_id   = it.item_id " +
                "JOIN lot l         ON i.lot_id    = l.lot_id " +
                "JOIN user_info u   ON i.emp_id    = u.emp_id " +
                "WHERE 1=1 " +
                "AND (? IS NULL OR i.io_type = ?) " +
                "AND (? IS NULL OR v.vendor_id = ?) " +
                "AND (? IS NULL OR it.g_id = ?) " +
                "AND (? IS NULL OR it.item_id = ?) " +
                "AND (? IS NULL OR i.io_time >= TO_DATE(?, 'YYYY-MM-DD')) " +
                "AND (? IS NULL OR i.io_time <= TO_DATE(?, 'YYYY-MM-DD') + 1) " +
                "AND (? IS NULL OR it.item_name LIKE ? OR it.item_id LIKE ?)"
            );
        ) {
            String ioType   = stockDTO.getFilterIoType();
            String vendorId = stockDTO.getFilterVendorId();
            String gId      = stockDTO.getFilterGId();
            String itemId   = stockDTO.getFilterItemId();
            String dateFrom = stockDTO.getFilterDateFrom();
            String dateTo   = stockDTO.getFilterDateTo();
            String keyword  = stockDTO.getFilterKeyword();
            String kwLike   = (keyword != null && !keyword.isEmpty()) ? "%" + keyword + "%" : null;

            int idx = 1;
            ps.setString(idx++, ioType);   ps.setString(idx++, ioType);
            ps.setString(idx++, vendorId); ps.setString(idx++, vendorId);
            ps.setString(idx++, gId);      ps.setString(idx++, gId);
            ps.setString(idx++, itemId);   ps.setString(idx++, itemId);
            ps.setString(idx++, dateFrom); ps.setString(idx++, dateFrom);
            ps.setString(idx++, dateTo);   ps.setString(idx++, dateTo);
            ps.setString(idx++, kwLike);   ps.setString(idx++, kwLike); ps.setString(idx++, kwLike);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) totalCount = rs.getInt("cnt");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalCount;
    }

    public List<StockDTO> select(StockDTO stockDTO) {
        List<StockDTO> list = new ArrayList<>();
        try (
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM ( " +
                "    SELECT rownum AS rnum, e.* FROM ( " +
                "        SELECT " +
                "            i.io_id, " +
                "            i.io_time, " +
                "            i.io_type, " +
                "            i.io_reason, " +
                "            v.vendor_id, " +
                "            v.vendor_name, " +
                "            it.item_id, " +
                "            it.g_id, " +
                "            it.item_name, " +
                "            it.unit, " +
                "            it.spec, " +
                "            l.lot_id, " +
                "            l.lot_qty, " +
                "            l.expiry_date, " +

                "            u.ename, " +
                "            u.dept_no, " +
                "            u.retire, " +
                "            u.emp_id " +
                "        FROM io i " +
                "        LEFT JOIN vendor v ON i.vendor_id = v.vendor_id " +
                "        JOIN item it       ON i.item_id   = it.item_id " +
                "        JOIN lot l         ON i.lot_id    = l.lot_id " +
                "        JOIN user_info u   ON i.emp_id    = u.emp_id " +
                "        WHERE 1=1 " +
                "        AND (? IS NULL OR i.io_type = ?) " +
                "        AND (? IS NULL OR v.vendor_id = ?) " +
                "        AND (? IS NULL OR it.g_id = ?) " +
                "        AND (? IS NULL OR it.item_id = ?) " +
                "        AND (? IS NULL OR i.io_time >= TO_DATE(?, 'YYYY-MM-DD')) " +
                "        AND (? IS NULL OR i.io_time <= TO_DATE(?, 'YYYY-MM-DD') + 1) " +
                "        AND (? IS NULL OR it.item_name LIKE ? OR it.item_id LIKE ?) " +
                "        ORDER BY i.io_time DESC " +
                "    ) e " +
                ") WHERE rnum >= ? AND rnum <= ?"
            );
        ) {
            // ���� �Ķ���� ���ε�
            String ioType   = stockDTO.getFilterIoType();
            String vendorId = stockDTO.getFilterVendorId();
            String gId      = stockDTO.getFilterGId();
            String itemId   = stockDTO.getFilterItemId();
            String dateFrom = stockDTO.getFilterDateFrom();
            String dateTo   = stockDTO.getFilterDateTo();
            String keyword  = stockDTO.getFilterKeyword();
            String kwLike   = (keyword != null && !keyword.isEmpty()) ? "%" + keyword + "%" : null;

            int idx = 1;
            ps.setString(idx++, ioType);   ps.setString(idx++, ioType);
            ps.setString(idx++, vendorId); ps.setString(idx++, vendorId);
            ps.setString(idx++, gId);      ps.setString(idx++, gId);
            ps.setString(idx++, itemId);   ps.setString(idx++, itemId);
            ps.setString(idx++, dateFrom); ps.setString(idx++, dateFrom);
            ps.setString(idx++, dateTo);   ps.setString(idx++, dateTo);
            ps.setString(idx++, kwLike);   ps.setString(idx++, kwLike); ps.setString(idx++, kwLike);

            // ����¡
            ps.setInt(idx++, stockDTO.getStart());
            ps.setInt(idx++, stockDTO.getEnd());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StockDTO dto = new StockDTO();
                    dto.setIo_id(rs.getString("io_id"));
                    dto.setIo_time(rs.getDate("io_time"));
                    dto.setIo_type(rs.getInt("io_type"));
                    dto.setIo_reason(rs.getString("io_reason"));
                    dto.setVender_id(rs.getString("vendor_id"));
                    dto.setVender_name(rs.getString("vendor_name"));
                    dto.setItem_id(rs.getString("item_id"));
                    dto.setG_id(rs.getString("g_id"));
                    dto.setItem_name(rs.getString("item_name"));
                    dto.setUnit(rs.getString("unit"));
                    String specStr = rs.getString("spec");
                    dto.setSpec(rs.getString("spec"));
                    dto.setLot_id(rs.getString("lot_id"));
                    dto.setLot_qty(rs.getInt("lot_qty"));
                    dto.setExpiry_date(rs.getDate("expiry_date"));
                    dto.setEname(rs.getString("ename"));
                    dto.setDept_no(rs.getString("dept_no"));
                    dto.setRetire(rs.getInt("retire"));
                    dto.setEmp_id(rs.getString("emp_id"));
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insert(StockDTO dto) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getConn();
            conn.setAutoCommit(false);

            String lotId = null;
            String ioId  = null;

            if (dto.getIo_type() == 0) {
                // ���� �԰�: lot_seq�� �� LOT ID ���� ����������������������������
                ps = conn.prepareStatement(
                    "SELECT 'LOT_' || lot_seq.nextval AS lot_id FROM dual"
                );
                rs = ps.executeQuery();
                if (rs.next()) lotId = rs.getString("lot_id");
                rs.close();
                ps.close();

                // lot INSERT
                ps = conn.prepareStatement(
                    "INSERT INTO lot (lot_id, item_id, lot_qty, expiry_date, deleted) " +
                    "VALUES (?, ?, ?, ?, 'N')"
                );
                ps.setString(1, lotId);
                ps.setString(2, dto.getItem_id());
                ps.setInt(3, dto.getLot_qty());
                ps.setDate(4, dto.getExpiry_date());
                ps.executeUpdate();
                ps.close();

                // �԰� io_id ����
                ps = conn.prepareStatement(
                    "SELECT 'IN_' || in_seq.nextval AS io_id FROM dual"
                );
                rs = ps.executeQuery();
                if (rs.next()) ioId = rs.getString("io_id");
                rs.close();
                ps.close();
                
                ps = conn.prepareStatement(
                	    "UPDATE stock SET stock_no = stock_no + ? WHERE item_id = ? AND deleted = 'N'"
                	);
                	ps.setInt(1, dto.getLot_qty());
                	ps.setString(2, dto.getItem_id());
                	ps.executeUpdate();
                	ps.close();

            } else {
                // ���� ���: ���� LOT ���� ����������������������������������������������������
                lotId = dto.getLot_id();

                // ��� io_id ����
                ps = conn.prepareStatement(
                    "SELECT 'OUT_' || out_seq.nextval AS io_id FROM dual"
                );
                rs = ps.executeQuery();
                if (rs.next()) ioId = rs.getString("io_id");
                rs.close();
                ps.close();
                
                rs = ps.executeQuery();
                if (rs.next()) ioId = rs.getString("io_id");
                rs.close();
                ps.close();

                // �� ���� �߰�
                ps = conn.prepareStatement(
                    "UPDATE stock SET stock_no = stock_no - ? WHERE item_id = ? AND deleted = 'N'"
                );
                ps.setInt(1, dto.getLot_qty());
                ps.setString(2, dto.getItem_id());
                ps.executeUpdate();
                ps.close();
                
            }

            // ���� io INSERT (�԰�/��� ����) ������������������������������������������������
            ps = conn.prepareStatement(
                "INSERT INTO io (io_id, io_time, io_type, io_reason, vendor_id, item_id, lot_id, emp_id, deleted) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'N')"
            );
            ps.setString(1, ioId);
            ps.setDate(2, dto.getIo_time());
            ps.setInt(3, dto.getIo_type());
            ps.setString(4, dto.getIo_reason());
            ps.setString(5, dto.getVender_id());
            ps.setString(6, dto.getItem_id());
            ps.setString(7, lotId);
            ps.setString(8, dto.getEmp_id());
            ps.executeUpdate();

            conn.commit();

        } catch (Exception e) {
            try { if (conn != null) conn.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } finally {
            try { if (rs   != null) rs.close();   } catch (Exception e) { e.printStackTrace(); }
            try { if (ps   != null) ps.close();   } catch (Exception e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }

    public List<StockDTO> selectVendorList() {
        List<StockDTO> list = new ArrayList<>();
        try (
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT vendor_id, vendor_name FROM vendor"
            );
            ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                StockDTO dto = new StockDTO();
                dto.setVender_id(rs.getString("vendor_id"));
                dto.setVender_name(rs.getString("vendor_name"));
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<StockDTO> selectItemList() {
        List<StockDTO> list = new ArrayList<>();
        try (
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT item_id, item_name, g_id, spec, unit FROM item"
            );
            ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                StockDTO dto = new StockDTO();
                dto.setItem_id(rs.getString("item_id"));
                dto.setItem_name(rs.getString("item_name"));
                dto.setG_id(rs.getString("g_id"));
                String specStr = rs.getString("spec");
                dto.setSpec(rs.getString("spec"));
                dto.setUnit(rs.getString("unit"));
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<StockDTO> selectGroupList() {
        List<StockDTO> list = new ArrayList<>();
        try (
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT g_id FROM item GROUP BY g_id ORDER BY g_id"
            );
            ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                StockDTO dto = new StockDTO();
                dto.setG_id(rs.getString("g_id"));
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // �԰� ��� ��ȸ (��� ��� �� AJAX ������)
    public List<StockDTO> selectInList() {
        List<StockDTO> list = new ArrayList<>();
        try (
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT " +
                "    i.io_id, " +
                "    it.item_id, " +
                "    it.item_name, " +
                "    it.spec, " +
                "    it.unit, " +
                "    l.lot_id, " +
                "    l.lot_qty " +
                "FROM io i " +
                "JOIN item it  ON i.item_id   = it.item_id " +
                "JOIN lot l    ON i.lot_id     = l.lot_id " +
                "WHERE i.io_type = 0 " +
                "ORDER BY i.io_time DESC"
            );
            ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                StockDTO dto = new StockDTO();
                dto.setIo_id(rs.getString("io_id"));
                dto.setItem_id(rs.getString("item_id"));
                dto.setItem_name(rs.getString("item_name"));
                dto.setSpec(rs.getString("spec"));
                dto.setUnit(rs.getString("unit"));
                dto.setLot_id(rs.getString("lot_id"));
                dto.setLot_qty(rs.getInt("lot_qty"));
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
 // selectAvailableLotList() �߰�
    public List<StockDTO> selectAvailableLotList(String keyword) {
        List<StockDTO> list = new ArrayList<>();
        try (
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(
            		"SELECT l.lot_id, l.lot_qty, l.expiry_date, " +
            		"       it.item_id, it.item_name, it.spec, it.unit, " +
            		"       u.emp_id, u.ename " +
            		"FROM lot l " +
            		"JOIN item it ON l.item_id = it.item_id " +
            		"JOIN user_info u ON u.emp_id = ( " +
            		"    SELECT i2.emp_id FROM io i2 " +
            		"    WHERE i2.lot_id = l.lot_id AND i2.io_type = 0 AND ROWNUM = 1 " +
            		") " +
            		"WHERE NVL(( " +
            		"    SELECT SUM(CASE WHEN io_type = 0 THEN lot_qty ELSE -lot_qty END) " +
            		"    FROM io WHERE lot_id = l.lot_id " +
            		"), 0) > 0 " +
            		"AND (it.item_name LIKE ? OR l.lot_id LIKE ?) " +
            		"ORDER BY l.lot_id DESC"
            );
        ) {
            String kw = "%" + (keyword == null ? "" : keyword) + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StockDTO dto = new StockDTO();
                    dto.setLot_id(rs.getString("lot_id"));
                    dto.setLot_qty(rs.getInt("lot_qty"));
                    dto.setExpiry_date(rs.getDate("expiry_date"));
                    dto.setItem_id(rs.getString("item_id"));
                    dto.setItem_name(rs.getString("item_name"));
                    dto.setSpec(rs.getString("spec"));
                    dto.setUnit(rs.getString("unit"));
                    dto.setEmp_id(rs.getString("emp_id"));
                    dto.setEname(rs.getString("ename"));
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<StockDTO> selectUserList(String keyword) {
        List<StockDTO> list = new ArrayList<>();
        try (
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT emp_id, ename, dept_no " +
                "FROM user_info " +
                "WHERE (retire = 0 OR retire IS NULL) " +
                "AND (ename LIKE ? OR emp_id LIKE ?) " +
                "ORDER BY ename"
            );
        ) {
            String kw = "%" + (keyword == null ? "" : keyword) + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StockDTO dto = new StockDTO();
                    dto.setEmp_id(rs.getString("emp_id"));
                    dto.setEname(rs.getString("ename"));
                    dto.setDept_no(rs.getString("dept_no"));
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // 유통기한 임박 LOT 수 (오늘 ~ 10일 이내)
    public int selectExpiryWarnCount() {
        int count = 0;
        try (
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(*) cnt FROM lot l " +
                "WHERE l.expiry_date IS NOT NULL " +
                "AND l.expiry_date >= TRUNC(SYSDATE) " +
                "AND l.expiry_date <= TRUNC(SYSDATE) + 10 " +
                "AND NVL(( " +
                "    SELECT SUM(CASE WHEN io_type = 0 THEN lot_qty ELSE -lot_qty END) " +
                "    FROM io WHERE lot_id = l.lot_id " +
                "), 0) > 0"
            );
        ) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) count = rs.getInt("cnt");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    // 유통기한 초과 LOT 수 (오늘 이전)
    public int selectExpiryOverCount() {
        int count = 0;
        try (
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(*) cnt FROM lot l " +
                "WHERE l.expiry_date IS NOT NULL " +
                "AND l.expiry_date < TRUNC(SYSDATE) " +
                "AND NVL(( " +
                "    SELECT SUM(CASE WHEN io_type = 0 THEN lot_qty ELSE -lot_qty END) " +
                "    FROM io WHERE lot_id = l.lot_id " +
                "), 0) > 0"
            );
        ) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) count = rs.getInt("cnt");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public int selectStockNo(String itemId) {
        int result = 0;
        try (
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT stock_no FROM stock WHERE item_id = ? AND deleted = 'N'"
            );
        ) {
            ps.setString(1, itemId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = rs.getInt("stock_no");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
}