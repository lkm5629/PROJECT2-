package P11_masterdata.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import P11_masterdata.DAO.BomDAO;

@WebServlet("/BomDetailDeleteController")
public class BomDetailDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/bom");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		String bomId = request.getParameter("bom_id");
		String sBomDetailId = request.getParameter("bom_detail_id");

		int bomDetailId = 0;
		try {
			bomDetailId = Integer.parseInt(sBomDetailId);
		} catch (Exception e) {
			bomDetailId = 0;
		}

		BomDAO bomDAO = new BomDAO();
		bomDAO.deleteBomDetail(bomDetailId);

		response.sendRedirect(request.getContextPath() + "/bomDetail?bomId=" + bomId);
	}
}
