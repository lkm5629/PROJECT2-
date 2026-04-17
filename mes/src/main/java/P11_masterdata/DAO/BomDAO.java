package P11_masterdata.DAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import P11_masterdata.DTO.BomDTO;

public class BomDAO {

	private Connection getConnection() throws NamingException, SQLException {
		Context ctx = new InitialContext();
		DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
		return dataFactory.getConnection();
	}

	private void close(ResultSet rs, PreparedStatement ps, Connection conn) {
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

	private void close(PreparedStatement ps, Connection conn) {
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

	public List<BomDTO> selectAll() {
		List<BomDTO> list = new ArrayList<BomDTO>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String query = "";
			query += "SELECT b.bom_id, ";
			query += "       b.parent_item_id, ";
			query += "       i.item_name, ";
			query += "       i.g_id, ";
			query += "       g.itemgroup_name ";
			query += "FROM bom2 b ";
			query += "JOIN item i ";
			query += "  ON b.parent_item_id = i.item_id ";
			query += "LEFT OUTER JOIN group_info g ";
			query += "  ON i.g_id = g.g_id ";
			query += "ORDER BY TO_NUMBER(SUBSTR(b.bom_id, 5)) DESC";

			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				BomDTO bomDTO = new BomDTO();
				bomDTO.setBom_id(rs.getString("bom_id"));
				bomDTO.setParent_item_id(rs.getString("parent_item_id"));
				bomDTO.setItem_name(rs.getString("item_name"));
				bomDTO.setG_id(rs.getInt("g_id"));
				bomDTO.setItemgroup_name(rs.getString("itemgroup_name"));

				list.add(bomDTO);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}
		return list;
	}

	public List<BomDTO> selectBomPage(BomDTO bomDTO) {
		List<BomDTO> list = new ArrayList<BomDTO>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String keyword = bomDTO.getKeyword();
			String itemGroup = bomDTO.getItemGroup();

			String query = "";
			query += "SELECT * ";
			query += "FROM ( ";
			query += "    SELECT ROWNUM rnum, t.* ";
			query += "    FROM ( ";
			query += "        SELECT b.bom_id, ";
			query += "               b.parent_item_id, ";
			query += "               i.item_name, ";
			query += "               i.g_id, ";
			query += "               g.itemgroup_name ";
			query += "        FROM bom2 b ";
			query += "        JOIN item i ";
			query += "          ON b.parent_item_id = i.item_id ";
			query += "        LEFT OUTER JOIN group_info g ";
			query += "          ON i.g_id = g.g_id ";
			query += "        WHERE 1=1 ";
			if (keyword != null && !keyword.trim().equals("")) {
				query += "          AND i.item_name LIKE ? ";
			}
			if (itemGroup != null && !itemGroup.trim().equals("")) {
				query += "          AND i.g_id = ? ";
			}
			query += "        ORDER BY TO_NUMBER(SUBSTR(b.bom_id, 5)) DESC ";
			query += "    ) t ";
			query += "    WHERE ROWNUM <= ? ";
			query += ") ";
			query += "WHERE rnum >= ?";

			ps = conn.prepareStatement(query);
			int index = 1;

			if (keyword != null && !keyword.trim().equals("")) {
				ps.setString(index++, "%" + keyword.trim() + "%");
			}
			if (itemGroup != null && !itemGroup.trim().equals("")) {
				ps.setInt(index++, Integer.parseInt(itemGroup));
			}
			ps.setInt(index++, bomDTO.getEnd());
			ps.setInt(index, bomDTO.getStart());

			rs = ps.executeQuery();

			while (rs.next()) {
				BomDTO dto = new BomDTO();
				dto.setBom_id(rs.getString("bom_id"));
				dto.setParent_item_id(rs.getString("parent_item_id"));
				dto.setItem_name(rs.getString("item_name"));
				dto.setG_id(rs.getInt("g_id"));
				dto.setItemgroup_name(rs.getString("itemgroup_name"));
				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}

		return list;
	}

	public int selectBomTotalCount() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int totalCount = 0;

		try {
			conn = getConnection();

			String query = "";
			query += "SELECT COUNT(*) cnt ";
			query += "FROM bom2";

			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			if (rs.next()) {
				totalCount = rs.getInt("cnt");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}

		return totalCount;
	}

	public int selectBomTotalCount(BomDTO bomDTO) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int totalCount = 0;

		try {
			conn = getConnection();

			String keyword = bomDTO.getKeyword();
			String itemGroup = bomDTO.getItemGroup();

			String query = "";
			query += "SELECT COUNT(*) cnt ";
			query += "FROM bom2 b ";
			query += "JOIN item i ";
			query += "  ON b.parent_item_id = i.item_id ";
			query += "WHERE 1=1 ";
			if (keyword != null && !keyword.trim().equals("")) {
				query += "  AND i.item_name LIKE ? ";
			}
			if (itemGroup != null && !itemGroup.trim().equals("")) {
				query += "  AND i.g_id = ? ";
			}

			ps = conn.prepareStatement(query);
			int index = 1;

			if (keyword != null && !keyword.trim().equals("")) {
				ps.setString(index++, "%" + keyword.trim() + "%");
			}
			if (itemGroup != null && !itemGroup.trim().equals("")) {
				ps.setInt(index++, Integer.parseInt(itemGroup));
			}

			rs = ps.executeQuery();

			if (rs.next()) {
				totalCount = rs.getInt("cnt");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}

		return totalCount;
	}

	public String selectNextBomId() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String nextBomId = "bom_1001";

		try {
			conn = getConnection();

			String query = "";
			query += "SELECT NVL(MAX(TO_NUMBER(SUBSTR(bom_id, 5))), 1000) + 1 AS next_num ";
			query += "FROM bom2";

			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			if (rs.next()) {
				int nextNum = rs.getInt("next_num");
				nextBomId = "bom_" + nextNum;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}

		return nextBomId;
	}

	public String selectItemIdByNameAndGroup(String itemName, int gId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String itemId = null;

		try {
			conn = getConnection();

			String query = "";
			query += "SELECT item_id ";
			query += "FROM item ";
			query += "WHERE item_name = ? ";
			query += "  AND g_id = ? ";

			ps = conn.prepareStatement(query);
			ps.setString(1, itemName);
			ps.setInt(2, gId);

			rs = ps.executeQuery();

			if (rs.next()) {
				itemId = rs.getString("item_id");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}

		return itemId;
	}

	public int insertBom(BomDTO bomDTO) {
		Connection conn = null;
		PreparedStatement ps = null;

		int result = -1;

		try {
			conn = getConnection();

			String query = "";
			query += "INSERT INTO bom2 (bom_id, parent_item_id) ";
			query += "VALUES (?, ?)";

			ps = conn.prepareStatement(query);
			ps.setString(1, bomDTO.getBom_id());
			ps.setString(2, bomDTO.getParent_item_id());

			result = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(ps, conn);
		}
		return result;
	}

	public int updateBom(BomDTO bomDTO) {
		Connection conn = null;
		PreparedStatement ps = null;
		int update_result = -1;

		try {
			conn = getConnection();

			String query = "";
			query += "UPDATE bom2 ";
			query += "SET parent_item_id = ? ";
			query += "WHERE bom_id = ?";

			ps = conn.prepareStatement(query);
			ps.setString(1, bomDTO.getParent_item_id());
			ps.setString(2, bomDTO.getBom_id());

			update_result = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(ps, conn);
		}
		return update_result;
	}

	public BomDTO selectBomInfo(String bomId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		BomDTO bomDTO = null;

		try {
			conn = getConnection();

			String query = "";
			query += "SELECT b.bom_id, ";
			query += "       b.parent_item_id, ";
			query += "       i.item_name, ";
			query += "       i.g_id, ";
			query += "       g.itemgroup_name ";
			query += "FROM bom2 b ";
			query += "JOIN item i ";
			query += "  ON b.parent_item_id = i.item_id ";
			query += "LEFT OUTER JOIN group_info g ";
			query += "  ON i.g_id = g.g_id ";
			query += "WHERE b.bom_id = ?";

			ps = conn.prepareStatement(query);
			ps.setString(1, bomId);
			rs = ps.executeQuery();

			if (rs.next()) {
				bomDTO = new BomDTO();
				bomDTO.setBom_id(rs.getString("bom_id"));
				bomDTO.setParent_item_id(rs.getString("parent_item_id"));
				bomDTO.setItem_name(rs.getString("item_name"));
				bomDTO.setG_id(rs.getInt("g_id"));
				bomDTO.setItemgroup_name(rs.getString("itemgroup_name"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}

		return bomDTO;
	}

	public String selectParentItemIdByBomId(String bomId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String parentItemId = null;

		try {
			conn = getConnection();

			String query = "";
			query += "SELECT parent_item_id ";
			query += "FROM bom2 ";
			query += "WHERE bom_id = ?";

			ps = conn.prepareStatement(query);
			ps.setString(1, bomId);
			rs = ps.executeQuery();

			if (rs.next()) {
				parentItemId = rs.getString("parent_item_id");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}

		return parentItemId;
	}

	public List<BomDTO> selectBomDetailPage(BomDTO bomDTO) {
		List<BomDTO> list = new ArrayList<BomDTO>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String query = "";
			query += "SELECT * ";
			query += "FROM ( ";
			query += "    SELECT ROWNUM rnum, t.* ";
			query += "    FROM ( ";
			query += "        SELECT bd.bom_detail_id, ";
			query += "               bd.bom_id, ";
			query += "               bd.child_item_id, ";
			query += "               i.item_name AS child_item_name, ";
			query += "               bd.ea, ";
			query += "               i.unit ";
			query += "        FROM bom_detail2 bd ";
			query += "        JOIN item i ";
			query += "          ON bd.child_item_id = i.item_id ";
			query += "        WHERE bd.bom_id = ? ";
			query += "        ORDER BY bd.bom_detail_id ASC ";
			query += "    ) t ";
			query += "    WHERE ROWNUM <= ? ";
			query += ") ";
			query += "WHERE rnum >= ?";

			ps = conn.prepareStatement(query);
			ps.setString(1, bomDTO.getBom_id());
			ps.setInt(2, bomDTO.getEnd());
			ps.setInt(3, bomDTO.getStart());

			rs = ps.executeQuery();

			while (rs.next()) {
				BomDTO dto = new BomDTO();
				dto.setBom_detail_id(rs.getInt("bom_detail_id"));
				dto.setBom_id(rs.getString("bom_id"));
				dto.setChild_item_id(rs.getString("child_item_id"));
				dto.setChild_item_name(rs.getString("child_item_name"));
				dto.setEa(rs.getBigDecimal("ea"));
				dto.setUnit(rs.getString("unit"));
				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}

		return list;
	}

	public int selectBomDetailTotalCount(String bomId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int totalCount = 0;

		try {
			conn = getConnection();

			String query = "";
			query += "SELECT COUNT(*) cnt ";
			query += "FROM bom_detail2 ";
			query += "WHERE bom_id = ?";

			ps = conn.prepareStatement(query);
			ps.setString(1, bomId);
			rs = ps.executeQuery();

			if (rs.next()) {
				totalCount = rs.getInt("cnt");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}

		return totalCount;
	}

	public int selectNextBomDetailId() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int nextId = 1;

		try {
			conn = getConnection();

			String query = "";
			query += "SELECT NVL(MAX(bom_detail_id), 0) + 1 AS next_id ";
			query += "FROM bom_detail2";

			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			if (rs.next()) {
				nextId = rs.getInt("next_id");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}

		return nextId;
	}

	public List<BomDTO> selectBomDetailItemList(String parentItemId) {
		List<BomDTO> list = new ArrayList<BomDTO>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			Set<String> allowedIds = getAllowedChildItemIds(parentItemId);

			String query = "";
			query += "SELECT item_id, item_name, unit, g_id ";
			query += "FROM item ";
			query += "WHERE g_id IN (10, 20) ";

			if (!allowedIds.isEmpty()) {
				query += "AND item_id IN (";
				query += String.join(",", Collections.nCopies(allowedIds.size(), "?"));
				query += ") ";
			}

			query += "ORDER BY item_name ASC";

			ps = conn.prepareStatement(query);

			int index = 1;
			for (String itemId : allowedIds) {
				ps.setString(index++, itemId);
			}

			rs = ps.executeQuery();

			while (rs.next()) {
				BomDTO dto = new BomDTO();
				dto.setChild_item_id(rs.getString("item_id"));
				dto.setChild_item_name(rs.getString("item_name"));
				dto.setUnit(rs.getString("unit"));
				dto.setG_id(rs.getInt("g_id"));
				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, ps, conn);
		}

		return list;
	}

	public boolean isValidChildItem(String parentItemId, String childItemId) {
		if (childItemId == null || childItemId.trim().equals("")) {
			return false;
		}

		Set<String> allowedIds = getAllowedChildItemIds(parentItemId);

		if (allowedIds.isEmpty()) {
			return true;
		}

		return allowedIds.contains(childItemId);
	}

	private Set<String> getAllowedChildItemIds(String parentItemId) {
		Set<String> set = new LinkedHashSet<String>();

		if (parentItemId == null || parentItemId.trim().equals("")) {
			return set;
		}

		switch (parentItemId) {
		case "semi_1001":
		case "semi_1002":
			set.add("raw_1001");
			break;

		case "semi_1003":
			set.addAll(Arrays.asList("semi_1001", "raw_1002", "raw_1008"));
			break;

		case "semi_1004":
			set.addAll(Arrays.asList("semi_1002", "raw_1002", "raw_1010"));
			break;

		case "semi_1005":
			set.addAll(Arrays.asList("semi_1001", "raw_1003", "raw_1008"));
			break;

		case "semi_1006":
			set.addAll(Arrays.asList("semi_1002", "raw_1003", "raw_1010"));
			break;

		case "fin_1006":
			set.addAll(Arrays.asList("semi_1003", "raw_1009"));
			break;

		case "fin_1005":
			set.addAll(Arrays.asList("semi_1004", "raw_1011"));
			break;

		case "fin_1004":
			set.addAll(Arrays.asList("semi_1005", "raw_1009"));
			break;

		case "fin_1003":
			set.addAll(Arrays.asList("semi_1006", "raw_1011"));
			break;

		default:
			break;
		}

		return set;
	}

	public int insertBomDetail(BomDTO bomDTO) {
		Connection conn = null;
		PreparedStatement ps = null;

		int result = -1;

		try {
			String parentItemId = selectParentItemIdByBomId(bomDTO.getBom_id());
			if (!isValidChildItem(parentItemId, bomDTO.getChild_item_id())) {
				return 0;
			}

			conn = getConnection();

			String query = "";
			query += "INSERT INTO bom_detail2 (bom_detail_id, bom_id, child_item_id, ea) ";
			query += "VALUES (?, ?, ?, ?)";

			ps = conn.prepareStatement(query);
			ps.setInt(1, bomDTO.getBom_detail_id());
			ps.setString(2, bomDTO.getBom_id());
			ps.setString(3, bomDTO.getChild_item_id());
			ps.setBigDecimal(4, bomDTO.getEa());

			result = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(ps, conn);
		}

		return result;
	}

	public int updateBomDetail(BomDTO bomDTO) {
		Connection conn = null;
		PreparedStatement ps = null;

		int result = -1;

		try {
			String parentItemId = selectParentItemIdByBomId(bomDTO.getBom_id());
			if (!isValidChildItem(parentItemId, bomDTO.getChild_item_id())) {
				return 0;
			}

			conn = getConnection();

			String query = "";
			query += "UPDATE bom_detail2 ";
			query += "SET child_item_id = ?, ";
			query += "    ea = ? ";
			query += "WHERE bom_detail_id = ?";

			ps = conn.prepareStatement(query);
			ps.setString(1, bomDTO.getChild_item_id());
			ps.setBigDecimal(2, bomDTO.getEa());
			ps.setInt(3, bomDTO.getBom_detail_id());

			result = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(ps, conn);
		}

		return result;
	}

	public int deleteBomDetail(int bomDetailId) {
		Connection conn = null;
		PreparedStatement ps = null;

		int result = -1;

		try {
			conn = getConnection();

			String query = "";
			query += "DELETE FROM bom_detail2 ";
			query += "WHERE bom_detail_id = ?";

			ps = conn.prepareStatement(query);
			ps.setInt(1, bomDetailId);

			result = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(ps, conn);
		}

		return result;
	}
}