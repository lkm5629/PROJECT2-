package P03_suggestion;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import P01_auth.LoginDTO;

@WebServlet("/suggestion/*")
public class SuggestionController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    SuggestionService suggestionService = new SuggestionService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) pathInfo = "/list";

        switch (pathInfo) {

            case "/list":
                int size = 10;
                int page = 1;
                try { size = Integer.parseInt(request.getParameter("size")); } catch (Exception e) {}
                try { page = Integer.parseInt(request.getParameter("page")); } catch (Exception e) {}

                SuggestionDTO dto = new SuggestionDTO();
                dto.setSize(size);
                dto.setPage(page);

                Map<String, Object> map = suggestionService.getList(dto);
                map.put("size", size);
                map.put("page", page);

                request.setAttribute("map", map);
                request.getRequestDispatcher(
                    "/WEB-INF/views/P03_suggestion/list.jsp"
                ).forward(request, response);
                break;

            case "/detail":
                String boardno = request.getParameter("boardno");

                SuggestionDTO detail = suggestionService.getDetail(boardno);
                request.setAttribute("detail", detail);
                request.setAttribute("page", request.getParameter("page"));
                request.setAttribute("size", request.getParameter("size"));

                request.getRequestDispatcher(
                    "/WEB-INF/views/P03_suggestion/detail.jsp"
                ).forward(request, response);
                break;

            case "/register":
                request.getRequestDispatcher(
                    "/WEB-INF/views/P03_suggestion/register.jsp"
                ).forward(request, response);
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/suggestion/list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) pathInfo = "";

        switch (pathInfo) {

            case "/insert":
                // 세션에서 로그인 정보 가져오기
                LoginDTO loginDto = (LoginDTO) request.getSession().getAttribute("dto");
                if (loginDto == null) {
                    response.sendRedirect(request.getContextPath() + "/login.jsp");
                    return;
                }
                String empId = loginDto.getEmpid();

                SuggestionDTO insertDto = new SuggestionDTO();
                insertDto.setTitle(request.getParameter("title"));
                insertDto.setContent(request.getParameter("content"));
                insertDto.setEmpId(empId);

                suggestionService.insert(insertDto);
                response.sendRedirect(request.getContextPath() + "/suggestion/list?page=1");
                break;

            case "/detail":
                // 답변완료 처리 (action=complete)
                String action        = request.getParameter("action");
                String detailBoardno = request.getParameter("boardno");

                if ("complete".equals(action)) {
                    suggestionService.updateComplete(detailBoardno);
                }
                response.sendRedirect(request.getContextPath() + "/suggestion/detail?boardno=" + detailBoardno);
                break;

            case "/comment":
                // 댓글 등록
                String commentBoardno = request.getParameter("boardno");
                String commentContent = request.getParameter("commentContent");

                suggestionService.insertComment(commentBoardno, commentContent);
                response.sendRedirect(request.getContextPath() + "/suggestion/detail?boardno=" + commentBoardno);
                break;

            case "/delete":
                String deleteBoardno = request.getParameter("boardno");
                suggestionService.delete(deleteBoardno);
                response.sendRedirect(request.getContextPath() + "/suggestion/list?page=1");
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/suggestion/list");
        }
    }
}