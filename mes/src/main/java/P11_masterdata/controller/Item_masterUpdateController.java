package P11_masterdata.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import P11_masterdata.DAO.Item_masterDAO;
import P11_masterdata.DTO.Item_masterDTO;

@WebServlet("/itemUpdate")
public class Item_masterUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Item_masterUpdateController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");

		String item_id = request.getParameter("item_id");
		int g_id = parseIntOrZero(request.getParameter("g_id"));
		String unit = request.getParameter("unit");
		String spec = request.getParameter("spec");
		String item_name = request.getParameter("item_name");
		int safe_qty = parseIntOrZero(request.getParameter("safe_qty"));
		int pay = parseIntOrZero(request.getParameter("pay"));

		Item_masterDTO item_masterDTO = new Item_masterDTO();

		item_masterDTO.setItem_id(item_id);
		item_masterDTO.setG_id(g_id);
		item_masterDTO.setUnit(unit);
		item_masterDTO.setSpec(spec);
		item_masterDTO.setItem_name(item_name);
		item_masterDTO.setSafe_qty(safe_qty);
		item_masterDTO.setPay(pay);

		int result = updateItem(item_masterDTO);

		if (result == 0) {
			response.sendRedirect(request.getContextPath() + "/itemmaster");
		} else {
			response.sendRedirect(request.getContextPath() + "/itemmaster");
		}
	}

	public int updateItem(Item_masterDTO item_masterDTO) {
		Item_masterDAO item_masterDAO = new Item_masterDAO();
		int update = item_masterDAO.updateItem(item_masterDTO);
		return update;
	}

	private int parseIntOrZero(String value) {
		try {
			if (value == null || value.trim().equals("")) {
				return 0;
			}
			return Integer.parseInt(value.trim().replace(",", ""));
		} catch (Exception e) {
			return 0;
		}
	}
}
