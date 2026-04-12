package P08_quality.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import P08_quality.QcDTO;
import P08_quality.QcDefDTO;
import P08_quality.QcService;

@WebServlet("/qcdetail")
public class QcDetailController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/qcdetail doGet 실행");

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8;");
		
		setting(request, response);
		defContent(request, response);
		
		request.getRequestDispatcher("/WEB-INF/views/P08_quality/detail.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/qcdetail doPost 실행");

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8;");
	}
	
	protected void setting (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/qcdetail setting 실행");
		
		String qcId = request.getParameter("qcId");
		QcDTO dto = new QcDTO();
		dto.setQcId(qcId);
		
		QcService service = new QcService();
		dto = service.detail(dto);
		
		System.out.println(dto);
		
		// forward
		request.setAttribute("qcInfo", dto);
	}
	
	protected void defContent (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/qcdetail defContent 실행");
		
		String qcId = request.getParameter("qcId");
		
		QcService service = new QcService();
		List<QcDefDTO> defList = service.defContent(qcId);
		
		System.out.println(defList);
		
		// forward
		request.setAttribute("defList", defList);
	}

}
