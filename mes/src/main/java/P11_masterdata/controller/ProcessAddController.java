package P11_masterdata.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import P11_masterdata.DAO.ProcessDAO;
import P11_masterdata.DTO.ProcessDTO;

@WebServlet("/ProcessAddController")
public class ProcessAddController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ProcessAddController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 한글처리
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");

		// 파라메타 확보
		String process_id = request.getParameter("process_id");
		
		int seq = Integer.parseInt(request.getParameter("seq"));
		String step_name = request.getParameter("step_name");

		// DTO에 담기
		ProcessDTO processDTO = new ProcessDTO();
		processDTO.setProcess_id(process_id);
		processDTO.setSeq(seq);
		processDTO.setStep_name(step_name);
		
		// addProcess 호출
		int result = addProcess(processDTO);

		// insert후 다시 페이지 복귀
		if (result == 0) {
			response.sendRedirect(request.getContextPath() + "/process");
		} else {
			response.sendRedirect(request.getContextPath() + "/process");
		}
	}

	public int addProcess(ProcessDTO processDTO) {
		ProcessDAO processDAO = new ProcessDAO();
		return processDAO.insertProcess(processDTO);
	}


}
