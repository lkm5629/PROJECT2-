package P05_stock;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/stockcontroller")
public class Controller extends HttpServlet {
	StockDTO stockDTO = new StockDTO();
	StockService service = new StockService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		// 응답의 한글 깨짐 방지
		response.setContentType("text/html; charset=utf-8;");
		response.getWriter().println("/stockcontroller doget 실행");
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		// 응답의 한글 깨짐 방지
		response.setContentType("text/html; charset=utf-8;");
		response.getWriter().println("/stockcontroller doPost 실행");
		
		//getParameter 등록/수정 모달
		// 입출고 분류
		int io_type = Integer.parseInt(request.getParameter("io_type"));

		// 자재 대분류
		String itemgroup_name = request.getParameter("itemgroup_name");

		// 자재 소분류 - 임시로 Item_name에 담음, 추후 item_id로 변경될 수 있음
		String item_id = request.getParameter("item_id");

		// 규격
		Integer sepc = Integer.parseInt(request.getParameter("sepc"));

		// 단위
		String unit = request.getParameter("unit");

		// 수량
		int lot_qty = Integer.parseInt(request.getParameter("lot_qty"));

		// 입출고일
		Date io_time = Date.valueOf(request.getParameter("io_time"));

		// 유통기한
		Date expiry_date = Date.valueOf(request.getParameter("expiry_date"));

		// 입출고사유
		String io_reason = request.getParameter("io_reason");

		// 거래처
		String vender_id = request.getParameter("vender_id");

		// LOT 번호
		String lot_id = request.getParameter("lot_id");

		// 작업자
		String emp_id = request.getParameter("emp_id");

		// DTO에 담기
		StockDTO dto = new StockDTO();
		dto.setIo_type(io_type);
		dto.setItemgroup_name(itemgroup_name);
		dto.setItem_id(item_id); // 임시: 소분류를 Item_name에 담음, 추후 item_id로 변경될 수 있음
		dto.setSepc(sepc);
		dto.setUnit(unit);
		dto.setLot_qty(lot_qty);
		dto.setI0_time(io_time);
		dto.setExpiry_date(expiry_date);
		dto.setIo_reason(io_reason);
		dto.setVender_id(vender_id);
		dto.setLot_id(lot_id);
		dto.setEmp_id(emp_id);
		
		service.select(dto);
		
		
		
	}

}
