package P11_masterdata.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import P11_masterdata.DTO.ProcessDTO;
import P11_masterdata.DTO.ProcessDTO;

public class ProcessDAO {

	private Connection getConnection() throws NamingException, SQLException {
		Context ctx = new InitialContext();
		DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
		return dataFactory.getConnection();
	}

	// 공정 목록 테이블용
	public List<ProcessDTO> selectProcessList() {

		List<ProcessDTO> list = new ArrayList<ProcessDTO>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String query = "";
			query += "SELECT process_id, ";
			query += "       process_name, ";
			query += "       seq, ";
			query += "       item_id, ";
			query += "       process_info ";
			query += "FROM process ";
			query += "ORDER BY seq, process_id";

			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				ProcessDTO dto = new ProcessDTO();
				dto.setProcess_id(rs.getString("process_id"));
				dto.setProcess_name(rs.getString("process_name"));
				dto.setSeq(rs.getInt("seq"));
				dto.setItem_id(rs.getString("item_id"));
				dto.setProcess_info(rs.getString("process_info"));

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
		}

		return list;
	}

	// 공정 흐름도용: process_step 읽기
	public List<ProcessDTO> selectProcessStepList(String processId) {

		List<ProcessDTO> list = new ArrayList<ProcessDTO>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String query = "";
			query += "SELECT process_step_id, ";
			query += "       seq, ";
			query += "       step_name, ";
			query += "       process_id ";
			query += "FROM process_step ";
			query += "WHERE process_id = ? ";
			query += "ORDER BY seq, process_step_id";

			ps = conn.prepareStatement(query);
			ps.setString(1, processId);

			rs = ps.executeQuery();

			while (rs.next()) {
				ProcessDTO dto = new ProcessDTO();
				dto.setProcess_step_id(rs.getString("process_step_id"));
				dto.setSeq(rs.getInt("seq"));
				dto.setStep_name(rs.getString("step_name"));
				dto.setProcess_id(rs.getString("process_id"));

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
		}

		return list;
	}

	public int insertProcess(ProcessDTO processDTO) {
		Connection conn = null;
		PreparedStatement psMax = null;
		PreparedStatement psInsert = null;
		ResultSet rs = null;

		int result = 0;

		try {
			conn = getConnection();

			// process_step_id 최대값 조회 -> 자동으로 공정 단계 추가를 위한
			String maxQuery = "";
			maxQuery += "SELECT NVL(MAX(TO_NUMBER(SUBSTR(process_step_id, 5))), 1000) AS max_num ";
			maxQuery += "FROM process_step";

			psMax = conn.prepareStatement(maxQuery);
			rs = psMax.executeQuery();

			int nextStepNum = 1001;
			if (rs.next()) {
				nextStepNum = rs.getInt("max_num") + 1;
			}

			String processStepId = "pst_" + nextStepNum;

			// 2. process_step 테이블 insert
			String insertQuery = "";
			insertQuery += "INSERT INTO process_step (process_step_id, seq, step_name, process_id) ";
			insertQuery += "VALUES (?, ?, ?, ?)";

			psInsert = conn.prepareStatement(insertQuery);
			psInsert.setString(1, processStepId);
			psInsert.setInt(2, processDTO.getSeq());
			psInsert.setString(3, processDTO.getStep_name());
			psInsert.setString(4, processDTO.getProcess_id());

			result = psInsert.executeUpdate();

			System.out.println("process_step insert 결과: " + result);
			System.out.println("생성된 process_step_id: " + processStepId);

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
			if (psMax != null) {
				try {
					psMax.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (psInsert != null) {
				try {
					psInsert.close();
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

		return result;
	}

	// 맨 위 상세 정보: process 테이블
	public ProcessDTO selectProcessDetail(String processId) {
		ProcessDTO dto = null;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String query = "";
			query += "SELECT process_id, ";
			query += "       process_name, ";
			query += "       seq, ";
			query += "       item_id, ";
			query += "       process_info ";
			query += "FROM process ";
			query += "WHERE process_id = ?";

			ps = conn.prepareStatement(query);
			ps.setString(1, processId);

			rs = ps.executeQuery();

			if (rs.next()) {
				dto = new ProcessDTO();
				dto.setProcess_id(rs.getString("process_id"));
				dto.setProcess_name(rs.getString("process_name"));
				dto.setSeq(rs.getInt("seq"));
				dto.setItem_id(rs.getString("item_id"));
				dto.setProcess_info(rs.getString("process_info"));
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
		}

		return dto;
	}

	public List<ProcessDTO> selectMaterialList(String processId) {
		List<ProcessDTO> list = new ArrayList<ProcessDTO>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String query = "";
			query += "SELECT i.item_id, ";
			query += "       i.item_name, ";
			query += "       i.unit ";
			query += "FROM process p ";
			query += "JOIN item i ";
			query += "  ON p.item_id = i.item_id ";
			query += "WHERE p.process_id = ?";

			ps = conn.prepareStatement(query);
			ps.setString(1, processId);

			rs = ps.executeQuery();

			while (rs.next()) {
				ProcessDTO dto = new ProcessDTO();
				dto.setItem_id(rs.getString("item_id"));
				dto.setItem_name(rs.getString("item_name"));
				dto.setUnit(rs.getString("unit"));

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
		}

		return list;
	}

	public List<ProcessDTO> selectEquipmentList(String processId) {
		List<ProcessDTO> list = new ArrayList<ProcessDTO>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String query = "";
			query += "SELECT e.eq_id, ";
			query += "       e.eq_name ";
			query += "FROM equipment e ";
			query += "WHERE e.eq_name LIKE '%' || ( ";
			query += "    SELECT p.process_name ";
			query += "    FROM process p ";
			query += "    WHERE p.process_id = ? ";
			query += ") || '%' ";
			query += "AND (e.deleted IS NULL OR e.deleted <> 'Y') ";
			query += "ORDER BY e.eq_id";

			ps = conn.prepareStatement(query);
			ps.setString(1, processId);

			rs = ps.executeQuery();

			while (rs.next()) {
				ProcessDTO dto = new ProcessDTO();
				dto.setEq_id(rs.getString("eq_id"));
				dto.setEq_name(rs.getString("eq_name"));

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
		}

		return list;
	}

	public int updateProcess(ProcessDTO processDTO) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int update_result = -1;

		try {
			
			Context ctx;
			ctx = new InitialContext();
			// DataSource: 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
			// DB 접속(그런데 이제 커넥션 풀로)
			conn = dataFactory.getConnection();

			// Sql 작성
			String query = "UPDATE process";
			query += " SET process_name = ?, process_info = ?";
			query += " WHERE process_id = ?";

			ps = conn.prepareStatement(query);
			ps.setString(1, processDTO.getProcess_name());
			ps.setString(2, processDTO.getProcess_info());
			ps.setString(3, processDTO.getProcess_id());

			// SQL 실행
			update_result = ps.executeUpdate();
			System.out.println("update 결과: " + update_result);

		} catch (NamingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
		return update_result;
	}

}
