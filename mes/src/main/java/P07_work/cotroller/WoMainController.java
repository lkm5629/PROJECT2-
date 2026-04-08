package P07_work.cotroller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import P07_work.SearchDTO;
import P07_work.WoDTO;
import P07_work.WoService;

@WebServlet("/worklist")
public class WoMainController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/worklist doGet 실행");
		

		// 요청의 한글 깨짐 방지
		request.setCharacterEncoding("utf-8");
		// 응답의 한글 깨짐 방지
		response.setContentType("text/html; charset=utf-8;");

		String cmd = request.getParameter("cmd");
		System.out.println("cmd : " + cmd);
		
		if ("search".equals(cmd)) {
			search(request, response);
		} else if ("detail".equals(cmd)) {
			detail(request, response);
			
			return;
		} else {
			getList(request, response);			
		}
		
		
		request.getRequestDispatcher("/WEB-INF/views/P07_work/main.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 요청의 한글 깨짐 방지
		request.setCharacterEncoding("utf-8");
		// 응답의 한글 깨짐 방지
		response.setContentType("text/html; charset=utf-8;");
		System.out.println("/worklist doPost 실행");
	}
	
	protected void getList (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/worklist getList 실행");
		
		int size = 10;
		int page = 1;
		
		String pageStr = request.getParameter("page");
		
		try {
			page = Integer.parseInt(pageStr);			
		} catch (Exception e) {

		}
		
		WoDTO dto = new WoDTO();
		dto.setSize(size);
		dto.setPage(page);
		
		// DB에서 모든 목록 가져오기
		WoService service = new WoService();
		Map woMap = service.getList(dto);
		
		// forward
		request.setAttribute("woMap", woMap);
		
	}
	
	protected void search (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/worklist search 실행");
		

		int size = 10;
		int page = 1;

		String pageStr = request.getParameter("page");
		
		try {
			page = Integer.parseInt(pageStr);			
		} catch (Exception e) {

		}
		
		WoDTO dto = new WoDTO();
		dto.setSize(size);
		dto.setPage(page);
		
		int status = 0;
		String statusStr = request.getParameter("status");
		if (!("".equals(statusStr)) && statusStr != null) {
			status = Integer.parseInt(statusStr);				
		}
		
		String sDateStr = request.getParameter("startDate");
		Date sDate = null;
		if (!("".equals(sDateStr)) && sDateStr != null) {
			sDate = Date.valueOf(sDateStr);
		}
		
		String eDateStr = request.getParameter("endDate");
		Date eDate = null;
		if (!("".equals(eDateStr)) && eDateStr != null) {
			eDate = Date.valueOf(eDateStr);
		}
		
		String keyword = "";
		keyword = request.getParameter("keyword");
		
		SearchDTO search = new SearchDTO();
		
		search.setStatus(status);
		search.setsDate(sDateStr);
		search.seteDate(eDateStr);
		search.setKeyword(keyword);
		
		// DB에서 모든 목록 가져오기
		WoService service = new WoService();
		Map map = service.search(dto, search);
		
		// forward
		request.setAttribute("woMap", map);
		
	}
	
	protected void detail (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/worklist detail 실행");
		
		String woId = request.getParameter("woId");
		
		String cp = request.getContextPath();
		response.sendRedirect(cp + "/workorder?woId=" + woId);
		
	}

}
