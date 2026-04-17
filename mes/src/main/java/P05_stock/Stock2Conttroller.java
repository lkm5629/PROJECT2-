package P05_stock;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/stock")
public class Stock2Conttroller extends HttpServlet {

    Stock2Service service = new Stock2Service();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        int size = 10;
        int page = 1;
        String sSize         = request.getParameter("size");
        String sPage         = request.getParameter("page");
        String filterGId     = request.getParameter("filterGId");
        String filterKeyword = request.getParameter("filterKeyword");
        String filterStock   = request.getParameter("filterStock");

        try { if (sSize != null) size = Integer.parseInt(sSize); } catch (Exception e) {}
        try { if (sPage != null) page = Integer.parseInt(sPage); } catch (Exception e) {}
        if (filterGId     != null && filterGId.isEmpty())     filterGId     = null;
        if (filterKeyword != null && filterKeyword.isEmpty()) filterKeyword = null;
        if (filterStock   != null && filterStock.isEmpty())   filterStock   = null;

        Stock2DTO dto = new Stock2DTO();
        dto.setSize(size);
        dto.setPage(page);
        dto.setFilterGId(filterGId);
        dto.setFilterKeyword(filterKeyword);
        dto.setFilterStock(filterStock);

        Map map = service.getStockList(dto);
        map.put("size",          size);
        map.put("page",          page);
        map.put("groupList",     service.getGroupList());
        map.put("filterGId",     filterGId);
        map.put("filterKeyword", filterKeyword);
        map.put("filterStock",   filterStock);
        map.put("totalStock",    service.getTotalCount());
        map.put("normalStock",   service.getNormalCount());
        map.put("lackStock",     service.getLackCount());

        request.setAttribute("map", map);
        request.getRequestDispatcher("WEB-INF/views/P05_stock/stock.jsp")
               .forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if ("updateSafeNo".equals(action)) {
            String itemId = request.getParameter("item_id");  // ★ stock_id → item_id
            int safeNo = 0;
            try { safeNo = Integer.parseInt(request.getParameter("safe_no")); } catch (Exception e) {}
            service.updateSafeNo(itemId, safeNo);  // ★ stockId → itemId

            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write("{\"result\":\"ok\"}");
            return;
        }

        response.sendRedirect("/mes/stock");
    }
}