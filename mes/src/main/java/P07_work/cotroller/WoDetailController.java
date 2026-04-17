package P07_work.cotroller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import P07_work.ProcessDTO;
import P07_work.WoBOMDTO;
import P07_work.WoDTO;
import P07_work.WoService;

@WebServlet("/workorder")
public class WoDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/workorder doGet 실행");

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8;");
		
		setting(request, response);
		setBOM(request, response);
		setProcess(request, response);
		
		request.getRequestDispatcher("/WEB-INF/views/P07_work/detail.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/workorder doPost 실행");

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8;");
		
	}
	
	protected void setting (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/workorder setting 실행");
		
		String woId = request.getParameter("woId");
		WoDTO dto = new WoDTO();
		dto.setWoId(woId);
		
		WoService service = new WoService();
		dto = service.detail(dto);
		
		System.out.println(dto);
		
		// forward
		request.setAttribute("woInfo", dto);
	}
	
	protected void setBOM(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/workorder setBOM 실행");
		
		String woId = request.getParameter("woId");
		WoBOMDTO dto = new WoBOMDTO();
		dto.setWoId(woId);
		
		WoService service = new WoService();
		List<WoBOMDTO> bom = service.setBOM(dto);
		
		System.out.println(bom);
		
		// forward
		request.setAttribute("bom", bom);
	}
	
	protected void setProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/workorder setProcess 실행");
		
		String woId = request.getParameter("woId");
		System.out.println(woId);
		
		ProcessDTO dto = new ProcessDTO();
		
		dto.setWoId(woId);
		
		WoService service = new WoService();
		List<ProcessDTO> process = service.setProcess(dto);
		
		System.out.println(process);
		
		// forward
		request.setAttribute("process", process);
	}

}
