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

@WebServlet("/womodify")
public class WoModifyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/womodify doGet 실행");
		

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
		setContent(request, response);
		
		request.getRequestDispatcher("/WEB-INF/views/P07_work/woModify.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/womodify doPost 실행");
		

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
		String cmd = request.getParameter("cmd");
		
		if ("update".equals(cmd)) {
			modifyWo(request, response);
		} else if ("delete".equals(cmd)) {
			deleteWo(request, response);
		}
		
	}
	
	protected void setContent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/womodify setContent 실행");
		
		String woId = request.getParameter("woId");
		WoDTO dto = new WoDTO();
		dto.setWoId(woId);
		
		WoService service = new WoService();
		WoDTO woDTO = service.detail(dto);
		
		System.out.println(dto);
		
		// forward
		request.setAttribute("woInfo", dto);

	}
	
	protected void modifyWo(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    System.out.println("/workadd modifyWo 실행");

	    request.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json; charset=UTF-8");
	    
	    String woId = request.getParameter("woId");
	    int woQty = Integer.parseInt(request.getParameter("targetQty"));
	    String workDate = request.getParameter("workDate");
	    String worker = request.getParameter("workerId");
	    String content = request.getParameter("content");
	    
	    WoAddDTO addDTO = new WoAddDTO();
	    
	    addDTO.setWoId(woId);
	    addDTO.setWoQty(woQty);
	    addDTO.setWorkDate(workDate);
	    addDTO.setWorker(worker);
	    addDTO.setContent(content);
	    
	    WoService service = new WoService();
	    int result = service.modifyOrder(addDTO);

	    System.out.println(result);
		
		response.sendRedirect("/mes/workorder?woId=" + addDTO.getWoId());
	}
	
	protected void deleteWo(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    System.out.println("/workadd deleteWo 실행");

	    request.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json; charset=UTF-8");
	    
	    String woId = request.getParameter("woId");
	    
	    WoService service = new WoService();
	    int result = service.deleteOrder(woId);

	    System.out.println(result);
		
		response.sendRedirect("/mes/worklist");
	}

}
