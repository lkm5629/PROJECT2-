package P03_notice;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/notice/*")
public class NoticeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    NoticeService noticeService = new NoticeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        // ④ auth 권한 하드코딩 (세션 연동 시 아래 두 줄을 교체)
        // TODO: String loginId = (String) request.getSession().getAttribute("loginId");
        // TODO: int auth = (int) request.getSession().getAttribute("auth");
        String loginId = "admin";
        int auth = 3;

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) pathInfo = "/list";

        switch (pathInfo) {

            case "/list": {
                int size = 10;
                int page = 1;
                try { size = Integer.parseInt(request.getParameter("size")); } catch (Exception e) {}
                try { page = Integer.parseInt(request.getParameter("page")); } catch (Exception e) {}

                // ① 검색어 수신
                String keyword = request.getParameter("keyword");

                NoticeDTO noticeDTO = new NoticeDTO();
                noticeDTO.setSize(size);
                noticeDTO.setPage(page);
                noticeDTO.setKeyword(keyword);

                Map map = noticeService.getListNotice(noticeDTO);
                map.put("size",    size);
                map.put("page",    page);
                map.put("keyword", keyword);  // JSP 검색창 유지용
                map.put("auth",    auth);      // ④ JSP 버튼 노출 제어용

                int totalCount    = (int) map.get("totalCount");
                int totalPages    = (int) Math.ceil((double) totalCount / size);
                if (totalPages < 1) totalPages = 1;

                int pageGroupSize  = 5;
                int currentGroup   = (int) Math.ceil((double) page / pageGroupSize);
                int groupStartPage = (currentGroup - 1) * pageGroupSize + 1;
                int groupEndPage   = Math.min(groupStartPage + pageGroupSize - 1, totalPages);

                map.put("totalPages",     totalPages);
                map.put("groupStartPage", groupStartPage);
                map.put("groupEndPage",   groupEndPage);

                request.setAttribute("map", map);
                request.getRequestDispatcher(
                    "/WEB-INF/views/P03_notice/noticeList.jsp"
                ).forward(request, response);
                break;
            }

            case "/detail": {
                String boardno = request.getParameter("boardno");

                if (boardno == null || boardno.trim().isEmpty()) {
                    response.sendRedirect(request.getContextPath() + "/notice/list");
                    return;
                }

                NoticeDTO dto = noticeService.selectOneNotice(boardno);

                if (dto == null) {
                    response.sendRedirect(request.getContextPath() + "/notice/list");
                    return;
                }

                // 쿠키로 조회수 중복 방지 (emp_id 포함)
                String cookieName = "notice_" + boardno + "_" + loginId;

                boolean viewed = false;
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie c : cookies) {
                        if (c.getName().equals(cookieName)) {
                            viewed = true;
                            break;
                        }
                    }
                }

                if (!viewed) {
                    noticeService.updateViews(boardno);
                    Cookie cookie = new Cookie(cookieName, "1");
                    cookie.setMaxAge(60 * 60 * 24);
                    response.addCookie(cookie);
                    dto.setViews(dto.getViews() + 1);
                }

                request.setAttribute("dto",  dto);
                request.setAttribute("page", request.getParameter("page"));
                request.setAttribute("size", request.getParameter("size"));
                request.setAttribute("auth", auth);  // ④ 수정/삭제 버튼 노출 제어용

                request.getRequestDispatcher(
                    "/WEB-INF/views/P03_notice/noticeDetail.jsp"
                ).forward(request, response);
                break;
            }

            case "/register": {
                // ④ auth=3 아니면 목록으로
                if (auth != 3) {
                    response.sendRedirect(request.getContextPath() + "/notice/list");
                    return;
                }
                request.getRequestDispatcher(
                    "/WEB-INF/views/P03_notice/noticeRegister.jsp"
                ).forward(request, response);
                break;
            }

            case "/edit": {
                // ④ auth=3 아니면 목록으로
                if (auth != 3) {
                    response.sendRedirect(request.getContextPath() + "/notice/list");
                    return;
                }
                String boardno = request.getParameter("boardno");
                NoticeDTO dto = noticeService.selectOneNotice(boardno);
                request.setAttribute("dto", dto);

                request.getRequestDispatcher(
                    "/WEB-INF/views/P03_notice/noticeEdit.jsp"
                ).forward(request, response);
                break;
            }

            default:
                response.sendRedirect(request.getContextPath() + "/notice/list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        // ④ auth 권한 하드코딩 (세션 연동 시 교체)
        // TODO: String loginId = (String) request.getSession().getAttribute("loginId");
        // TODO: int auth = (int) request.getSession().getAttribute("auth");
        String loginId = "admin";
        int auth = 3;

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) pathInfo = "";

        switch (pathInfo) {

            case "/insert": {
                // ④ auth=3 아니면 목록으로
                if (auth != 3) {
                    response.sendRedirect(request.getContextPath() + "/notice/list");
                    return;
                }
                NoticeDTO dto = new NoticeDTO();
                dto.setTitle(   request.getParameter("title") );
                dto.setContent( request.getParameter("content") );
                dto.setEmpId(loginId);

                noticeService.insertNotice(dto);
                response.sendRedirect(request.getContextPath() + "/notice/list?page=1");
                break;
            }

            case "/update": {
                // ④ auth=3 아니면 목록으로
                if (auth != 3) {
                    response.sendRedirect(request.getContextPath() + "/notice/list");
                    return;
                }
                NoticeDTO dto = new NoticeDTO();
                dto.setBoardno( request.getParameter("boardno") );
                dto.setTitle(   request.getParameter("title") );
                dto.setContent( request.getParameter("content") );

                noticeService.updateNotice(dto);
                response.sendRedirect(request.getContextPath() + "/notice/detail?boardno=" + dto.getBoardno());
                break;
            }

            case "/delete": {
                // ④ auth=3 아니면 목록으로
                if (auth != 3) {
                    response.sendRedirect(request.getContextPath() + "/notice/list");
                    return;
                }
                String boardno = request.getParameter("boardno");
                noticeService.deleteNotice(boardno);
                response.sendRedirect(request.getContextPath() + "/notice/list?page=1");
                break;
            }

            default:
                response.sendRedirect(request.getContextPath() + "/notice/list");
        }
    }
}