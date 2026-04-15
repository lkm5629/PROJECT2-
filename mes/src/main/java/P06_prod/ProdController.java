package P06_prod;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/prod/*")
public class ProdController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    ProdService prodService = new ProdService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) pathInfo = "/list";

        switch (pathInfo) {

            /* ── 목록 ──────────────────────────────────────────── */
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

                request.setAttribute("groupList", prodService.getGroupList());
                request.setAttribute("itemList",  prodService.getItemList());

                request.getRequestDispatcher("/WEB-INF/views/P06_prod/prodList.jsp")
                       .forward(request, response);
                break;
            }

            /* ── 상세 ──────────────────────────────────────────── */
            case "/detail": {
                String planId = request.getParameter("planId");
                if (planId == null || planId.isEmpty()) {
                    response.sendRedirect("list");
                    break;
                }
                ProdDTO dto = prodService.getPlanDetail(planId);
                if (dto == null) {
                    response.sendRedirect("list");
                    break;
                }
                request.setAttribute("dto", dto);
                request.getRequestDispatcher("/WEB-INF/views/P06_prod/prodDetail.jsp")
                       .forward(request, response);
                break;
            }

            /* ── 담당자 검색 AJAX ──────────────────────────────── */
            case "/api/empSearch": {
                response.setContentType("application/json;charset=utf-8");

                String keyword = request.getParameter("keyword");
                if (keyword == null) keyword = "";
                int size = 5;
                int page = 1;
                try { size = Integer.parseInt(request.getParameter("size")); } catch (Exception e) {}
                try { page = Integer.parseInt(request.getParameter("page")); } catch (Exception e) {}

                Map empMap     = prodService.getEmpList(keyword, page, size);
                List<Map> list = (List<Map>) empMap.get("list");
                int totalCount = (int) empMap.get("totalCount");

                StringBuilder sb = new StringBuilder();
                sb.append("{\"totalCount\":").append(totalCount).append(",\"list\":[");
                for (int i = 0; i < list.size(); i++) {
                    Map emp = list.get(i);
                    if (i > 0) sb.append(",");
                    sb.append("{")
                      .append("\"empId\":\"").append(emp.get("empId")).append("\",")
                      .append("\"ename\":\"").append(emp.get("ename")).append("\",")
                      .append("\"deptName\":\"").append(emp.get("deptName")).append("\"")
                      .append("}");
                }
                sb.append("]}");

                PrintWriter out = response.getWriter();
                out.print(sb.toString());
                out.flush();
                break;
            }

            default: {
                response.sendRedirect("list");
                break;
            }
        }
    }
}