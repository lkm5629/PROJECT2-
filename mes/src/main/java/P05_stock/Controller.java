package P05_stock;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/stockcontroller")
public class Controller extends HttpServlet {
    StockService service = new StockService();
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8;");
        
        int size = 10;
        int page = 1;
        
        String sSize = request.getParameter("size");
        String sPage = request.getParameter("page");
        
        try { if(sSize != null) size = Integer.parseInt(sSize); } catch (Exception e) { e.printStackTrace(); }
        try { if(sPage != null) page = Integer.parseInt(sPage); } catch (Exception e) { e.printStackTrace(); }
        
        StockDTO stockDTO = new StockDTO();
        stockDTO.setSize(size);
        stockDTO.setPage(page);
        
        Map map = service.getListStock(stockDTO);
        map.put("size", size);
        map.put("page", page);
        
        // 모달 셀렉트박스용 목록 추가
        map.put("vendorList", service.getVendorList());
        map.put("itemList", service.getItemList());
        
        request.setAttribute("map", map);
        request.getRequestDispatcher("io.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8;");
        
        StockDTO dto = new StockDTO();
        
        try {
            dto.setIo_type(Integer.parseInt(request.getParameter("io_type")));
            dto.setIo_reason(request.getParameter("io_reason"));
            dto.setVender_id(request.getParameter("vender_id"));
            dto.setItem_id(request.getParameter("item_id"));
            dto.setLot_id(request.getParameter("lot_id"));
            dto.setLot_qty(Integer.parseInt(request.getParameter("lot_qty")));
            dto.setIo_time(Date.valueOf(request.getParameter("io_time")));
            
            String expiryDateStr = request.getParameter("expiry_date");
            if(expiryDateStr != null && !expiryDateStr.isEmpty()) {
                dto.setExpiry_date(Date.valueOf(expiryDateStr));
            }
            
            dto.setEmp_id(request.getParameter("emp_id"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        service.insert(dto);
        response.sendRedirect("/mes/stockcontroller");
    }
}
