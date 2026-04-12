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
import P01_auth.DTO.UserWoDTO;
import P06_prod.DTO.PlanWoDTO;

public class WoDAO {
	
	public List<WoDTO> getList (int start, int end) {
		List<WoDTO> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			conn = dataFactory.getConnection();

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
					+ "order by workdate desc";
			ps = conn.prepareStatement(query);

			rs = ps.executeQuery();

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
				

				WoDTO dto = new WoDTO();
				
				dto.setWoId(woId);
				dto.setWorkDate(workDate);
				dto.setPlanId(planId);
				dto.setWoStatus(woStatus);
				dto.setWoQty(woQty);
				dto.setDeleted(deleted);
				dto.setWorker(worker);
				dto.setPlanStatus(planStatus);
				dto.setPlanQty(planQty);
				dto.setPrevQty(prevQty);
				dto.setItemId(itemId);
				dto.setDirector(director);
				dto.setItemName(itemName);
				dto.setUni(unit);
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

			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			conn = dataFactory.getConnection();

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

			rs = ps.executeQuery();

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
				

				WoDTO dto = new WoDTO();
				
				dto.setWoId(woId);
				dto.setWorkDate(workDate);
				dto.setPlanId(planId);
				dto.setWoStatus(woStatus);
				dto.setWoQty(woQty);
				dto.setDeleted(deleted);
				dto.setWorker(worker);
				dto.setPlanStatus(planStatus);
				dto.setPlanQty(planQty);
				dto.setPrevQty(prevQty);
				dto.setItemId(itemId);
				dto.setDirector(director);
				dto.setItemName(itemName);
				dto.setUni(unit);
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

			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			conn = dataFactory.getConnection();

			String query = "SELECT "
					+ "    wo.*, "
					+ "	   worker.ename workerName, "
					+ "    pp.status, "
					+ "    pp.plan_qty, "
					+ "    pp.prev_qty plan_prev, "
					+ "    pp.item_id, "
					+ "    pp.emp_id director, "
					+ "    pp.plan_sdate sdate, "
					+ "    pp.plan_edate edate, "
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

			rs = ps.executeQuery();

			while (rs.next()) {
				
				// wo
				String woId = rs.getString("wo_id");
				Date workDate = rs.getDate("workdate");
				int woStatus = rs.getInt("wostatus_no");
				int woQty = rs.getInt("wo_qty");
				int prevQty = rs.getInt("prev_qty");
				String worker = rs.getString("emp_id");
				String wName = rs.getString("workerName");
				String content = rs.getString("content");
				String deleted = rs.getString("deleted");
				
				// plan
				String planId = rs.getString("plan_id");
				Date sDate = rs.getDate("sDate");
				Date eDate = rs.getDate("eDate");
				int planStatus = rs.getInt("status");
				int planQty = rs.getInt("plan_qty");
				int planPrev = rs.getInt("plan_prev");
				String director = rs.getString("director");
				String dName = rs.getString("directorName");
				
				// item
				String itemId = rs.getString("item_id");
				String itemName = rs.getString("item_name");
				String unit = rs.getString("unit");
				int spec = rs.getInt("spec");
				String group = rs.getString("g_id");
				
				
				// wo
				dto.setWoId(woId);
				dto.setWorkDate(workDate);
				dto.setWoStatus(woStatus);
				dto.setWoQty(woQty);
				dto.setPrevQty(prevQty);
				dto.setWorker(worker);
				dto.setwName(wName);
				dto.setContent(content);
				dto.setDeleted(deleted);
				
				//plan
				dto.setPlanId(planId);
				dto.setsDate(sDate);
				dto.seteDate(eDate);
				dto.setPlanStatus(planStatus);
				dto.setPlanQty(planQty);
				dto.setPlanPrev(planPrev);
				dto.setDirector(director);
				dto.setdName(dName);
				
				// item
				dto.setItemId(itemId);
				dto.setItemName(itemName);
				dto.setUni(unit);
				dto.setSpec(spec);
				dto.setGroup(group);
				
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

			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			conn = dataFactory.getConnection();

			String query = " SELECT count(*) cnt from (select * from work_order where deleted is null) ";
			
			ps= new LoggableStatement(conn, query);
			
			rs = ps.executeQuery();
			
			

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

			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			conn = dataFactory.getConnection();

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
			
			rs = ps.executeQuery();
			
			
			if (rs.next()) {
	            cnt = rs.getInt(1);
	        }
			

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
	
	
	public List<PlanWoDTO> planList () {
		List<PlanWoDTO> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			conn = dataFactory.getConnection();

			String query = "SELECT p.*, d.eName dName "
					+ "FROM production_plan p "
					+ "	LEFT OUTER JOIN user_info d "
					+ "		ON p.emp_id = d.emp_id "
					+ "WHERE p.status=0 or p.status=1 ";
			ps = conn.prepareStatement(query);

			rs = ps.executeQuery();

			while (rs.next()) {
				
				String planId = rs.getString("plan_id");
				int planQty = rs.getInt("plan_qty");
				int prevQty = rs.getInt("prev_qty");
				Date sDate = rs.getDate("plan_sdate");
				Date eDate = rs.getDate("plan_edate");
				int planStatus = rs.getInt("status");
				String itemId = rs.getString("item_id");
				String dId = rs.getString("emp_id");
				String dName = rs.getString("dName");

				PlanWoDTO dto = new PlanWoDTO();
				
				dto.setPlanId(planId);
				dto.setPlanQty(planQty);
				dto.setPrevQty(prevQty);
				dto.setsDate(sDate);
				dto.seteDate(eDate);
				dto.setPlanStatus(planStatus);
				dto.setItemId(itemId);
				dto.setdId(dId);
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
	
	
	public PlanWoDTO getPlan (PlanWoDTO planDTO) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		PlanWoDTO dto = new PlanWoDTO();
		
		try {

			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			conn = dataFactory.getConnection();

			String query = "SELECT p.*, d.eName dName "
					+ "FROM production_plan p "
					+ "	LEFT OUTER JOIN user_info d "
					+ "		ON p.emp_id = d.emp_id "
					+ "WHERE plan_id=? ";
			ps = conn.prepareStatement(query);
			ps.setString(1, planDTO.getPlanId());

			rs = ps.executeQuery();

			while (rs.next()) {
				
				String planId = rs.getString("plan_id");
				int planQty = rs.getInt("plan_qty");
				int prevQty = rs.getInt("prev_qty");
				Date sDate = rs.getDate("plan_sdate");
				Date eDate = rs.getDate("plan_edate");
				int planStatus = rs.getInt("status");
				String itemId = rs.getString("item_id");
				String dId = rs.getString("emp_id");
				String dName = rs.getString("dName");

				dto.setPlanId(planId);
				dto.setPlanQty(planQty);
				dto.setPrevQty(prevQty);
				dto.setsDate(sDate);
				dto.seteDate(eDate);
				dto.setPlanStatus(planStatus);
				dto.setItemId(itemId);
				dto.setdId(dId);
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
	
	public List<UserWoDTO> searchWorker(String keyword) {
	    List<UserWoDTO> list = new ArrayList<>();
	    
	    Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
	    try {
			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			conn = dataFactory.getConnection();
			
			String sql = "SELECT emp_id, eName " +
	    				"FROM user_info " +
	    				"WHERE dept_no = 10 and retire is null and (upper(emp_id) LIKE upper(?) OR upper(eName) LIKE upper(?))";
			
			ps = conn.prepareStatement(sql);
			
			String param = "%" + (keyword == null ? "" : keyword) + "%";
	        ps.setString(1, param);
	        ps.setString(2, param);

	        rs = ps.executeQuery();

	        while (rs.next()) {
	            UserWoDTO dto = new UserWoDTO();
	            dto.setEmpId(rs.getString("emp_id"));
	            dto.seteName(rs.getString("eName"));
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
	
	
	
	public int addOrder(WoAddDTO dto) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int result = -1;

		try {

			// JNDI 방식
			// context.xml에 있는 DB 정보로 커넥션 풀을 가져온다
			Context ctx = new InitialContext();
			// DataSource : 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			// DB 접속(그런데 이제 커넥션 풀로)
			conn = dataFactory.getConnection();

			// SQL 준비
			String query = "INSERT INTO WORK_order (wo_id, workdate, plan_id, wostatus_no, wo_qty, emp_id, content) "
					+ "VALUES ('wo_'||wo_seq.nextval, to_date(?, 'yyyy-MM-dd'), ?, 10, ?, ?, ?) ";
			
			ps = new LoggableStatement(conn, query);
			
			ps.setString(1, dto.getWorkDate());
			ps.setString(2, dto.getPlanId());
			ps.setInt(3, dto.getWoQty());
			ps.setString(4, dto.getWorker());
			ps.setString(5,  dto.getContent());

			// SQL 실행 및 결과 확보
			result = ps.executeUpdate();

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

		return result;
	} // add
	

	
	public int modifyOrder(WoAddDTO dto) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int result = -1;
		
		try {

			// JNDI 방식
			// context.xml에 있는 DB 정보로 커넥션 풀을 가져온다
			Context ctx = new InitialContext();
			// DataSource : 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			// DB 접속(그런데 이제 커넥션 풀로)
			conn = dataFactory.getConnection();

			// SQL 준비
			String query = "UPDATE WORK_ORDER "
					+ "SET workdate = to_date(?, 'yyyy-MM-dd'), "
					+ "	wo_qty = ?, "
					+ "	emp_id = ?, "
					+ "	content = ? "
					+ "WHERE wo_id = ?";
			
			ps = new LoggableStatement(conn, query);
			
			ps.setString(1, dto.getWorkDate());
			ps.setInt(2, dto.getWoQty());
			ps.setString(3, dto.getWorker());
			ps.setString(4, dto.getContent());
			ps.setString(5, dto.getWoId());

			// SQL 실행 및 결과 확보
			result = ps.executeUpdate();

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

		return result;
	} // modify
	
	
	public int deleteOrder(String woId) {
		System.out.println("DTO : " + woId);
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int result = -1;
		
		try {
			
			// JNDI 방식
			// context.xml에 있는 DB 정보로 커넥션 풀을 가져온다
			Context ctx = new InitialContext();
			// DataSource : 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
			
			// DB 접속(그런데 이제 커넥션 풀로)
			conn = dataFactory.getConnection();
			
			// SQL 준비
			String query = "UPDATE WORK_ORDER SET deleted = 'Y' WHERE wo_id=?";
			
			ps = new LoggableStatement(conn, query);
			
			ps.setString(1, woId);
			
			// SQL 실행 및 결과 확보
			result = ps.executeUpdate();
			
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
		
		return result;
	} // delete
	
	public int updateContent(String woId, int status, int prevQty) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int result = -1;
		
		try {

			// JNDI 방식
			// context.xml에 있는 DB 정보로 커넥션 풀을 가져온다
			Context ctx = new InitialContext();
			// DataSource : 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			// DB 접속(그런데 이제 커넥션 풀로)
			conn = dataFactory.getConnection();

			// SQL 준비
			String query = "UPDATE work_order "
					+ "SET wostatus_no=?, prev_qty=? "
					+ "WHERE wo_id=?";
			
			ps = new LoggableStatement(conn, query);
			
			ps.setInt(1, status);
			ps.setInt(2, prevQty);
			ps.setString(3, woId);

			// SQL 실행 및 결과 확보
			result = ps.executeUpdate();

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

		return result;
	} // modify

}
