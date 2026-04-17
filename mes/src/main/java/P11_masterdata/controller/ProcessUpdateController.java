package P11_masterdata.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import P11_masterdata.DAO.ProcessDAO;
import P11_masterdata.DTO.ProcessDTO;

@WebServlet("/processUpdate")
public class ProcessUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ProcessUpdateController() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 한글처리
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");

		// 파라메터 확보
		String process_id = request.getParameter("process_id");
		String process_name = request.getParameter("process_name");
		String process_info = request.getParameter("process_info");
		String process_type = request.getParameter("process_type");

		// DTO 담기(가져오고!)
		ProcessDTO processDTO = new ProcessDTO();
		processDTO.setProcess_id(process_id);
		processDTO.setProcess_name(process_name);
		processDTO.setProcess_info(process_info);
		processDTO.setProcess_type(process_type);

		// update 실행
		int result = updateItem(processDTO);
		// 페이지 변경까지
		response.sendRedirect(request.getContextPath() + "/process?processId=" + process_id);

	}

	private int updateItem(ProcessDTO processDTO) {
		// 서비스니까 DAO(DB)로 전달하기
		ProcessDAO processDAO = new ProcessDAO();
		// DAO(DB)안에 DTO 담기
		int update = processDAO.updateProcess(processDTO);

		return update;
	}

}
