package P08_quality;

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
import P07_work.SearchDTO;
import P07_work.WoAddDTO;
import P07_work.WoDTO;

public class QcDAO {
	
	public List<QcDTO> getList (int start, int end) {
		List<QcDTO> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			conn = dataFactory.getConnection();

			String query = "SELECT q.*, d.eName dName, w.eName wName, wo.WO_QTY qty, wo.itemID, wo.iNAME, NVL(def_sum.def_cnt, 0) def_sum FROM QUALITY_CHECK q "
					+ "LEFT OUTER JOIN user_info w "
					+ "	ON q.worker = w.emp_id "
					+ "LEFT OUTER JOIN user_info d "
					+ "	ON q.director = d.EMP_ID  "
					+ "LEFT OUTER JOIN ( "
					+ "	SELECT * "
					+ "	FROM WORK_ORDER wo "
					+ "	LEFT OUTER JOIN ( "
					+ "		SELECT p.plan_id, p.ITEM_ID  itemId, i.ITEM_NAME iName "
					+ "		FROM production_plan p "
					+ "		LEFT OUTER JOIN item i "
					+ "			ON p.ITEM_ID = i.item_id "
					+ "		) p "
					+ "		ON wo.PLAN_ID = p.plan_Id "
					+ ") wo "
					+ "	ON q.WO_ID = wo.WO_ID "
					+ "LEFT OUTER JOIN ( "
					+ "	SELECT qc_id, sum(defect_cnt) def_cnt "
					+ "	FROM defect "
					+ "	GROUP BY qc_id "
					+ ") def_sum "
					+ "	ON q.qc_id = def_sum.qc_id "
					+ "WHERE q.deleted IS null "
					+ "order by q.qc_id desc";
			ps = conn.prepareStatement(query);

			rs = ps.executeQuery();

			while (rs.next()) {
				
				// wo
				String woId = rs.getString("wo_id");
				int qty = rs.getInt("qty");
				String itemId = rs.getString("itemId");
				String iName = rs.getString("iName");
				
				// qc
				String qcId = rs.getString("qc_id");
				Date sDate = rs.getDate("qc_sdate");
				Date eDate = rs.getDate("qc_edate");;
				int qcStatus = rs.getInt("qcstatus_no");;
				String content = rs.getString("content");
				
				// user
				String dId = rs.getString("director");
				String dName = rs.getString("dName");
				String wId = rs.getString("worker");
				String wName = rs.getString("wName");
				
				// defect
				int defSum = rs.getInt("def_sum");
				

				QcDTO dto = new QcDTO();
				
				dto.setWoId(woId);
				dto.setQty(qty);
				dto.setItemId(itemId);
				dto.setiName(iName);
				
				dto.setQcId(qcId);
				dto.setsDate(sDate);
				dto.seteDate(eDate);
				dto.setQcStatus(qcStatus);
				dto.setContent(content);
				
				dto.setdId(dId);
				dto.setdName(dName);
				dto.setwId(wId);
				dto.setwName(wName);
				
				dto.setDefSum(defSum);
				
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
	
	
	public QcCardDTO getCard () {
		
		QcCardDTO dto = new QcCardDTO();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			conn = dataFactory.getConnection();

			String query = "SELECT "
					+ "    nvl(SUM(wo.wo_qty), 0) AS total_qty, "
					+ "    NVL(SUM(def_sum.def_cnt), 0) AS total_defect, "
					+ "    nvl(SUM(wo.wo_qty) - NVL(SUM(def_sum.def_cnt), 0), 0) AS pass_qty, "
					+ "    nvl (ROUND( "
					+ "        AVG( "
					+ "            CASE  "
					+ "                WHEN wo.wo_qty = 0 THEN 0 "
					+ "                ELSE NVL(def_sum.def_cnt, 0) / wo.wo_qty "
					+ "            END "
					+ "        ) * 100 "
					+ "    , 1), 0) AS avg_defect_rate "
					+ "FROM quality_check qc "
					+ "JOIN work_order wo "
					+ "    ON qc.wo_id = wo.wo_id "
					+ "LEFT OUTER JOIN ( "
					+ "    SELECT qc_id, SUM(defect_cnt) AS def_cnt "
					+ "    FROM defect "
					+ "    GROUP BY qc_id "
					+ ") def_sum "
					+ "    ON qc.qc_id = def_sum.qc_id "
					+ "WHERE qc.qcstatus_no = 30 "
					+ "  AND qc.deleted IS NULL "
					+ "  AND wo.deleted IS NULL "
					+ "  AND qc.qc_edate >= TRUNC(SYSDATE) "
					+ "  AND qc.qc_edate < TRUNC(SYSDATE) + 1";
			ps = conn.prepareStatement(query);

			rs = ps.executeQuery();

			while (rs.next()) {
				
				int total = rs.getInt("total_qty");
				int defect = rs.getInt("total_defect");
				int pass = rs.getInt("pass_qty");
				Double avgDefect = rs.getDouble("avg_defect_rate");
				
				dto.setTotal(total);
				dto.setDefect(defect);
				dto.setPass(pass);
				dto.setAvgDefect(avgDefect);

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
	} // getCard
	
	
	public List<QcDTO> search (int start, int end, SearchDTO search) {
		List<QcDTO> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			conn = dataFactory.getConnection();

			String query = "SELECT q.*, d.eName dName, w.eName wName, wo.WO_QTY qty, wo.itemID, wo.iNAME, NVL(def_sum.def_cnt, 0) def_sum FROM QUALITY_CHECK q "
					+ "LEFT OUTER JOIN user_info w "
					+ "	ON q.worker = w.emp_id "
					+ "LEFT OUTER JOIN user_info d "
					+ "	ON q.director = d.EMP_ID  "
					+ "LEFT OUTER JOIN ( "
					+ "	SELECT * "
					+ "	FROM WORK_ORDER wo "
					+ "	LEFT OUTER JOIN ( "
					+ "		SELECT p.plan_id, p.ITEM_ID  itemId, i.ITEM_NAME iName "
					+ "		FROM production_plan p "
					+ "		LEFT OUTER JOIN item i "
					+ "			ON p.ITEM_ID = i.item_id "
					+ "		) p "
					+ "		ON wo.PLAN_ID = p.plan_Id "
					+ ") wo "
					+ "	ON q.WO_ID = wo.WO_ID "
					+ "LEFT OUTER JOIN ( "
					+ "	SELECT qc_id, sum(defect_cnt) def_cnt "
					+ "	FROM defect "
					+ "	GROUP BY qc_id "
					+ ") def_sum "
					+ "	ON q.qc_id = def_sum.qc_id "
					+ "WHERE q.deleted IS null ";
			
			if (search.getStatus() != 0) {
				query += " and q.qcstatus_no = " + search.getStatus();
			}
			
			int idx = 1;

			if (search.getsDate() != null && !search.getsDate().isEmpty()) {
			    query += " and qc_edate >= TO_DATE(?, 'YYYY-MM-DD') ";
			}

			if (search.geteDate() != null && !search.geteDate().isEmpty()) {
			    query += " and qc_edate <= TO_DATE(?, 'YYYY-MM-DD') ";
			}
			
			if (!( "".equals(search.getKeyword()) )) {
				query += " and (upper(wo.iName) like upper(?) or upper(w.ename) like upper(?)) ";
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

				// wo
				String woId = rs.getString("wo_id");
				int qty = rs.getInt("qty");
				String itemId = rs.getString("itemId");
				String iName = rs.getString("iName");
				
				// qc
				String qcId = rs.getString("qc_id");
				Date sDate = rs.getDate("qc_sdate");
				Date eDate = rs.getDate("qc_edate");;
				int qcStatus = rs.getInt("qcstatus_no");;
				String content = rs.getString("content");
				
				// user
				String dId = rs.getString("director");
				String dName = rs.getString("dName");
				String wId = rs.getString("worker");
				String wName = rs.getString("wName");
				
				// defect
				int defSum = rs.getInt("def_sum");
				

				QcDTO dto = new QcDTO();
				
				dto.setWoId(woId);
				dto.setQty(qty);
				dto.setItemId(itemId);
				dto.setiName(iName);
				
				dto.setQcId(qcId);
				dto.setsDate(sDate);
				dto.seteDate(eDate);
				dto.setQcStatus(qcStatus);
				dto.setContent(content);
				
				dto.setdId(dId);
				dto.setdName(dName);
				dto.setwId(wId);
				dto.setwName(wName);
				
				dto.setDefSum(defSum);
				
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
	
	public int countSearch(SearchDTO search) {
		
		int cnt = 0;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			conn = dataFactory.getConnection();

			String query = "SELECT count(*) "
					+ "FROM QUALITY_CHECK q "
					+ "LEFT OUTER JOIN user_info w  "
					+ "	ON q.worker = w.emp_id "
					+ "LEFT OUTER JOIN user_info d "
					+ "	ON q.director = d.EMP_ID  "
					+ "LEFT OUTER JOIN ( "
					+ "		SELECT * "
					+ "		FROM WORK_ORDER wo "
					+ "		LEFT OUTER JOIN ( "
					+ "			SELECT p.plan_id, p.ITEM_ID  itemId, i.ITEM_NAME iName	 "
					+ "			FROM production_plan p "
					+ "			LEFT OUTER JOIN item i "
					+ "				ON p.ITEM_ID = i.item_id "
					+ "			) p "
					+ "			ON wo.PLAN_ID = p.plan_Id "
					+ "		) wo "
					+ "	ON q.WO_ID = wo.WO_ID "
					+ "LEFT OUTER JOIN ( "
					+ "		SELECT qc_id, sum(defect_cnt) def_cnt "
					+ "		FROM defect "
					+ "		GROUP BY qc_id) def_sum "
					+ "	ON q.qc_id = def_sum.qc_id "
					+ "WHERE q.deleted IS null";
			
	        if (search.getStatus() != 0) {
	            query += " AND q.qcstatus_no = " + search.getStatus();
	        }

	        int idx = 1;

	        if (search.getsDate() != null && !search.getsDate().isEmpty()) {
	            query += " AND q.qc_edate >= TO_DATE(?, 'YYYY-MM-DD') ";
	        }

	        if (search.geteDate() != null && !search.geteDate().isEmpty()) {
	            query += " AND q.qc_edate <= TO_DATE(?, 'YYYY-MM-DD') ";
	        }

	        if (search.getKeyword() != null && !search.getKeyword().isEmpty()) {
	            query += " AND (UPPER(wo.iName) LIKE UPPER(?) "
	                   + " OR UPPER(w.ename) LIKE UPPER(?)) ";
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
	
	
	public int count() {
		
		int cnt = 0;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			conn = dataFactory.getConnection();

			String query = " SELECT count(*) cnt from (select * from quality_check where deleted is null) ";
			
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
	
	
	public QcDTO detail (QcDTO dto) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			conn = dataFactory.getConnection();

			String query = "SELECT q.*, d.eName dName, w.eName wName, wo.WO_QTY qty, wo.itemID, wo.iNAME, NVL(def_sum.def_cnt, 0) def_sum FROM QUALITY_CHECK q "
					+ "LEFT OUTER JOIN user_info w "
					+ "	ON q.worker = w.emp_id "
					+ "LEFT OUTER JOIN user_info d "
					+ "	ON q.director = d.EMP_ID  "
					+ "LEFT OUTER JOIN ( "
					+ "	SELECT * "
					+ "	FROM WORK_ORDER wo "
					+ "	LEFT OUTER JOIN ( "
					+ "		SELECT p.plan_id, p.ITEM_ID  itemId, i.ITEM_NAME iName "
					+ "		FROM production_plan p "
					+ "		LEFT OUTER JOIN item i "
					+ "			ON p.ITEM_ID = i.item_id "
					+ "		) p "
					+ "		ON wo.PLAN_ID = p.plan_Id "
					+ ") wo "
					+ "	ON q.WO_ID = wo.WO_ID "
					+ "LEFT OUTER JOIN ( "
					+ "	SELECT qc_id, sum(defect_cnt) def_cnt "
					+ "	FROM defect "
					+ "	where deleted is null "
					+ "	GROUP BY qc_id "
					+ ") def_sum "
					+ "	ON q.qc_id = def_sum.qc_id "
					+ "WHERE q.deleted IS null "
					+ "and q.qc_id = ?";

			ps = conn.prepareStatement(query);
			ps.setString(1, dto.getQcId());

			rs = ps.executeQuery();

			while (rs.next()) {
				
				// wo
				String woId = rs.getString("wo_id");
				int qty = rs.getInt("qty");
				String itemId = rs.getString("itemId");
				String iName = rs.getString("iName");
				
				// qc
				String qcId = rs.getString("qc_id");
				Date sDate = rs.getDate("qc_sdate");
				Date eDate = rs.getDate("qc_edate");;
				int qcStatus = rs.getInt("qcstatus_no");;
				String content = rs.getString("content");
				
				// user
				String dId = rs.getString("director");
				String dName = rs.getString("dName");
				String wId = rs.getString("worker");
				String wName = rs.getString("wName");
				
				// defect
				int defSum = rs.getInt("def_sum");
				

				dto = new QcDTO();
				
				dto.setWoId(woId);
				dto.setQty(qty);
				dto.setItemId(itemId);
				dto.setiName(iName);
				
				dto.setQcId(qcId);
				dto.setsDate(sDate);
				dto.seteDate(eDate);
				dto.setQcStatus(qcStatus);
				dto.setContent(content);
				
				dto.setdId(dId);
				dto.setdName(dName);
				dto.setwId(wId);
				dto.setwName(wName);
				
				dto.setDefSum(defSum);
				
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
	
	
	
	public List<QcDefDTO> defContent (String qcId) {
		List<QcDefDTO> defList = new ArrayList<>();
		

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			conn = dataFactory.getConnection();

			String query = "SELECT d.*, dt.DTYPE_NAME  "
					+ "FROM DEFECT d "
					+ "	LEFT OUTER JOIN DEFECT_TYPE dt "
					+ "		ON d.DTYPE_NO = dt.DTYPE_No "
					+ "WHERE d.qc_id=? AND d.deleted IS null ";

			ps = conn.prepareStatement(query);
			ps.setString(1, qcId);

			rs = ps.executeQuery();

			while (rs.next()) {
				
				String defId = rs.getString("defect_id");
				int dType = rs.getInt("dtype_no");
				String dtName = rs.getString("dtype_name");
				int defCnt = rs.getInt("defect_cnt");
				String solution = rs.getString("solution");
				String dispose = rs.getString("dispose");
				
				QcDefDTO defDTO = new QcDefDTO();
				
				defDTO.setDefId(defId);
				defDTO.setdType(dType);
				defDTO.setDtName(dtName);
				defDTO.setDefCnt(defCnt);
				defDTO.setSolution(solution);
				defDTO.setDispose(dispose);
				
				defList.add(defDTO);
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
		
		return defList;
	}
	
	
	
	public List getWoList () {
		List list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			conn = dataFactory.getConnection();

			String query = "SELECT wo_id from work_order where wostatus_no=30 and deleted is null ";
			ps = conn.prepareStatement(query);

			rs = ps.executeQuery();

			while (rs.next()) {
				
				// wo
				String woId = rs.getString("wo_id");
//				int woStatus = rs.getInt("wostatus_no");
//				int qty = rs.getInt("wo_qty");
//				Date woDate = rs.getDate("workdate");
//				String itemId = rs.getString("item_id");
//				String iName = rs.getString("iName");
//
//				WoDTO dto = new WoDTO();
//				
//				dto.setWoId(woId);
//				dto.setWoStatus(woStatus);
//				dto.setWoQty(qty);
//				dto.setWorkDate(woDate);
//				dto.setItemId(itemId);
//				dto.setItemName(iName);
				
				list.add(woId);

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
	} // getWoList
	
	
	
	public WoDTO setWo (WoDTO dto) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			conn = dataFactory.getConnection();

			String query = "SELECT wo.wo_id, wo.wostatus_no, wo.wo_qty, wo.WORKDATE, i.ITEM_ID, i.ITEM_NAME "
					+ "FROM WORK_ORDER wo "
					+ "	LEFT OUTER JOIN production_plan p "
					+ "		ON wo.plan_id = p.PLAN_ID "
					+ "	LEFT OUTER JOIN item i "
					+ "		ON p.ITEM_ID = i.ITEM_ID "
					+ "WHERE wo.wo_id = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, dto.getWoId());

			rs = ps.executeQuery();

			while (rs.next()) {
				
				String woId = rs.getString("wo_id");
				int woStatus = rs.getInt("wostatus_no");
				int qty = rs.getInt("wo_qty");
				Date woDate = rs.getDate("workdate");
				String itemId = rs.getString("item_id");
				String iName = rs.getString("item_Name");

				dto.setWoId(woId);
				dto.setWoStatus(woStatus);
				dto.setWoQty(qty);
				dto.setWorkDate(woDate);
				dto.setItemId(itemId);
				dto.setItemName(iName);
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
	} // getWo
	
	
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
    				"WHERE dept_no = 20 and retire is null and (upper(emp_id) LIKE upper(?) OR upper(eName) LIKE upper(?))";
			
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
	} // searchWorker
	
	
	public int addQc(QcAddDTO dto) {

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
			String query = "INSERT INTO quality_check (qc_id, qc_sdate, qcstatus_no, content, director, worker, wo_id) "
					+ "VALUES ('qc_'||qc_seq.nextval, ?, 10, ?, ?, ?, ?) ";
			
			ps = new LoggableStatement(conn, query);
			
			ps.setDate(1, dto.getsDate());
			ps.setString(2, dto.getContent());
			ps.setString(3, dto.getDirector());
			ps.setString(4, dto.getWorker());
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
	} // addQc
	
	
	
	public QcDTO getQc (String qcId) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		QcDTO dto = new QcDTO();

		try {

			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			conn = dataFactory.getConnection();

			String query = "SELECT q.*, d.eName dName, w.eName wName, wo.WO_QTY qty, wo.itemID, wo.iNAME, wo.workdate, NVL(def_sum.def_cnt, 0) def_sum FROM QUALITY_CHECK q "
					+ "LEFT OUTER JOIN user_info w "
					+ "	ON q.worker = w.emp_id "
					+ "LEFT OUTER JOIN user_info d "
					+ "	ON q.director = d.EMP_ID  "
					+ "LEFT OUTER JOIN ( "
					+ "	SELECT * "
					+ "	FROM WORK_ORDER wo "
					+ "	LEFT OUTER JOIN ( "
					+ "		SELECT p.plan_id, p.ITEM_ID  itemId, i.ITEM_NAME iName "
					+ "		FROM production_plan p "
					+ "		LEFT OUTER JOIN item i "
					+ "			ON p.ITEM_ID = i.item_id "
					+ "		) p "
					+ "		ON wo.PLAN_ID = p.plan_Id "
					+ ") wo "
					+ "	ON q.WO_ID = wo.WO_ID "
					+ "LEFT OUTER JOIN ( "
					+ "	SELECT qc_id, sum(defect_cnt) def_cnt "
					+ "	FROM defect "
					+ "	GROUP BY qc_id "
					+ ") def_sum "
					+ "	ON q.qc_id = def_sum.qc_id "
					+ "WHERE q.deleted IS null and q.qc_id = ?";
			
			ps = conn.prepareStatement(query);
			ps.setString(1, qcId);

			rs = ps.executeQuery();

			while (rs.next()) {
				
				// wo
				String woId = rs.getString("wo_id");
				int qty = rs.getInt("qty");
				String itemId = rs.getString("itemId");
				String iName = rs.getString("iName");
				Date workDate = rs.getDate("workdate");
				
				// qc
				Date sDate = rs.getDate("qc_sdate");
				Date eDate = rs.getDate("qc_edate");;
				int qcStatus = rs.getInt("qcstatus_no");;
				String content = rs.getString("content");
				
				// user
				String dId = rs.getString("director");
				String dName = rs.getString("dName");
				String wId = rs.getString("worker");
				String wName = rs.getString("wName");
				
				// defect
				int defSum = rs.getInt("def_sum");
			
				dto.setWoId(woId);
				dto.setQty(qty);
				dto.setItemId(itemId);
				dto.setiName(iName);
				dto.setWorkDate(workDate);
				
				dto.setQcId(qcId);
				dto.setsDate(sDate);
				dto.seteDate(eDate);
				dto.setQcStatus(qcStatus);
				dto.setContent(content);
				
				dto.setdId(dId);
				dto.setdName(dName);
				dto.setwId(wId);
				dto.setwName(wName);
				
				dto.setDefSum(defSum);
				
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
	} // getQc
	
	
	public int modifyOrder(String qcId, QcAddDTO dto) {

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
			String query = "UPDATE QUALITY_CHECK "
					+ "SET qc_sdate = ?, content = ?, worker = ? "
					+ "WHERE qc_id=?";
			
			ps = new LoggableStatement(conn, query);
			
			ps.setDate(1, dto.getsDate());
			ps.setString(2, dto.getContent());
			ps.setString(3, dto.getWorker());
			ps.setString(4, qcId);

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
	} // modifyOrder
	
	
	public int deleteQc(String qcId) {

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
			String query = "UPDATE QUALITY_CHECK "
					+ "set deleted='Y' "
					+ "WHERE qc_id=?";
			
			ps = new LoggableStatement(conn, query);
			
			ps.setString(1, qcId);

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
	} // deleteQc
	
	
	public int addDef(String qcId, QcDefDTO dto) {

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
			String seqSql = "SELECT 'def_id'||defect_seq.NEXTVAL FROM dual";
			ps = conn.prepareStatement(seqSql);
			rs = ps.executeQuery();

			String defectId = null;
			if (rs.next()) {
			    defectId = rs.getString(1);
			}
			
			String query = "INSERT INTO DEFECT (defect_id, dtype_no, defect_cnt, solution, qc_id, dispose) "
					+ "VALUES (?, ?, ?, ?, ?, ?) ";
			
			ps = new LoggableStatement(conn, query);
			
			ps.setString(1, defectId);
			ps.setInt(2, dto.getdType());
			ps.setInt(3, dto.getDefCnt());
			ps.setString(4, dto.getSolution());
			ps.setString(5, qcId);
			ps.setString(6, dto.getDispose());

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
	} // addDef
	
	
	public QcDisposeDTO disposeSum(QcDisposeDTO disDTO) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			conn = dataFactory.getConnection();

			String query = "SELECT  "
					+ "    qc_id, "
					+ "    SUM(CASE WHEN dispose = 'Y' THEN defect_cnt ELSE 0 END) AS dispose_qty, "
					+ "    SUM(CASE WHEN dispose IS NULL THEN defect_cnt ELSE 0 END) AS rework_qty "
					+ "FROM defect "
					+ "WHERE qc_id = ? and deleted is null "
					+ "GROUP BY qc_id";
			
			ps = conn.prepareStatement(query);
			ps.setString(1, disDTO.getQcId());

			rs = ps.executeQuery();

			while (rs.next()) {
				
				int dispose = rs.getInt("dispose_qty");
				int rework = rs.getInt("rework_qty");

				disDTO.setDispose(dispose);
				disDTO.setRework(rework);

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
		
		return disDTO;
	} // disposeSum
	
	
	public int updateDef(QcDefDTO dto) {

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
			String query = "UPDATE defect "
					+ "SET dtype_no = ?, defect_cnt = ?, solution = ?, dispose = ? "
					+ "WHERE defect_id=?";
			
			ps = new LoggableStatement(conn, query);
			
			ps.setInt(1, dto.getdType());
			ps.setInt(2, dto.getDefCnt());
			ps.setString(3, dto.getSolution());
			ps.setString(4, dto.getDispose());
			ps.setString(5, dto.getDefId());

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
	} // updateDef
	
	
	
	public int deleteDef(String defId) {

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
			String query = "UPDATE defect "
					+ "SET deleted = 'Y' "
					+ "WHERE defect_id=?";
			
			ps = new LoggableStatement(conn, query);
			
			ps.setString(1, defId);

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
	} // deleteDef
	
	
	
	public int modifyResult(QcDTO dto) {

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
			String query = "UPDATE quality_check "
					+ "SET qc_edate = ?, qcstatus_no = ? "
					+ "WHERE qc_id = ?";
			
			ps = new LoggableStatement(conn, query);
			
			ps.setDate(1, dto.geteDate());
			ps.setInt(2, dto.getQcStatus());
			ps.setString(3, dto.getQcId());

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
	} // modifyResult
	
}
