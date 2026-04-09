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
		System.out.println("/login DAO.login 실행");

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
			String query = " select * from user_info u ";
			query += " left outer join dept d on u.dept_no = d.dept_no ";
			query += " where u.emp_id = ? ";
			query += " and u.password = ? ";

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
				dto.setPhone(rs.getInt("phone"));
				dto.setDeptname(rs.getString("DEPT_NAME"));
				dto.setDeptno(rs.getString("DEPT_NO"));

				dto.setHiredate(rs.getDate("hiredate"));

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
		System.out.println("Login 함수 실행 : " + list.size());
		return list;

	}

	public int join(LoginDTO d) {
		System.out.println("/login DAO.join 실행");

		int count = -1;

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			// JNDI 방식
			// connection.xml 맨 아래에 있는 DB정보로 커넥션 풀을 가져온다. Server 폴더에 있다. 기억!
			Context ctx = new InitialContext();

			// DataSource : 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			// DB접속(그런데 이제 커넥션 풀로.)
			conn = dataFactory.getConnection();

			// SQL 준비
			String query = " INSERT INTO USER_INFO ( EMP_ID, ENAME, PHONE, PASSWORD, AUTH, HIREDATE, RETIREDATE, RETIRE, MGR, LICENSE, DEPT_NO) ";
			query += " VALUES ( 'user_' || user_seq.NEXTVAL, ?, ?, ?, null, sysdate, null, null, ?, ?, ? ) ";

			ps = conn.prepareStatement(query);
			ps.setString(1, d.getEname());
			ps.setLong(2, d.getPhone());
			ps.setString(3, d.getPassword());
			ps.setString(4, d.getMgr());
			ps.setString(5, d.getLicense());
			ps.setString(6, d.getDeptno());

			// SQL 실행 및 결과 확보
			count = ps.executeUpdate();

			// 결과 활용

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
		System.out.println("회원가입 결과 : " + count + " 0이상이면 성공. -1이면 실패 ");
		return count;

	}

	public LoginDTO empno(LoginDTO d) {
		System.out.println("/login DAO.empno 실행");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		// 바구니 소환
		LoginDTO dto = new LoginDTO();

		try {
			// JNDI 방식
			// connection.xml 맨 아래에 있는 DB정보로 커넥션 풀을 가져온다. Server 폴더에 있다. 기억!
			Context ctx = new InitialContext();

			// DataSource : 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			// DB접속(그런데 이제 커넥션 풀로.)
			conn = dataFactory.getConnection();

			// SQL 준비
			String query = " select * from user_info u ";
			query += " left outer join dept d on u.dept_no = d.dept_no ";
			query += " where u.ename = ? ";
			query += " and u.password = ? ";

			ps = conn.prepareStatement(query);
			ps.setString(1, d.getEname());
			ps.setString(2, d.getPassword());

			// SQL 실행 및 결과 확보
			rs = ps.executeQuery();

			// 결과 활용

			// 신입사원에게 알려줄 내용들
			while (rs.next()) {
				dto.setEname(rs.getString("ename"));
				dto.setEmpid(rs.getString("emp_id"));
				dto.setPassword(rs.getString("password"));
				dto.setDeptno(rs.getString("DEPT_NO"));
				dto.setDeptname(rs.getString("DEPT_NAME"));
				dto.setHiredate(rs.getDate("hiredate"));
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
		System.out.println("Login 함수 실행 : " + dto.toString());
		return dto;

	}

	public LoginDTO editCheck(LoginDTO d) {
		System.out.println("/login DAO.edit 실행");

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		// 바구니 소환
		LoginDTO dto = new LoginDTO();

		try {
			// JNDI 방식
			// connection.xml 맨 아래에 있는 DB정보로 커넥션 풀을 가져온다. Server 폴더에 있다. 기억!
			Context ctx = new InitialContext();

			// DataSource : 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			// DB접속(그런데 이제 커넥션 풀로.)
			conn = dataFactory.getConnection();

			// SQL 준비
			String query = " select ename, phone, password user_info ";
				   query += " where u.emp_id = ? ";

			ps = conn.prepareStatement(query);
			ps.setString(1, d.getEmpid());
			

			// SQL 실행 및 결과 확보
			rs = ps.executeQuery();

			// 결과 활용
			while (rs.next()) {
				dto.setEname(rs.getString("ename"));
				dto.setPhone(rs.getLong("phone"));
				dto.setPassword(rs.getString("password"));				
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
		return dto;

	}

	public int edit(LoginDTO d) {
		System.out.println("/login DAO.edit 실행");

		int count = -1;

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			// JNDI 방식
			// connection.xml 맨 아래에 있는 DB정보로 커넥션 풀을 가져온다. Server 폴더에 있다. 기억!
			Context ctx = new InitialContext();

			// DataSource : 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			// DB접속(그런데 이제 커넥션 풀로.)
			conn = dataFactory.getConnection();

			// SQL 준비
			String query = " update user_info ";
			query += " set ename = ?,  phone = ?, pw = ? ";
			query += " where u.emp_id = ? ";

			ps = conn.prepareStatement(query);
			ps.setString(1, d.getEname());
			ps.setLong(2, d.getPhone());
			ps.setString(3, d.getPassword());
			ps.setString(4, d.getEmpid());

			// SQL 실행 및 결과 확보
			count = ps.executeUpdate();

			// 결과 활용

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
		System.out.println("회원가입 결과 : " + count + " 0이상이면 성공. -1이면 실패 ");
		return count;

	}

}
