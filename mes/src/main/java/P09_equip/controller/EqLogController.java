package P09_equip.controller;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import P09_equip.EqService;
import P09_equip.DTO.EqLogDTO;

@WebServlet("/eqlogadd")
public class EqLogController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/eqlogadd doGet 실행");

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8;");
		
		setEqInfo(request, response);
		
		request.getRequestDispatcher("/WEB-INF/views/P09_equip/addInsp.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/eqlogadd doPost 실행");

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8;");
		
		addLog(request, response);
	}
	
	protected void setEqInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/eqlogadd setEqInfo 실행");
		
		String eqId = request.getParameter("eqId");
		String eqName = request.getParameter("eqName");
		
		request.setAttribute("eqId", eqId);
		request.setAttribute("eqName", eqName);
	}
	

	protected void addLog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/eqlogadd addLog 실행");
		EqLogDTO dto = new EqLogDTO();
		
		String eqId = request.getParameter("eqId");
		String wId = request.getParameter("wId");
		
		String sDateStr = request.getParameter("sDate");
		String sTimeStr = request.getParameter("sTime")+":00";
		String sTimeFull = sDateStr + " " + sTimeStr;
		Timestamp sTime = Timestamp.valueOf(sTimeFull);
		
		String eDateStr = request.getParameter("eDate");
		System.out.println("eDate : " + eDateStr);
		Timestamp eTime = null;
		if (!("".equals(eDateStr))) {
			String eTimeStr = request.getParameter("eTime")+":00";
			String eTimeFull = eDateStr + " " + eTimeStr;
			eTime = Timestamp.valueOf(eTimeFull);
			dto.seteTime(eTime);
		}
		
		String inspType = request.getParameter("inspType");
		String content = request.getParameter("content");
		
		
		dto.setEqId(eqId);
		dto.setwId(wId);
		dto.setsTime(sTime);
		dto.setInspType(inspType);
		dto.setInspContent(content);
		
		EqService service = new EqService();
		int result = service.addLog(dto);
		
		System.out.println(result);
		
		response.sendRedirect("/mes/eqdetail?eqId=" + eqId);
	}

}
