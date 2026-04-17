package P11_masterdata.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import P11_masterdata.DAO.Item_masterDAO;
import P11_masterdata.DTO.Item_masterDTO;

@WebServlet("/itemMasterAdd")
public class Item_masterAddController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");

		String item_id = request.getParameter("item_id");
		String item_name = request.getParameter("item_name");
		String unit = request.getParameter("unit");
		String spec = request.getParameter("spec");
		int g_id = parseIntOrZero(request.getParameter("g_id"));
		int safe_qty = parseIntOrZero(request.getParameter("safe_qty"));
		int pay = parseIntOrZero(request.getParameter("pay"));
		String itemgroup_name = request.getParameter("itemgroup_name");

		Item_masterDTO item_masterDTO = new Item_masterDTO();
		item_masterDTO.setItem_id(item_id);
		item_masterDTO.setItem_name(item_name);
		item_masterDTO.setUnit(unit);
		item_masterDTO.setSpec(spec);
		item_masterDTO.setG_id(g_id);
		item_masterDTO.setSafe_qty(safe_qty);
		item_masterDTO.setPay(pay);
		item_masterDTO.setItemgroup_name(itemgroup_name);

		int result = addItem(item_masterDTO);

		if (result == 0) {
			response.sendRedirect(request.getContextPath() + "/itemmaster");
		} else {
			response.sendRedirect(request.getContextPath() + "/itemmaster");
		}
	}

	public int addItem(Item_masterDTO item_masterDTO) {
		Item_masterDAO item_masterDAO = new Item_masterDAO();
		int add = item_masterDAO.insertItem(item_masterDTO);
		return add;
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
