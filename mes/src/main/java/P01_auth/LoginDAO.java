package P01_auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.sql.DataSource; 

public class LoginDAO {
	
	public List<LoginDTO> login(LoginDTO d) {
		
		List<LoginDTO> list = new ArrayList<LoginDTO>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			// JNDI 방식
			// connection.xml 맨 아래에 있는 DB정보로 커넥션 풀을 가져온다. Server 폴더에 있다. 기억!
			Context ctx = new InitialContext();

			// DataSource : 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			// DB접속(그런데 이제 커넥션 풀로.)
			conn = dataFactory.getConnection();

			// SQL 준비
			String query = " select * from user_info ";
			       query += " where emp_id = ? ";
			       query += " and password = ? ";
			
			ps = conn.prepareStatement(query);
			ps.setString(1, d.getEmpid());
			ps.setString(2, d.getPassword());

			// SQL 실행 및 결과 확보
			rs = ps.executeQuery();

			// 결과 활용
			
			while (rs.next()) {
				LoginDTO dto = new LoginDTO();
				dto.setEmpid(rs.getString("emp_id"));
				dto.setPassword(rs.getString("password"));
				dto.setEname(rs.getString("ename"));
				
				list.add(dto);	
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		System.out.println("Login 함수 실행 : "+list.size());
		return list;
		
	}

}
