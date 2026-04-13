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
		// 한글처리
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");

		// 파라메타 확보
		String item_id = request.getParameter("item_id");
		String item_name = request.getParameter("item_name");
		String unit = request.getParameter("unit");
		int spec = Integer.parseInt(request.getParameter("spec"));
		int g_id = Integer.parseInt(request.getParameter("g_id"));
		String itemgroup_name = request.getParameter("itemgroup_name");

		// DTO에 담기
		Item_masterDTO item_masterDTO = new Item_masterDTO();
		item_masterDTO.setItem_id(item_id);
		item_masterDTO.setItem_name(item_name);
		item_masterDTO.setUnit(unit);
		item_masterDTO.setSpec(spec);
		item_masterDTO.setG_id(g_id);
		item_masterDTO.setItemgroup_name(itemgroup_name);
		
		//addItem 호출
		int result = addItem(item_masterDTO);

		//insert후 다시 페이지 복귀
		if (result == 0) {
			response.sendRedirect(request.getContextPath() + "/itemMaster");
		} else {
			response.sendRedirect(request.getContextPath() + "/itemMaster");
		}
	}

	// 컨트롤러가 DTO전달인자로 todoDTO 받아오기
	public int addItem(Item_masterDTO item_masterDTO) {
		// 서비스니까 DAO(DB)로 전달하기
		Item_masterDAO item_masterDAO = new Item_masterDAO();
		// DAO(DB)안에 DTO 담기
		int add = item_masterDAO.insertItem(item_masterDTO);
		// 리턴
		return add;
	}
}
