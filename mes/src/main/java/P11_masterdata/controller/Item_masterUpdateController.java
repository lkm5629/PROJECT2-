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

		// 한글 처리
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");

		// 파라메타 확보
		String item_id = request.getParameter("item_id");
		int g_id = Integer.parseInt(request.getParameter("g_id"));
		String unit = request.getParameter("unit");
		int spec = Integer.parseInt(request.getParameter("spec"));
		String item_name = request.getParameter("item_name");


		// DTO 담기
		Item_masterDTO item_masterDTO = new Item_masterDTO();

		// 바구니 안에 넣기
		item_masterDTO.setItem_id(item_id);
		item_masterDTO.setG_id(g_id);
		item_masterDTO.setUnit(unit);
		item_masterDTO.setSpec(spec);
		item_masterDTO.setItem_name(item_name);

		// update 실행
		int result = updateItem(item_masterDTO);

		// update후 다시 페이지 복귀
		if (result == 0) {
			response.sendRedirect(request.getContextPath() + "/itemMaster");
		} else {
			response.sendRedirect(request.getContextPath() + "/itemMaster");
		}
	}

	// 서비스
	public int updateItem(Item_masterDTO item_masterDTO) {
		// DAO로 전달하기
		Item_masterDAO item_masterDAO = new Item_masterDAO();
		int update = item_masterDAO.updateItem(item_masterDTO);

		return update;
	}
}
