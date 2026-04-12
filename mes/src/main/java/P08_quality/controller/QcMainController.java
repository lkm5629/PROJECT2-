package P08_quality.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import P07_work.SearchDTO;
import P07_work.WoDTO;
import P07_work.WoService;
import P08_quality.QcCardDTO;
import P08_quality.QcDTO;
import P08_quality.QcService;

@WebServlet("/quality")
public class QcMainController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/quality doGet 실행");

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
		getCard(request, response);

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
		
		
		request.getRequestDispatcher("/WEB-INF/views/P08_quality/main.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/quality doPost 실행");
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
	}
	
	protected void getList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/quality getList 실행");
		
		
		int size = 10;
		int page = 1;
		
		String pageStr = request.getParameter("page");
		
		try {
			page = Integer.parseInt(pageStr);			
		} catch (Exception e) {

		}
		
		QcDTO dto = new QcDTO();
		dto.setSize(size);
		dto.setPage(page);
		
		QcService service = new QcService();
		Map qcMap = service.getList(dto);
		
		System.out.println(qcMap);
		
		request.setAttribute("qcMap", qcMap);
	}
	
	protected void getCard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/quality getCard 실행");
		
		QcService service = new QcService();
		QcCardDTO cardDTO = service.getCard();
		
		request.setAttribute("cardDTO", cardDTO);
	}
	
	protected void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/quality search 실행");


		int size = 10;
		int page = 1;

		String pageStr = request.getParameter("page");
		
		try {
			page = Integer.parseInt(pageStr);			
		} catch (Exception e) {

		}
		
		QcDTO dto = new QcDTO();
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
		keyword = request.getParameter("keyword").trim();
		
		SearchDTO search = new SearchDTO();
		
		search.setStatus(status);
		search.setsDate(sDateStr);
		search.seteDate(eDateStr);
		search.setKeyword(keyword);
		
		QcService service = new QcService();
		Map map = service.search(dto, search);
		
		// forward
		request.setAttribute("qcMap", map);
		
	}
	
	protected void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/quality detail 실행");
		
	}

}
