package P01_auth;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/permission")
public class PermissionController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("permission의 doget 실행");
		// TODO Auto-generated method stub

		// 주소 : http://localhost:8080/mes/login.jsp

		// 한글깨짐 방지
		request.setCharacterEncoding("UTF-8");
//		response.setContentType("text/html; charset=UTF-8");
		// 함수 모음집 소환
		LoginService s = new LoginService();
		// 데이터 바구니 소환
		LoginDTO d = new LoginDTO();
		
		
		//Paging 페이징
		String page = request.getParameter("page");
		
		//처음은 1로 스타트되게
		if(page == null) {
			page = "1";
		}
		// 한 페이지당 보여줄 개수
		int countPage = 5;
		
		//시작 번호 1이면 0. 2면 5. 3이면 10.
		int start_no = (Integer.parseInt(page) - 1) * countPage;
		
		
		
		

		// 함수 소환 후 결과(전체 사원수)를 인트에 저장.
		int count = s.readEmp();
		
		int page_no = (int)Math.ceil(count/countPage);
		
		// 함수 소환 후 결과를 리스트에 저장.
		List<LoginDTO> list = s.paging(start_no, countPage);
		

		// 세션 소환
		HttpSession session = request.getSession();

		// 세션으로 보내기
		session.setAttribute("page_no", page_no);
		session.setAttribute("list", list);

		// 세션이니 그냥 주소 바뀌게 ㄱㄱ.
		request.getRequestDispatcher("/permission.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}
