package P08_quality.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import P08_quality.QcDTO;
import P08_quality.QcDefDTO;
import P08_quality.QcDisposeDTO;
import P08_quality.QcService;

@WebServlet("/qcresultmodify")
public class QcResultController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/qcresultmodify doGet 실행");

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8;");
		
		setContent(request, response);
		defList(request, response);
		disposeSum(request, response);
		
		request.getRequestDispatcher("/WEB-INF/views/P08_quality/resultModify.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/qcresultmodify doPost 실행");
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8;");
		
		String cmd = request.getParameter("cmd");
		System.out.println(cmd);
		
		if("defectAdd".equals(cmd)) {
			defectAdd(request, response);

		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");

		    response.getWriter().write("{\"success\":true}");
		} else if("defectUpdate".equals(cmd)) {
			defectUpdate(request, response);
		} else if("defectDelete".equals(cmd)) {
			defectDelete(request, response);
		} else if("modify".equals(cmd)) {
			modifyResult(request, response);
		}
		
	}
	
	protected void setContent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/qcresultmodify setContent 실행");
		
		String qcId = request.getParameter("qcId");
		QcDTO dto = new QcDTO();
		dto.setQcId(qcId);
		
		QcService service = new QcService();
		QcDTO QcDTO = service.detail(dto);
		
		System.out.println(QcDTO);
		
		// forward
		request.setAttribute("qcInfo", QcDTO);

	}
	
	protected void defectAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/qcresultmodify defectAdd 실행");
		
		String qcId = request.getParameter("qcId");
		int defQty = Integer.parseInt(request.getParameter("defQty"));
		int defType = Integer.parseInt(request.getParameter("defType"));
		String solution = request.getParameter("solution");
		String disposeStr = request.getParameter("dispose");
		String dispose = null;
		if ("Y".equals(disposeStr)) {
			dispose = "Y";
		}
		
		QcDefDTO dto = new QcDefDTO();
		dto.setDefCnt(defQty);
		dto.setdType(defType);
		dto.setSolution(solution);
		dto.setDispose(dispose);
		
		QcService service = new QcService();
		int result = service.addDef(qcId, dto);
		
	}
	
	protected void defList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/qcresultmodify defList 실행");
		
		String qcId = request.getParameter("qcId");
		
		QcService service = new QcService();
		List defList = service.defContent(qcId);
		
		System.out.println(defList);
		
		// forward
		request.setAttribute("defList", defList);

	}
	
	protected void disposeSum(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/qcresultmodify disposeSum 실행");
		
		String qcId = request.getParameter("qcId");
		
		QcDisposeDTO disDTO = new QcDisposeDTO();
		disDTO.setQcId(qcId);
		
		QcService service = new QcService();
		disDTO = service.disposeSum(disDTO);
		
		System.out.println(disDTO);
		
		// forward
		request.setAttribute("dispose", disDTO);

	}
	
	protected void defectUpdate (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/qcresultmodify defectUpdate 실행");
		
		String defId = request.getParameter("defectId");
		int defQty = Integer.parseInt(request.getParameter("defQty"));
		int defType = Integer.parseInt(request.getParameter("defType"));
		String solution = request.getParameter("solution");
		
		String disposeStr = request.getParameter("dispose");
		String dispose = null;
		if ("Y".equals(disposeStr)) {
			dispose = "Y";
		}
		
		QcDefDTO dto = new QcDefDTO();
		dto.setDefId(defId);
		dto.setDefCnt(defQty);
		dto.setdType(defType);
		dto.setSolution(solution);
		dto.setDispose(dispose);
		
		QcService service = new QcService();
		int result = service.updateDef(dto);
		

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		response.getWriter().write("{\"success\":" + (result > 0) + "}");
	}
	
	protected void defectDelete (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/qcresultmodify defectDelete 실행");
		
		String defId = request.getParameter("defectId");
		
		QcService service = new QcService();
		int result = service.deleteDef(defId);
		

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		response.getWriter().write("{\"success\":" + (result > 0) + "}");
	}
	
	protected void modifyResult(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/qcresultmodify modifyResult 실행");
		
		String qcId = request.getParameter("qcId");
		
		// qc
		Date eDate = Date.valueOf(request.getParameter("eDate"));
		int qcStatus = Integer.parseInt(request.getParameter("status"));
		
		QcDTO dto = new QcDTO();
		
		dto.setQcId(qcId);
		dto.seteDate(eDate);
		dto.setQcStatus(qcStatus);
		
		QcService service = new QcService();
		int result = service.modifyResult(dto);
		
		response.sendRedirect("/mes/qcdetail?qcId=" + qcId);
	}

}
