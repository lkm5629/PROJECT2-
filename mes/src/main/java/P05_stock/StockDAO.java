package P05_stock;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class StockDAO {

	public List<StockDTO> select(StockDTO stockDTO) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// JDNI 방식
			// context.xml에 있는 DB 정보로 커넥션 풀을 가져온다
			Context ctx = new InitialContext();
			// DataSource : 커넥션 풀 관리자
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
			// DB 접속(그런데 이제 커넥션 풀로)
			conn = dataFactory.getConnection();
			// SQL 준비
			String query = "select * from userinfo";
			ps = conn.prepareStatement(query);

			// SQL 실행 및 결과 확보
			rs = ps.executeQuery();

			// 결과 활용
			while (rs.next()) {
//				int todo_id = rs.getInt("todo_id");
//				Date duedate = rs.getDate("duedate");
//				int done = rs.getInt("done");
//				String contents = rs.getString("contents");
//				Date ctime = rs.getDate("ctime");



//				TodoDTO todoDTO = new TodoDTO();
//				todoDTO.setTodo_id(todo_id);
//				todoDTO.setDueDate(duedate);
//				todoDTO.setDone(done);
//				todoDTO.setContents(contents);
//				todoDTO.setCtime(ctime);
//				
//				list.add(todoDTO);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
			// JNDI 방식
			// context.xml에 있는 DB 정보로 커넥션 풀을 가져온다

		}

		return list;
	}

}
