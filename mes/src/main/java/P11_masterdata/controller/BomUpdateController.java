package P11_masterdata.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import P11_masterdata.DAO.BomDAO;
import P11_masterdata.DTO.BomDTO;

@WebServlet("/BomUpdateController")
public class BomUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BomUpdateController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/bom");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");

		String bom_id = request.getParameter("bom_id");
		String item_name = request.getParameter("item_name");
		String g_id_str = request.getParameter("g_id");

		int g_id = 0;
		try {
			g_id = Integer.parseInt(g_id_str);
		} catch (Exception e) {
			g_id = 0;
		}

		BomDAO bomDAO = new BomDAO();
		String parent_item_id = bomDAO.selectItemIdByNameAndGroup(item_name, g_id);

		if (bom_id != null && !bom_id.trim().equals("")
				&& parent_item_id != null && !parent_item_id.trim().equals("")) {

			BomDTO bomDTO = new BomDTO();
			bomDTO.setBom_id(bom_id);
			bomDTO.setParent_item_id(parent_item_id);

			updateBom(bomDTO);
		}

		response.sendRedirect(request.getContextPath() + "/bom");
	}

	private int updateBom(BomDTO bomDTO) {
		BomDAO bomDAO = new BomDAO();
		return bomDAO.updateBom(bomDTO);
	}
}