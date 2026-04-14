package P09_equip;

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
import P07_work.SearchDTO;
import P07_work.WoDTO;
import P09_equip.DTO.EqDTO;
import P09_equip.DTO.EqSearchDTO;

public class EqDAO {
	
	public List<EqDTO> getList(int start, int end) {
		
	List<EqDTO> list = new ArrayList<>();
			
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			conn = dataFactory.getConnection();

			String query = "SELECT * "
					+ "FROM ( "
					+ "    SELECT ROWNUM rnum, inner_query.* "
					+ "    FROM ( "
					+ "        SELECT  "
					+ "            e.eq_id, "
					+ "            e.eq_name, "
					+ "            e.eq_status, "
					+ "            ROUND((SYSDATE - e.eq_startdate) * 24 * 60) AS total_min, "
					+ "            ROUND(NVL(SUM((NVL(r.etime, SYSDATE) - r.stime) * 24 * 60), 0)) AS run_min, "
					+ "            ROUND( "
					+ "                (SYSDATE - e.eq_startdate) * 24 * 60 "
					+ "                - NVL(SUM((NVL(r.etime, SYSDATE) - r.stime) * 24 * 60), 0) "
					+ "            , 2) AS stop_min, "
					+ "            ROUND( "
					+ "                CASE  "
					+ "                    WHEN (SYSDATE - e.eq_startdate) = 0 THEN 0 "
					+ "                    ELSE  "
					+ "                        NVL(SUM((NVL(r.etime, SYSDATE) - r.stime)), 0)  "
					+ "                        / (SYSDATE - e.eq_startdate) * 100 "
					+ "                END "
					+ "            , 2) AS run_rate "
					+ "        FROM equipment e "
					+ "        LEFT JOIN eqrun_log r "
					+ "            ON e.eq_id = r.eq_id "
					+ "        GROUP BY e.eq_id, e.eq_name, e.eq_startdate, e.eq_status "
					+ "        ORDER BY e.eq_id "
					+ "    ) inner_query "
					+ ") "
					+ "WHERE rnum BETWEEN ? AND ?";
			
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, start);
			ps.setInt(2, end);

			rs = ps.executeQuery();

			while (rs.next()) {
				
				// eq
				String eqId = rs.getString("eq_id");
				String eqName = rs.getString("eq_name");
				
				// runTime
				int totalMin = rs.getInt("total_min");
				int runMin = rs.getInt("run_min");
				int stopMin = rs.getInt("stop_min");
				double runRate = rs.getDouble("run_rate");
				String status = rs.getString("eq_status");
				

				EqDTO dto = new EqDTO();
				
				dto.setEqId(eqId);
				dto.setEqName(eqName);
				dto.setTotalMin(totalMin);
				dto.setRunMin(runMin);
				dto.setStopMin(stopMin);
				dto.setRunRate(runRate);
				dto.setStatus(status);
				
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
		
	} // getList
	
	
	public int count() {
		
		int cnt = 0;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			conn = dataFactory.getConnection();

			String query = " SELECT count(*) cnt from equipment ";
			
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
	
	
	public List<EqDTO> getAllList() {
		
		List<EqDTO> list = new ArrayList<>();
				
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			try {

				Context ctx = new InitialContext();
				DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

				conn = dataFactory.getConnection();

				String query = "SELECT  "
						+ "    e.eq_id, "
						+ "    e.eq_name, "
						+ "    e.eq_status, "
						+ "    ROUND((SYSDATE - e.eq_startdate) * 24 * 60) total_min, "
						+ "    ROUND(NVL(SUM((NVL(r.etime, SYSDATE) - r.stime) * 24 * 60), 0)) run_min, "
						+ "    ROUND( "
						+ "        (SYSDATE - e.eq_startdate) * 24 * 60 "
						+ "        - NVL(SUM((NVL(r.etime, SYSDATE) - r.stime) * 24 * 60), 0) "
						+ "    , 2) stop_min, "
						+ "    ROUND( "
						+ "        CASE  "
						+ "            WHEN (SYSDATE - e.eq_startdate) = 0 THEN 0 "
						+ "            ELSE  "
						+ "                NVL(SUM((NVL(r.etime, SYSDATE) - r.stime)), 0)  "
						+ "                / (SYSDATE - e.eq_startdate) * 100 "
						+ "        END "
						+ "    , 2) run_rate "
						+ "FROM equipment e "
						+ "LEFT JOIN eqrun_log r "
						+ "    ON e.eq_id = r.eq_id "
						+ "GROUP BY e.eq_id, e.eq_name, e.eq_startdate, e.eq_status "
						+ "ORDER BY e.eq_id";
				
				ps = conn.prepareStatement(query);

				rs = ps.executeQuery();

				while (rs.next()) {
					
					// eq
					String eqId = rs.getString("eq_id");
					String eqName = rs.getString("eq_name");
					
					// runTime
					int totalMin = rs.getInt("total_min");
					int runMin = rs.getInt("run_min");
					int stopMin = rs.getInt("stop_min");
					double runRate = rs.getDouble("run_rate");
					String status = rs.getString("eq_status");
					

					EqDTO dto = new EqDTO();
					
					dto.setEqId(eqId);
					dto.setEqName(eqName);
					dto.setTotalMin(totalMin);
					dto.setRunMin(runMin);
					dto.setStopMin(stopMin);
					dto.setRunRate(runRate);
					dto.setStatus(status);
					
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
			
		} // getList
	
	
	public List<EqDTO> search (int start, int end, EqSearchDTO search) {
		List<EqDTO> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			conn = dataFactory.getConnection();

			String query = "SELECT * "
				+ "FROM ( "
				+ "    SELECT ROWNUM rnum, inner_query.* "
				+ "    FROM ( "
				+ "        SELECT  "
				+ "            e.eq_id, "
				+ "            e.eq_name, "
				+ "            e.eq_status, "
				+ "            ROUND((SYSDATE - e.eq_startdate) * 24 * 60) total_min, "
				+ "            ROUND(NVL(SUM((NVL(r.etime, SYSDATE) - r.stime) * 24 * 60), 0)) run_min, "
				+ "            ROUND( "
				+ "                (SYSDATE - e.eq_startdate) * 24 * 60 "
				+ "                - NVL(SUM((NVL(r.etime, SYSDATE) - r.stime) * 24 * 60), 0) "
				+ "            , 2) stop_min, "
				+ "            ROUND( "
				+ "                CASE "
				+ "                    WHEN (SYSDATE - e.eq_startdate) = 0 THEN 0 "
				+ "                    ELSE NVL(SUM((NVL(r.etime, SYSDATE) - r.stime)), 0) "
				+ "                         / (SYSDATE - e.eq_startdate) * 100 "
				+ "                END "
				+ "            , 2) run_rate "
				+ "        FROM equipment e "
				+ "        LEFT JOIN eqrun_log r "
				+ "            ON e.eq_id = r.eq_id "
				+ "        WHERE e.eq_id IS NOT NULL ";
			
			if (!search.getStatus().isEmpty()) {
			    query += " AND e.eq_status = ? ";
			}

			if (search.getKeyword() != null && !search.getKeyword().isEmpty()) {
			    query += " AND (UPPER(e.eq_name) LIKE UPPER(?) OR UPPER(e.eq_id) LIKE UPPER(?)) ";
			}
			
			query += " GROUP BY e.eq_id, e.eq_name, e.eq_startdate, e.eq_status "
					+ " ORDER BY e.eq_id "
					+ "    ) inner_query "
					+ ") "
					+ "WHERE rnum BETWEEN ? AND ?";
			
			ps = conn.prepareStatement(query);
			
			int idx = 1;

			if (search.getStatus() != null && !search.getStatus().isEmpty()) {
			    ps.setString(idx++, search.getStatus());
			}

			if (search.getKeyword() != null && !search.getKeyword().isEmpty()) {
			    String keyword = "%" + search.getKeyword() + "%";
			    ps.setString(idx++, keyword);
			    ps.setString(idx++, keyword);
			}

			ps.setInt(idx++, start);
			ps.setInt(idx++, end);

			
			rs = ps.executeQuery();

			while (rs.next()) {

				// eq
				String eqId = rs.getString("eq_id");
				String eqName = rs.getString("eq_name");
				
				// runTime
				int totalMin = rs.getInt("total_min");
				int runMin = rs.getInt("run_min");
				int stopMin = rs.getInt("stop_min");
				double runRate = rs.getDouble("run_rate");
				String status = rs.getString("eq_status");
				
				EqDTO dto = new EqDTO();
				
				dto.setEqId(eqId);
				dto.setEqName(eqName);
				
				dto.setTotalMin(totalMin);
				dto.setRunMin(runMin);
				dto.setStopMin(stopMin);
				dto.setRunRate(runRate);
				dto.setStatus(status);
				
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
	} // search
	
	public int countSearch(EqSearchDTO search) {
		
		int cnt = 0;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			conn = dataFactory.getConnection();

			String query = "SELECT COUNT(*) "
					+ "FROM ( "
					+ "    SELECT  "
					+ "        e.eq_id "
					+ "    FROM equipment e "
					+ "    LEFT JOIN eqrun_log r "
					+ "        ON e.eq_id = r.eq_id "
					+ "    WHERE e.eq_id is not null";
			
			if (!search.getStatus().isEmpty()) {
			    query += " AND e.eq_status = ? ";
			}

			if (search.getKeyword() != null && !search.getKeyword().isEmpty()) {
			    query += " AND (UPPER(e.eq_name) LIKE UPPER(?) OR UPPER(e.eq_id) LIKE UPPER(?)) ";
			}

			query += "    GROUP BY e.eq_id, e.eq_name, e.eq_startdate, e.eq_status "
					+ ")";
			
			ps= new LoggableStatement(conn, query);
			
			int idx = 1;
			
			if (search.getStatus() != null && !search.getStatus().isEmpty()) {
			    ps.setString(idx++, search.getStatus());
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
	

}
