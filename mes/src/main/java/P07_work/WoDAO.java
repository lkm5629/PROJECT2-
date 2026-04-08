package P07_work;

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

public class WoDAO {
	
	public List<WoDTO> getList (int start, int end) {
		List<WoDTO> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			// JNDI 방식
			// context.xml에 있는 DB 정보로 커넥션 풀을 가져온다
			Context ctx = new InitialContext();
			// DataSource : 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			// DB 접속(그런데 이제 커넥션 풀로)
			conn = dataFactory.getConnection();

			// SQL 준비
			String query = "SELECT "
					+ "    wo.*, "
					+ "	   worker.ename workerName, "
					+ "    pp.status, "
					+ "    pp.plan_qty, "
					+ "    pp.prev_qty, "
					+ "    pp.item_id, "
					+ "    pp.emp_id director, "
					+ "    director.ename directorName, "
					+ "    i.item_name, "
					+ "    i.unit, "
					+ "    i.spec, "
					+ "    i.g_id "
					+ "FROM work_order wo "
					+ "LEFT OUTER JOIN user_info worker "
					+ "	ON wo.emp_id = worker.emp_id "
					+ "LEFT outer JOIN production_plan pp "
					+ "    ON wo.plan_id = pp.plan_id "
					+ "LEFT OUTER JOIN user_info director "
					+ "	ON pp.emp_id = director.emp_id "
					+ "LEFT OUTER JOIN item i "
					+ "    ON pp.item_id = i.item_id "
					+ "where wo.deleted is null";
			ps = conn.prepareStatement(query);

			// SQL 실행 및 결과 확보
			rs = ps.executeQuery();

			// 결과 활용
			while (rs.next()) {
				
				String woId = rs.getString("wo_id");
				Date workDate = rs.getDate("workdate");
				String planId = rs.getString("plan_id");
				int woStatus = rs.getInt("wostatus_no");
				int woQty = rs.getInt("wo_qty");
				String deleted = rs.getString("deleted");
				String worker = rs.getString("emp_id");
				String wName = rs.getString("workerName");
				int planStatus = rs.getInt("status");
				int planQty = rs.getInt("plan_qty");
				int prevQty = rs.getInt("prev_qty");
				String itemId = rs.getString("item_id");
				String director = rs.getString("director");
				String dName = rs.getString("directorName");
				String itemName = rs.getString("item_name");
				String unit = rs.getString("unit");
				int spec = rs.getInt("spec");
				String group = rs.getString("g_id");
				

				// DTO 에 넣기
				WoDTO dto = new WoDTO();
				
				dto.setWoId(woId);
				dto.setWorkDate(workDate);
				dto.setPlanId(planId);
				dto.setWostatus(woStatus);
				dto.setWoQty(woQty);
				dto.setDeleted(deleted);
				dto.setWorker(worker);
				dto.setPlanStatus(planStatus);
				dto.setPlanQty(planQty);
				dto.setPrevQty(prevQty);
				dto.setItemId(itemId);
				dto.setDirector(director);
				dto.setItemName(itemName);
				dto.setUnit(unit);
				dto.setSpec(spec);
				dto.setGroup(group);
				dto.setwName(wName);
				dto.setdName(dName);
				
				list.add(dto);

			}

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
		} // finally
		
		return list;
	}
	
	
	public List<WoDTO> search (int start, int end, SearchDTO search) {
		List<WoDTO> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			// JNDI 방식
			// context.xml에 있는 DB 정보로 커넥션 풀을 가져온다
			Context ctx = new InitialContext();
			// DataSource : 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			// DB 접속(그런데 이제 커넥션 풀로)
			conn = dataFactory.getConnection();

			// SQL 준비
			String query = "SELECT "
					+ "    wo.*, "
					+ "    worker.ename workerName, "
					+ "    pp.status, "
					+ "    pp.plan_qty, "
					+ "    pp.prev_qty, "
					+ "    pp.item_id, "
					+ "    pp.emp_id director, "
					+ "    director.ename directorName, "
					+ "    i.item_name, "
					+ "    i.unit, "
					+ "    i.spec, "
					+ "    i.g_id "
					+ "FROM work_order wo "
					+ "LEFT OUTER JOIN user_info worker "
					+ "	ON wo.emp_id = worker.emp_id "
					+ "LEFT outer JOIN production_plan pp "
					+ "    ON wo.plan_id = pp.plan_id "
					+ "LEFT OUTER JOIN user_info director "
					+ "	ON pp.emp_id = director.emp_id "
					+ "LEFT OUTER JOIN item i "
					+ "    ON pp.item_id = i.item_id "
					+ "where wo.deleted is null";
			
			if (search.getStatus() != 0) {
				query += " and wo.wostatus_no = " + search.getStatus();
			}
			
			int idx = 1;

			if (search.getsDate() != null && !search.getsDate().isEmpty()) {
			    query += " and workdate >= TO_DATE(?, 'YYYY-MM-DD') ";
			}

			if (search.geteDate() != null && !search.geteDate().isEmpty()) {
			    query += " and workdate <= TO_DATE(?, 'YYYY-MM-DD') ";
			}
			
			if (!( "".equals(search.getKeyword()) )) {
				query += " and (upper(i.item_name) like upper(?) or upper(worker.ename) like upper(?)) ";
			}
			
			ps = conn.prepareStatement(query);

			if (search.getsDate() != null && !search.getsDate().isEmpty()) {
			    ps.setString(idx++, search.getsDate());
			}

			if (search.geteDate() != null && !search.geteDate().isEmpty()) {
			    ps.setString(idx++, search.geteDate());
			}
			
			if (search.getKeyword() != null && !search.getKeyword().isEmpty()) {
			    String keyword = "%" + search.getKeyword() + "%";

			    ps.setString(idx++, keyword);
			    ps.setString(idx++, keyword);
			}

			// SQL 실행 및 결과 확보
			rs = ps.executeQuery();

			// 결과 활용
			while (rs.next()) {

				String woId = rs.getString("wo_id");
				Date workDate = rs.getDate("workdate");
				String planId = rs.getString("plan_id");
				int woStatus = rs.getInt("wostatus_no");
				int woQty = rs.getInt("wo_qty");
				String deleted = rs.getString("deleted");
				String worker = rs.getString("emp_id");
				String wName = rs.getString("workername");
				int planStatus = rs.getInt("status");
				int planQty = rs.getInt("plan_qty");
				int prevQty = rs.getInt("prev_qty");
				String itemId = rs.getString("item_id");
				String director = rs.getString("director");
				String dName = rs.getString("directorname");
				String itemName = rs.getString("item_name");
				String unit = rs.getString("unit");
				int spec = rs.getInt("spec");
				String group = rs.getString("g_id");
				

				// DTO 에 넣기
				WoDTO dto = new WoDTO();
				
				dto.setWoId(woId);
				dto.setWorkDate(workDate);
				dto.setPlanId(planId);
				dto.setWostatus(woStatus);
				dto.setWoQty(woQty);
				dto.setDeleted(deleted);
				dto.setWorker(worker);
				dto.setPlanStatus(planStatus);
				dto.setPlanQty(planQty);
				dto.setPrevQty(prevQty);
				dto.setItemId(itemId);
				dto.setDirector(director);
				dto.setItemName(itemName);
				dto.setUnit(unit);
				dto.setSpec(spec);
				dto.setGroup(group);
				dto.setwName(wName);
				dto.setdName(dName);
				
				list.add(dto);

			}

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
		} // finally
		
		return list;
	}
	
	public WoDTO detail (WoDTO dto) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			// JNDI 방식
			// context.xml에 있는 DB 정보로 커넥션 풀을 가져온다
			Context ctx = new InitialContext();
			// DataSource : 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			// DB 접속(그런데 이제 커넥션 풀로)
			conn = dataFactory.getConnection();

			// SQL 준비
			String query = "SELECT "
					+ "    wo.*, "
					+ "	   worker.ename workerName, "
					+ "    pp.status, "
					+ "    pp.plan_qty, "
					+ "    pp.prev_qty, "
					+ "    pp.item_id, "
					+ "    pp.emp_id director, "
					+ "    director.ename directorName, "
					+ "    i.item_name, "
					+ "    i.unit, "
					+ "    i.spec, "
					+ "    i.g_id "
					+ "FROM work_order wo "
					+ "LEFT OUTER JOIN user_info worker "
					+ "	ON wo.emp_id = worker.emp_id "
					+ "LEFT outer JOIN production_plan pp "
					+ "    ON wo.plan_id = pp.plan_id "
					+ "LEFT OUTER JOIN user_info director "
					+ "	ON pp.emp_id = director.emp_id "
					+ "LEFT OUTER JOIN item i "
					+ "    ON pp.item_id = i.item_id "
					+ "where wo.deleted is null "
					+ "and wo_id = ? ";

			ps = conn.prepareStatement(query);
			ps.setString(1, dto.getWoId());

			// SQL 실행 및 결과 확보
			rs = ps.executeQuery();

			// 결과 활용
			while (rs.next()) {
				
				String woId = rs.getString("wo_id");
				Date workDate = rs.getDate("workdate");
				String planId = rs.getString("plan_id");
				int woStatus = rs.getInt("wostatus_no");
				int woQty = rs.getInt("wo_qty");
				String deleted = rs.getString("deleted");
				String worker = rs.getString("emp_id");
				String wName = rs.getString("workerName");
				int planStatus = rs.getInt("status");
				int planQty = rs.getInt("plan_qty");
				int prevQty = rs.getInt("prev_qty");
				String itemId = rs.getString("item_id");
				String director = rs.getString("director");
				String dName = rs.getString("directorName");
				String itemName = rs.getString("item_name");
				String unit = rs.getString("unit");
				int spec = rs.getInt("spec");
				String group = rs.getString("g_id");
				

				// DTO 에 넣기
				dto.setWoId(woId);
				dto.setWorkDate(workDate);
				dto.setPlanId(planId);
				dto.setWostatus(woStatus);
				dto.setWoQty(woQty);
				dto.setDeleted(deleted);
				dto.setWorker(worker);
				dto.setPlanStatus(planStatus);
				dto.setPlanQty(planQty);
				dto.setPrevQty(prevQty);
				dto.setItemId(itemId);
				dto.setDirector(director);
				dto.setItemName(itemName);
				dto.setUnit(unit);
				dto.setSpec(spec);
				dto.setGroup(group);
				dto.setwName(wName);
				dto.setdName(dName);
				
			}

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
		} // finally
		
		return dto;
	}
	
	public int count() {
		
		int cnt = 0;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			// JNDI 방식
			// context.xml에 있는 DB 정보로 커넥션 풀을 가져온다
			Context ctx = new InitialContext();
			// DataSource : 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			// DB 접속(그런데 이제 커넥션 풀로)
			conn = dataFactory.getConnection();

			// SQL 준비
			String query = " SELECT count(*) cnt from (select * from work_order where deleted is null) ";
			
			ps= new LoggableStatement(conn, query);
			
			// SQL 실행 및 결과 확보
			rs = ps.executeQuery();
			
			

			// 결과 활용
			while (rs.next()) {
				
				cnt = rs.getInt("cnt");
				
			}

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
		} // finally

		return cnt;

	} // count
	
	
	public int countSearch(SearchDTO search) {
		
		int cnt = 0;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			// JNDI 방식
			// context.xml에 있는 DB 정보로 커넥션 풀을 가져온다
			Context ctx = new InitialContext();
			// DataSource : 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			// DB 접속(그런데 이제 커넥션 풀로)
			conn = dataFactory.getConnection();

			// SQL 준비
			String query = "SELECT COUNT(DISTINCT wo.wo_id) "
                    + "FROM work_order wo "
                    + "LEFT OUTER JOIN user_info worker "
                    + "    ON wo.emp_id = worker.emp_id "
                    + "LEFT OUTER JOIN production_plan pp "
                    + "    ON wo.plan_id = pp.plan_id "
                    + "LEFT OUTER JOIN user_info director "
                    + "    ON pp.emp_id = director.emp_id "
                    + "LEFT OUTER JOIN item i "
                    + "    ON pp.item_id = i.item_id "
                    + "WHERE wo.deleted IS NULL";
			
			 // 조건 동일하게 유지
	        if (search.getStatus() != 0) {
	            query += " AND wo.wostatus_no = " + search.getStatus();
	        }

	        int idx = 1;

	        if (search.getsDate() != null && !search.getsDate().isEmpty()) {
	            query += " AND workdate >= TO_DATE(?, 'YYYY-MM-DD') ";
	        }

	        if (search.geteDate() != null && !search.geteDate().isEmpty()) {
	            query += " AND workdate <= TO_DATE(?, 'YYYY-MM-DD') ";
	        }

	        if (search.getKeyword() != null && !search.getKeyword().isEmpty()) {
	            query += " AND (UPPER(i.item_name) LIKE UPPER(?) "
	                   + " OR UPPER(worker.ename) LIKE UPPER(?)) ";
	        }
			
			ps= new LoggableStatement(conn, query);
			
			
			
			// 파라미터 바인딩 (순서 동일!)
	        if (search.getsDate() != null && !search.getsDate().isEmpty()) {
	            ps.setString(idx++, search.getsDate());
	        }

	        if (search.geteDate() != null && !search.geteDate().isEmpty()) {
	            ps.setString(idx++, search.geteDate());
	        }

	        if (search.getKeyword() != null && !search.getKeyword().isEmpty()) {
	            String keyword = "%" + search.getKeyword() + "%";
	            ps.setString(idx++, keyword);
	            ps.setString(idx++, keyword);
	        }
			
			// SQL 실행 및 결과 확보
			rs = ps.executeQuery();
			
			
			if (rs.next()) {
	            cnt = rs.getInt(1);
	        }
			

			// 결과 활용
			while (rs.next()) {
				
				cnt = rs.getInt("cnt");
				
			}

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
		} // finally

		return cnt;

	} // countSearch

}
