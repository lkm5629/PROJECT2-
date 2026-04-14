package P09_equip.controller;

import java.io.IOException;
import java.util.List;

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
		
		setting(request, response);
		
		request.getRequestDispatcher("/WEB-INF/views/P09_equip/detail.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/equipdetail doPost 실행");
		
	}
	
	protected void setting(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/eqdetail setting 실행");
		
		String eqId = request.getParameter("eqId");
		EqDTO dto = new EqDTO();
		dto.setEqId(eqId);
		
		EqService service = new EqService();
		dto = service.detail(dto);
		
		EqLogDTO eqDTO = new EqLogDTO();
		eqDTO.setEqId(eqId);
		List<EqLogDTO> logList = service.getLog(eqDTO);
		
		System.out.println(dto);
		System.out.println(logList);
		
		// forward
		request.setAttribute("eqInfo", dto);
		request.setAttribute("log", logList);
	}

}
