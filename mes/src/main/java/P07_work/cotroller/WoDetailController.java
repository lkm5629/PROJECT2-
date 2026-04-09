package P07_work.cotroller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import P07_work.WoDTO;
import P07_work.WoService;

@WebServlet("/workorder")
public class WoDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/workorder doGet 실행");

		// 요청의 한글 깨짐 방지
		request.setCharacterEncoding("utf-8");
		// 응답의 한글 깨짐 방지
		response.setContentType("text/html; charset=utf-8;");
		
		setting(request, response);
		
		request.getRequestDispatcher("/WEB-INF/views/P07_work/detail.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/workorder doPost 실행");

		// 요청의 한글 깨짐 방지
		request.setCharacterEncoding("utf-8");
		// 응답의 한글 깨짐 방지
		response.setContentType("text/html; charset=utf-8;");
		
	}
	
	protected void setting (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/workorder setting 실행");
		
		String woId = request.getParameter("woId");
		WoDTO dto = new WoDTO();
		dto.setWoId(woId);
		
		// DB에서 모든 목록 가져오기
		WoService service = new WoService();
		dto = service.detail(dto);
		
		System.out.println(dto);
		
		// forward
		request.setAttribute("woInfo", dto);
	}

}
