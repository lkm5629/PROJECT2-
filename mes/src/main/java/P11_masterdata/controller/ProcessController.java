package P11_masterdata.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import P11_masterdata.DAO.ProcessDAO;
import P11_masterdata.DTO.ProcessDTO;

@WebServlet("/process")
public class ProcessController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ProcessController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		ProcessDAO processDAO = new ProcessDAO();

		List<ProcessDTO> processList = processDAO.selectProcessList();

		String selectedProcessId = request.getParameter("processId");

		if ((selectedProcessId == null || selectedProcessId.trim().equals("")) && !processList.isEmpty()) {
			selectedProcessId = processList.get(0).getProcess_id();
		}

		List<ProcessDTO> stepList = new ArrayList<ProcessDTO>();

		if (selectedProcessId != null && !selectedProcessId.trim().equals("")) {
			stepList = processDAO.selectProcessStepList(selectedProcessId);
		}

		request.setAttribute("processList", processList);
		request.setAttribute("stepList", stepList);
		request.setAttribute("selectedProcessId", selectedProcessId);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/process.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
}
