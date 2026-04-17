package P11_masterdata.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import P11_masterdata.DTO.VendorDTO;

public class VendorDAO {

	public List selectAll() {
		List<VendorDTO> list = new ArrayList<VendorDTO>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			Context ctx;
			ctx = new InitialContext();
			// DataSource: 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			// DB 접속(그런데 이제 커넥션 풀로)
			conn = dataFactory.getConnection();

			// SQL 준비
			String query = "select * from vendor";
			ps = conn.prepareStatement(query);

			// SQL 실행 및 결과 확보
			rs = ps.executeQuery();

			// 결과 활용
			while (rs.next()) {
				String vendor_id = rs.getString("vendor_id");
				String vendor_name = rs.getString("vendor_name");
				String vendor_type = rs.getString("vendor_type");
				String phone_no = rs.getString("phone_no");
				String addr = rs.getString("addr");
				String emp_id = rs.getString("emp_id");
				

				VendorDTO vendorDTO = new VendorDTO();
				vendorDTO.setVendor_id(vendor_id);
				vendorDTO.setVendor_name(vendor_name);
				vendorDTO.setVendor_type(vendor_type);
				vendorDTO.setPhone_no(phone_no);
				vendorDTO.setAddr(addr);
				vendorDTO.setEmp_id(emp_id);

				list.add(vendorDTO);
			}
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

}
