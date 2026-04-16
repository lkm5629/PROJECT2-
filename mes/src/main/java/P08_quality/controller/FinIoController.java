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
	
	
	protected void itemIn (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/fin itemIn 실행");
		
		QcService service = new QcService();
		
		String lotId = service.lotId();
		System.out.println(lotId);
		
		String empId = request.getParameter("empId");
		String itemId = request.getParameter("itemId");
		Integer qty = Integer.parseInt(request.getParameter("qty"));
		Date date = Date.valueOf(request.getParameter("date"));
		
		FinIoDTO ioDTO = new FinIoDTO();
		
		ioDTO.setLotId(lotId);
		ioDTO.setEmpId(empId);
		ioDTO.setItemId(itemId);
		ioDTO.setQty(qty);
		ioDTO.setDate(date);
		
		System.out.println("date : " + date);
		
		int result = service.addLot(ioDTO);
		System.out.println("result 1 : " + result);
		
		int result2 = service.addIn(ioDTO);
		System.out.println("result 2 : " + result2);
		
		int result3 = service.updateStock(ioDTO);
		System.out.println("result 3 : " + result3);
		
		
		String woId = request.getParameter("woId");
		String qcId = request.getParameter("qcId");
		
		int result4 = service.updateWoStatus(woId);
		int result5 = service.updateQcStatus(qcId);
		
		System.out.println("result 4 : " + result4);
		System.out.println("result 5 : " + result5);
		
		int result6 = service.updateWoLot(woId, ioDTO);
		
		System.out.println("result 6 : " + result6);
		
		response.sendRedirect("/mes/qcdetail?qcId=" + qcId);
	}

}
