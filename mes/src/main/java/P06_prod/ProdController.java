package P06_prod;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import P06_prod.ProdDTO;
import P06_prod.ProdService;

@WebServlet("/prod/*")          // ← /* 와일드카드로 변경
public class ProdController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    ProdService prodService = new ProdService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        // /production/list  → pathInfo = "/list"
        // /production/detail → pathInfo = "/detail"
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) pathInfo = "/list";   // 기본값

        switch (pathInfo) {

            /* ── 목록 ─────────────────────────────────────── */
            case "/list": {

                int size = 10;
                int page = 1;

                try { size = Integer.parseInt(request.getParameter("size")); } catch (Exception e) {}
                try { page = Integer.parseInt(request.getParameter("page")); } catch (Exception e) {}

                ProdDTO planDTO = new ProdDTO();
                planDTO.setSize(size);
                planDTO.setPage(page);

                Map map = prodService.getPlanList(planDTO);
                map.put("size", size);
                map.put("page", page);
                request.setAttribute("map", map);

                request.getRequestDispatcher("/WEB-INF/views/P06_prod/prodList.jsp")
                       .forward(request, response);
                break;
            }

            /* ── 상세 ─────────────────────────────────────── */
            case "/detail": {
                String planId = request.getParameter("planId");
                ProdDTO dto = prodService.getPlanDetail(planId);
                request.setAttribute("dto", dto);

                request.getRequestDispatcher("/WEB-INF/views/P06_prod/prodDetail.jsp")
                       .forward(request, response);
                break;
            }

            /* ── 매핑 없는 경로 ───────────────────────────── */
            default: {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
            }
        }
    }
}