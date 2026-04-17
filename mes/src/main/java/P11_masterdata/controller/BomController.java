package P11_masterdata.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import P11_masterdata.DAO.BomDAO;

/**
 * Servlet implementation class bomController
 */
@WebServlet("/bomOld")
public class BomController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BomController() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 한글깨짐 방지
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		// DAO에 list를 가져옴
		List list = bomListSelect();

		// 보내기
		request.setAttribute("bomList", list);
		// another = 수신자(보낼 곳)
		RequestDispatcher dispatcher = request.getRequestDispatcher("bom.jsp");
		// 주소 바꿈 방지
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public List bomListSelect() {
		// 서비스니까 DAO(DB)로 전달하기
		BomDAO bomDAO = new BomDAO();
		List add = bomDAO.selectAll();
		// 리턴
		return add;
	}
}
