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

import P11_masterdata.DTO.Item_masterDTO;

public class Item_masterDAO {

	// DB연결
	// SQL 실행
	// DTO에 담기

	public List<Item_masterDTO> selectItemList() {

		List<Item_masterDTO> list = new ArrayList<Item_masterDTO>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			// JNDI 방식
			// context.xml에 있는 DB 정보로 커넥션 풀을 가져옴
			Context ctx;
			ctx = new InitialContext();
			// DataSource: 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");

			// DB 접속(그런데 이제 커넥션 풀로)
			conn = dataFactory.getConnection();

			// SQL 준비
			String query = "";
			query += "SELECT i.item_id, ";
			query += "       i.item_name, ";
			query += "       i.unit, ";
			query += "       i.spec, ";
			query += "       i.g_id, ";
			query += "       g.itemgroup_name ";
			query += "FROM item i ";
			query += "LEFT OUTER JOIN group_info g ";
			query += "  ON i.g_id = g.g_id ";
			query += "ORDER BY i.item_id";

			ps = conn.prepareStatement(query);

			// SQL 실행 및 결과 확보
			rs = ps.executeQuery();

			// 결과 활용
			while (rs.next()) {
				String item_id = rs.getString("ITEM_ID");
				String item_name = rs.getString("ITEM_NAME");
				String unit = rs.getString("UNIT");
				int spec = rs.getInt("SPEC");
				int g_id = rs.getInt("G_ID");
				String itemgroup_name = rs.getString("ITEMGROUP_NAME");

				// 가져왔으니 담기
				Item_masterDTO item_masterDTO = new Item_masterDTO();
				item_masterDTO.setItem_id(item_id);
				item_masterDTO.setItem_name(item_name);
				item_masterDTO.setUnit(unit);
				item_masterDTO.setSpec(spec);
				item_masterDTO.setG_id(g_id);
				item_masterDTO.setItemgroup_name(itemgroup_name);

				list.add(item_masterDTO);
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

	public int insertItem(Item_masterDTO item_masterDTO) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int result = -1;

		try {
			// JNDI 방식
			// context.xml에 있는 DB 정보로 커넥션 풀을 가져옴
			Context ctx;
			ctx = new InitialContext();
			// DataSource: 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
			// DB 접속(그런데 이제 커넥션 풀로)
			conn = dataFactory.getConnection();

			// SQL 준비
			String query = "INSERT INTO item (item_id, item_name, unit, spec, g_id)";
			query += " VALUES (?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(query);
			// 가져오기
			ps.setString(1, item_masterDTO.getItem_id());
			ps.setString(2, item_masterDTO.getItem_name());
			ps.setString(3, item_masterDTO.getUnit());
			ps.setInt(4, item_masterDTO.getSpec());
			ps.setInt(5, item_masterDTO.getG_id());

			result = ps.executeUpdate();
			System.out.println("insert 결과: " + result);

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
		return result;

	}

	public int updateItem(Item_masterDTO item_masterDTO) {
		Connection conn = null;
		PreparedStatement ps = null;

		int update_result = -1;

		try {
			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
			conn = dataFactory.getConnection();

			String query = "";
			query += "UPDATE item ";
			query += "SET g_id = ?, unit = ?, spec = ?, item_name = ? ";
			query += "WHERE item_id = ?";

			ps = conn.prepareStatement(query);

			ps.setInt(1, item_masterDTO.getG_id());
			ps.setString(2, item_masterDTO.getUnit());
			ps.setInt(3, item_masterDTO.getSpec());
			ps.setString(4, item_masterDTO.getItem_name());
			ps.setString(5, item_masterDTO.getItem_id());

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
