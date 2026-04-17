package P11_masterdata.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import P11_masterdata.DAO.VendorDAO;
import P11_masterdata.DTO.VendorDTO;

@WebServlet("/VendorPageController")
public class VendorPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public VendorPageController() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 한글깨짐 방지
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		int size = 5; // 페이지 당 표시 수
		int page = 1;// 현재 페이지

		String sSize = request.getParameter("size");
		String sPage = request.getParameter("page");

		try {
			size = Integer.parseInt(sSize);
		} catch (Exception e) {
			e.printStackTrace();
		} // 같이 있으면 하나만 안되어도 오류 나므로 따로 분리함
		try {
			page = Integer.parseInt(sPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 디비에 보낼 것을 담아둠
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setSize(size);
		vendorDTO.setPage(page);

		Map<String, Object> map = vendorlistSelect();

		// 보내기(브라우저)
		request.setAttribute("list", map.get("list"));
		request.setAttribute("page", map.get("page"));
		request.setAttribute("size", map.get("size"));
		request.setAttribute("totalPage", map.get("totalPage"));
		request.setAttribute("totalCount", map.get("totalCount"));

		// another = 수신자(보낼 곳)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/vendor.jsp");
		// 주소 바꿈 방지
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public Map vendorlistSelect() {
		// 서비스니까 DAO(DB)로 전달하기
		VendorDAO vendorDAO = new VendorDAO();
		List add = vendorDAO.selectAll();
		// 리턴
		return (Map) add;
	}
}
