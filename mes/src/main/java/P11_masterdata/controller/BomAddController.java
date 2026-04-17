package P11_masterdata.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import P11_masterdata.DAO.BomDAO;
import P11_masterdata.DTO.BomDTO;

/**
 * Servlet implementation class BomAddController
 */
@WebServlet("/BomAddController")
public class BomAddController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BomAddController() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/bom");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 한글처리
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		// 파라메터 확보(이름이 "" 인 것)
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
		// DTO 담기(가져오고!)
		if (parent_item_id != null && bom_id != null && !bom_id.trim().equals("")) {
			BomDTO bomDTO = new BomDTO();
		// content 넣기 바구니 안에
			bomDTO.setBom_id(bom_id);
			bomDTO.setParent_item_id(parent_item_id);

		// 서비스로 DTO 보내기
			addBom(bomDTO);
		}
		// 페이지 변경까지!
		response.sendRedirect(request.getContextPath() + "/bom");
	}
	public int addBom(BomDTO bomDTO) {
		// 서비스니까 DAO(DB)로 전달하기
		BomDAO bomDAO = new BomDAO();
		// DAO(DB)안에 DTO 담기
		int add = bomDAO.insertBom(bomDTO);
		// 리턴
		return add;
	}
}
