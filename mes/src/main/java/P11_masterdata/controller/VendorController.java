package P11_masterdata.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import P11_masterdata.DAO.VendorDAO;
import P11_masterdata.DTO.VendorDTO;

/**
 * Servlet implementation class VendorController
 */
@WebServlet("/vendor")
public class VendorController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public VendorController() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 한글깨짐 방지
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		// DAO에 list를 가져옴
		List<VendorDTO> list = vendorListSelect();
		// 요약카드 카운트 변수
		int totalVendor = 0; // 전체
		int vendorTypeA = 0; // 공급업체
		int vendorTypeB = 0; // 고객사

		if (list != null) {
			totalVendor = list.size();

			for (VendorDTO dto : list) {
				if ("공급업체".equals(dto.getVendor_type())) {
					vendorTypeA++;
				} else if ("고객사".equals(dto.getVendor_type())) {
					vendorTypeB++;
				}
			}
		}
		// 보내기,// 값 추가
		request.setAttribute("vendor", list);
		
		request.setAttribute("list", list);
		request.setAttribute("totalVendor", totalVendor);
		request.setAttribute("vendorTypeA", vendorTypeA);
		request.setAttribute("vendorTypeB", vendorTypeB);
//		request.setAttribute("page", page);
//		request.setAttribute("totalPage", totalPage);
		// another = 수신자(보낼 곳)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/vendor.jsp");
		// 주소 바꿈 방지
		dispatcher.forward(request, response);
	}

	private List vendorListSelect() {
		VendorDAO vendorDAO = new VendorDAO();
		List select = vendorDAO.selectAll();
		// 리턴
		return select;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
