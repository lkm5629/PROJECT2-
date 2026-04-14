package P07_work.cotroller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import P07_work.WoAddDTO;
import P07_work.WoDTO;
import P07_work.WoService;

/**
 * Servlet implementation class WoContentController
 */
@WebServlet("/contentmodify")
public class WoContentController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/contentmodify doGet 실행");
		

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
		setContent(request, response);
		
		request.getRequestDispatcher("/WEB-INF/views/P07_work/contentModify.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/contentmodify doPost 실행");
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
		updateContent(request, response);
	}
	
	protected void setContent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/contentmodify setContent 실행");
		
		String woId = request.getParameter("woId");
		WoDTO dto = new WoDTO();
		dto.setWoId(woId);
		
		WoService service = new WoService();
		WoDTO woDTO = service.detail(dto);
		
		System.out.println(dto);
		
		// forward
		request.setAttribute("woInfo", dto);

	}
	
	protected void updateContent(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    System.out.println("/workadd modifyWo 실행");
	    
	    String woId = request.getParameter("woId");
	    int status = 0;
	    int prevQty = 0;
	    
	    if (!("".equals(request.getParameter("status")))) {
	    	status = Integer.parseInt(request.getParameter("status"));
	    }
	    if (!("".equals(request.getParameter("prevQty")))) {
	    	prevQty = Integer.parseInt(request.getParameter("prevQty"));
	    }
	    
	    
	    WoService service = new WoService();
	    int result = service.updateContent(woId, status, prevQty);

	    System.out.println(result);
	    
	    updatePlan(request, response);
		
		response.sendRedirect("/mes/workorder?woId=" + woId);
	}
	
	protected void updatePlan(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    System.out.println("/workadd updatePlan 실행");
	    
	    WoService service = new WoService();
	    int result = service.updatePlan();

	    System.out.println(result);
		
	}

}
