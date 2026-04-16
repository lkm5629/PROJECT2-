package P08_quality.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import P08_quality.FinIoDTO;
import P08_quality.QcService;

@WebServlet("/fin")
public class FinIoController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/fin doGet 실행");

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
		itemIn(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/fin doGet 실행");

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
	}
	
	
	protected void itemIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    System.out.println("/fin itemIn 실행");

	    request.setCharacterEncoding("utf-8");
	    response.setContentType("text/html; charset=utf-8;");

	    try {
	        String empId = request.getParameter("empId");
	        String itemId = request.getParameter("itemId");
	        String woId = request.getParameter("woId");
	        String qcId = request.getParameter("qcId");

	        int qty = Integer.parseInt(request.getParameter("qty"));
	        Date date = Date.valueOf(request.getParameter("date"));

	        if (empId == null || empId.trim().isEmpty()) {
	            throw new RuntimeException("작업자 정보가 없습니다.");
	        }
	        if (itemId == null || itemId.trim().isEmpty()) {
	            throw new RuntimeException("제품 정보가 없습니다.");
	        }
	        if (woId == null || woId.trim().isEmpty()) {
	            throw new RuntimeException("작업지시 정보가 없습니다.");
	        }
	        if (qcId == null || qcId.trim().isEmpty()) {
	            throw new RuntimeException("검사 정보가 없습니다.");
	        }
	        if (qty <= 0) {
	            throw new RuntimeException("입고 수량이 0 이하입니다.");
	        }
	        if (date == null) {
	            throw new RuntimeException("입고 기준 날짜가 없습니다.");
	        }

	        FinIoDTO ioDTO = new FinIoDTO();
	        ioDTO.setEmpId(empId);
	        ioDTO.setItemId(itemId);
	        ioDTO.setQty(qty);
	        ioDTO.setDate(date);

	        QcService service = new QcService();
	        service.processFinIn(woId, qcId, ioDTO);

	        response.sendRedirect("/mes/qcdetail?qcId=" + qcId);

	    } catch (Exception e) {
	        throw new ServletException("입고 처리 중 오류 발생", e);
	    }
	}

}
