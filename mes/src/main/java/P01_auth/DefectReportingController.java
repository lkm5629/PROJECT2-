package P01_auth;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/defectreporting")
public class DefectReportingController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("defectreporting의 doget 실행");
		// TODO Auto-generated method stub

		// 주소 : http://localhost:8080/mes/login.jsp

		// 한글깨짐 방지
		request.setCharacterEncoding("UTF-8");
//		response.setContentType("text/html; charset=UTF-8");
		// 함수 모음집 소환
		LoginService s = new LoginService();
		// 데이터 바구니 소환
		DreportingDTO d = new DreportingDTO();
		
		
		
		

		// Paging 페이징(notice)
		String npage = request.getParameter("n_btn");
		System.out.println("npage : " + npage);

		// 처음은 1로 스타트되게
		if (npage == null) {
			npage = "1";
		}
		// 한 페이지당 보여줄 개수
		int ncountPage = 5;

		// 시작 번호 1이면 0. 2면 5. 3이면 10.
		int nstart_no = (Integer.parseInt(npage) - 1) * ncountPage;

		System.out.println("nstart_no : " + nstart_no);

		int ncountPageNo = nstart_no + ncountPage;
		System.out.println(" ncountPageNo : " + ncountPageNo);

		// 함수 소환 후 결과(전체 사원수)를 인트에 저장.
		int ncount = s.nread();

		int npage_no = (int) Math.ceil(ncount / ncountPage);
		
		System.out.println("npage_no : "+npage_no);
		
		
		
		
		
		// Paging 페이징(suggestion)
		String spage = request.getParameter("s_btn");
		System.out.println("spage : " + spage);
		
		// 처음은 1로 스타트되게
		if (spage == null) {
			spage = "1";
		}
		// 한 페이지당 보여줄 개수
		int scountPage = 5;
		
		// 시작 번호 1이면 0. 2면 5. 3이면 10.
		int sstart_no = (Integer.parseInt(spage) - 1) * scountPage;
		
		System.out.println("sstart_no : " + sstart_no);
		
		int scountPageNo = sstart_no + scountPage;
		System.out.println(" scountPageNo : " + scountPageNo);
		
		// 함수 소환 후 결과(전체 사원수)를 인트에 저장.
		int scount = s.sread();
		
		int spage_no = (int) Math.ceil(scount / scountPage);
		
		System.out.println("spage_no : "+spage_no);
		
		

		List<DashDTO> list = s.defect();
		List<DashDTO> notice = s.notice(nstart_no, ncountPageNo);
		List<DashDTO> suggestion = s.suggestion(sstart_no, scountPageNo);

		// 세션 소환
		HttpSession session = request.getSession();

		// 세션으로 보내기
		session.setAttribute("list", list);
		
		session.setAttribute("npage_no", npage_no);
		session.setAttribute("notice", notice);
		
		session.setAttribute("spage_no", spage_no);
		session.setAttribute("suggestion", suggestion);

		// 세션이니 그냥 주소 바뀌게 ㄱㄱ.
		request.getRequestDispatcher("/dashboard.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}
