package P09_equip.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import P07_work.WoDTO;
import P07_work.WoService;
import P09_equip.EqService;
import P09_equip.DTO.EqDTO;
import P09_equip.DTO.EqLogDTO;

@WebServlet("/eqdetail")
public class EqDetailController extends HttpServlet {
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/equipdetail doGet 실행");

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8;");
		
		String cmd = request.getParameter("cmd");
		
		if("eqStop".equals(cmd)) {
			eqStop(request, response);
			return;
		} else if("eqRun".equals(cmd)) {
			eqRun(request, response);
			return;
		}
		
		setting(request, response);
		request.getRequestDispatcher("/WEB-INF/views/P09_equip/detail.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/eqdetail doPost 실행");

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8;");
		
		String cmd = request.getParameter("cmd");
		
		if("statusChange".equals(cmd)) {
			statusChange(request, response);
			return;
		}
		
	}
	
	protected void setting(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/eqdetail setting 실행");
		
		String eqId = request.getParameter("eqId");
		EqDTO dto = new EqDTO();
		dto.setEqId(eqId);
		
		EqService service = new EqService();
		dto = service.detail(dto);
		
		System.out.println(dto);
		
		// forward
		request.setAttribute("eqInfo", dto);
		
		int size = 10;
		int page = 1;
		
		String pageStr = request.getParameter("page");
		
		try {
			page = Integer.parseInt(pageStr);			
		} catch (Exception e) {

		}
		
		EqLogDTO logDTO = new EqLogDTO();
		logDTO.setSize(size);
		logDTO.setPage(page);
		logDTO.setEqId(eqId);
		
		Map eqMap = service.getLog(logDTO);
		
		// forward
		request.setAttribute("eqMap", eqMap);
		request.setAttribute("log", eqMap.get("list"));
		
		System.out.println(eqMap);
		System.out.println(eqMap.get("list"));
	}
	

	protected void eqStop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/eqdetail eqStop 실행");
		
		String eqId = request.getParameter("eqId");
		
		EqService service = new EqService();
		int eqStop = service.eqStop(eqId);
		int stopLog = service.stopLog(eqId);
		
		System.out.println(eqStop);
		System.out.println(stopLog);
		
		String cp = request.getContextPath();
		response.sendRedirect(cp + "/eqdetail?eqId=" + eqId);

	}
	
	protected void eqRun(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/eqdetail eqRun 실행");
		
		String eqId = request.getParameter("eqId");
		
		EqService service = new EqService();
		int eqRun = service.eqRun(eqId);
		int startLog = service.startLog(eqId);
		
		System.out.println(eqRun);
		System.out.println(startLog);

		String cp = request.getContextPath();
		response.sendRedirect(cp + "/eqdetail?eqId=" + eqId);
	}

	protected void statusChange(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/eqdetail statusChange 실행");
		
		String eqId = request.getParameter("eqId");
		String status = request.getParameter("status");
		
		EqService service = new EqService();
		int statusChange = service.statusChange(status, eqId);
		
		System.out.println(statusChange);
		
		String cp = request.getContextPath();
		response.sendRedirect(cp + "/eqdetail?eqId=" + eqId);

	}

}
