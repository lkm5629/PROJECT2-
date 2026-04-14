package P01_auth;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/dashboard")
public class DashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("dashboard의 doget 실행");
		// TODO Auto-generated method stub

		// 주소 : http://localhost:8080/mes/login.jsp

		// 한글깨짐 방지
		request.setCharacterEncoding("UTF-8");
//		response.setContentType("text/html; charset=UTF-8");
		// 함수 모음집 소환
		LoginService s = new LoginService();
		// 데이터 바구니 소환
		DashDTO d = new DashDTO();

		
		List<DashDTO> list = s.defect();
		List<DashDTO> notice = s.notice();
		
	
		// 세션 소환
		HttpSession session = request.getSession();

		// 세션으로 보내기
		session.setAttribute("list", list);
		session.setAttribute("notice", notice);

		// 세션이니 그냥 주소 바뀌게 ㄱㄱ.
		request.getRequestDispatcher("/dashboard.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}
