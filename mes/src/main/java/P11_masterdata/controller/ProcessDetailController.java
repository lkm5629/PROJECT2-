package P11_masterdata.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import P11_masterdata.DAO.ProcessDAO;
import P11_masterdata.DTO.ProcessDTO;

@WebServlet("/processDetail")
public class ProcessDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ProcessDetailController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		ProcessDAO processDAO = new ProcessDAO();
		String processId = request.getParameter("processId");

		// processId 없이 들어오면 첫 번째 공정을 기본으로 보여줌
		if (processId == null || processId.trim().equals("")) {
			List<ProcessDTO> processList = processDAO.selectProcessList();

			if (processList != null && !processList.isEmpty()) {
				processId = processList.get(0).getProcess_id();
			}
		}

		ProcessDTO processDetail = null;
		List<ProcessDTO> materialList = null;
		List<ProcessDTO> equipmentList = null;

		if (processId != null && !processId.trim().equals("")) {
			processDetail = processDAO.selectProcessDetail(processId);
			materialList = processDAO.selectMaterialList(processId);
			equipmentList = processDAO.selectEquipmentList(processId);
		}

		request.setAttribute("processDetail", processDetail);
		request.setAttribute("materialList", materialList);
		request.setAttribute("equipmentList", equipmentList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/processDetail.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
