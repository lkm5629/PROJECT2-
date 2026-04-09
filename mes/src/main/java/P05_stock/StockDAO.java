package P05_stock;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import P00_layout.LoggableStatement;

public class StockDAO {

    // JNDI 방식으로 커넥션 풀에서 DB 연결 가져오기
    private Connection getConn() {
        Connection conn = null;
        try {
            // context.xml에 설정된 커넥션 풀 관리자 가져오기
            Context ctx = new InitialContext();
            DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
            // 커넥션 풀에서 DB 연결 획득
            conn = dataFactory.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    // 전체 입출고 건수 조회 (페이징 totalCount 계산용)
    public int selectTotal() {
        int totalCount = 0;
        try (
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(*) cnt FROM io"
            );
        ) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // 전체 건수 추출
                    totalCount = rs.getInt("cnt");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalCount;
    }

    // 입출고 목록 조회 (페이징 적용)
    // io, vendor, item, lot, user_info 테이블 JOIN
    public List<StockDTO> select(StockDTO stockDTO) {
        List<StockDTO> list = new ArrayList<>();
        try (
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(
                // 페이징 처리: rownum으로 start ~ end 범위 추출
                "SELECT * FROM ( " +
                "    SELECT rownum AS rnum, e.* FROM ( " +
                "        SELECT " +
                "            i.io_id, " +
                "            i.io_time, " +
                "            i.deleted, " +
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
                "            l.deleted AS lotdeleted, " +
                "            u.ename, " +
                "            u.dept_no, " +
                "            u.retire, " +
                "            u.emp_id " +
                "        FROM io i " +
                // 거래처 정보 JOIN
                "        JOIN vendor v    ON i.vendor_id = v.vendor_id " +
                // 자재 정보 JOIN
                "        JOIN item it     ON i.item_id   = it.item_id " +
                // LOT 정보 JOIN
                "        JOIN lot l       ON i.lot_id    = l.lot_id " +
                // 작업자 정보 JOIN
                "        JOIN user_info u ON i.emp_id    = u.emp_id " +
                // 입출고일 내림차순 정렬
                "        ORDER BY i.io_time DESC " +
                "    ) e " +
                // 페이징 범위 조건 (start ~ end)
                ") WHERE rnum >= ? AND rnum <= ?"
            );
        ) {
            // 페이징 파라미터 바인딩
            ps.setInt(1, stockDTO.getStart());
            ps.setInt(2, stockDTO.getEnd());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // 조회 결과를 DTO에 담기
                    StockDTO dto = new StockDTO();

                    // io 테이블
                    dto.setIo_id(rs.getString("io_id"));
                    dto.setIo_time(rs.getDate("io_time"));
                    dto.setDeleted(rs.getString("deleted"));
                    dto.setIo_type(rs.getInt("io_type"));
                    dto.setIo_reason(rs.getString("io_reason"));

                    // vendor 테이블
                    dto.setVender_id(rs.getString("vendor_id"));
                    dto.setVender_name(rs.getString("vendor_name"));

                    // item 테이블
                    dto.setItem_id(rs.getString("item_id"));
                    dto.setG_id(rs.getString("g_id"));
                    dto.setItem_name(rs.getString("item_name"));
                    dto.setUnit(rs.getString("unit"));
                    dto.setSpec(rs.getInt("spec"));

                    // lot 테이블
                    dto.setLot_id(rs.getString("lot_id"));
                    dto.setLot_qty(rs.getInt("lot_qty"));
                    dto.setExpiry_date(rs.getDate("expiry_date"));
                    dto.setLotdeleted(rs.getString("lotdeleted"));

                    // user_info 테이블
                    dto.setEname(rs.getString("ename"));
                    dto.setDept_no(rs.getString("dept_no"));
                    dto.setRetire(rs.getInt("retire"));
                    dto.setEmp_id(rs.getString("emp_id"));

                    // list에 추가
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    
 // io INSERT + lot INSERT
    public void insert(StockDTO dto) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = getConn();
            conn.setAutoCommit(false);
            
            // lot INSERT - 모달에서 입력받은 lot_id 직접 사용
            String lotSql = "INSERT INTO lot (lot_id, item_id, lot_qty, expiry_date, deleted) " +
                            "VALUES (?, ?, ?, ?, 'N')";
            ps = conn.prepareStatement(lotSql);
            ps.setString(1, dto.getLot_id());
            ps.setString(2, dto.getItem_id());
            ps.setInt(3, dto.getLot_qty());
            ps.setDate(4, dto.getExpiry_date());
            ps.executeUpdate();
            ps.close();
            
            // io_type에 따라 시퀀스 분기
            // 0: 입고 → io_seq / 1: 출고 → out_seq
            String seq = (dto.getIo_type() == 0) ? "in_seq.nextval" : "out_seq.nextval";
            
            // io INSERT
            String ioSql = "INSERT INTO io (io_id, io_time, io_type, io_reason, vendor_id, item_id, lot_id, emp_id, deleted) " +
                           "VALUES (" + seq + ", ?, ?, ?, ?, ?, ?, ?, 'N')";
            ps = conn.prepareStatement(ioSql);
            ps.setDate(1, dto.getIo_time());
            ps.setInt(2, dto.getIo_type());
            ps.setString(3, dto.getIo_reason());
            ps.setString(4, dto.getVender_id());
            ps.setString(5, dto.getItem_id());
            ps.setString(6, dto.getLot_id());
            ps.setString(7, dto.getEmp_id());
            ps.executeUpdate();
            
            conn.commit();
            
        } catch (Exception e) {
            try { if(conn != null) conn.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } finally {
            try { if(ps != null) ps.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if(conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
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
            while(rs.next()) {
                StockDTO dto = new StockDTO();
                dto.setVender_id(rs.getString("vendor_id"));
                dto.setVender_name(rs.getString("vendor_name"));
                list.add(dto);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
 // 자재 목록 조회
    public List<StockDTO> selectItemList() {
        List<StockDTO> list = new ArrayList<>();
        try (
            Connection conn = getConn();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT item_id, item_name, g_id, spec, unit FROM item"
            );
            ResultSet rs = ps.executeQuery();
        ) {
            while(rs.next()) {
                StockDTO dto = new StockDTO();
                dto.setItem_id(rs.getString("item_id"));
                dto.setItem_name(rs.getString("item_name"));
                dto.setG_id(rs.getString("g_id"));
                dto.setSpec(rs.getInt("spec"));
                dto.setUnit(rs.getString("unit"));
                list.add(dto);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
}
