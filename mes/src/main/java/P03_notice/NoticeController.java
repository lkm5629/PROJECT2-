package P03_notice;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet("/notice/*")
public class NoticeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 파일 저장 경로
    private static final String UPLOAD_PATH =
        "C:\\workspace_proj2\\mes\\src\\main\\webapp\\static\\upload\\notice";

    NoticeService noticeService = new NoticeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // TODO: String loginId = (String) request.getSession().getAttribute("loginId");
        // TODO: int auth = (int) request.getSession().getAttribute("auth");
        String loginId = "user_1001";
        int auth = 3;

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) pathInfo = "/list";

        switch (pathInfo) {

            case "/list": {
                int size = 10;
                int page = 1;
                try { size = Integer.parseInt(request.getParameter("size")); } catch (Exception e) {}
                try { page = Integer.parseInt(request.getParameter("page")); } catch (Exception e) {}

                String keyword = request.getParameter("keyword");

                NoticeDTO noticeDTO = new NoticeDTO();
                noticeDTO.setSize(size);
                noticeDTO.setPage(page);
                noticeDTO.setKeyword(keyword);

                Map map = noticeService.getListNotice(noticeDTO);
                map.put("size",    size);
                map.put("page",    page);
                map.put("keyword", keyword);
                map.put("auth",    auth);

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

                // 쿠키로 조회수 중복 방지
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

                request.setAttribute("noticeDTO",  dto);
                request.setAttribute("page", request.getParameter("page"));
                request.setAttribute("size", request.getParameter("size"));
                request.setAttribute("auth", auth);

                request.getRequestDispatcher(
                    "/WEB-INF/views/P03_notice/noticeDetail.jsp"
                ).forward(request, response);
                break;
            }

            case "/register": {
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
                if (auth != 3) {
                    response.sendRedirect(request.getContextPath() + "/notice/list");
                    return;
                }
                String boardno = request.getParameter("boardno");
                NoticeDTO dto = noticeService.selectOneNotice(boardno);
                request.setAttribute("noticeDTO", dto);

                request.getRequestDispatcher(
                    "/WEB-INF/views/P03_notice/noticeEdit.jsp"
                ).forward(request, response);
                break;
            }

            // ──────────────────────────────────────────────
            // 파일 다운로드
            // 요청 예: /notice/download?save=밀리초_파일명&origin=원본파일명
            // ──────────────────────────────────────────────
            case "/download": {
                String saveName   = request.getParameter("save");
                String originName  = request.getParameter("origin");

                if (saveName == null || saveName.trim().isEmpty()) {
                    response.sendRedirect(request.getContextPath() + "/notice/list");
                    return;
                }

                File file = new File(UPLOAD_PATH + "\\" + saveName);

                if (!file.exists()) {
                    response.setContentType("text/html;charset=UTF-8");
                    response.getWriter().write("<script>alert('파일을 찾을 수 없습니다.'); history.back();</script>");
                    return;
                }

                // 브라우저 캐시 미사용
                response.setHeader("Cache-Control", "no-cache");
                // 다운로드 헤더 - 원본 파일명으로 저장되도록
                String encodedName = new String(originName.getBytes("UTF-8"), "ISO-8859-1");
                response.addHeader("Content-Disposition", "attachment; filename=\"" + encodedName + "\"");
                response.setContentLengthLong(file.length());

                byte[] buf = new byte[1024 * 8];
                try (InputStream is = new FileInputStream(file);
                     OutputStream os = response.getOutputStream()) {
                    int count;
                    while ((count = is.read(buf)) != -1) {
                        os.write(buf, 0, count);
                    }
                    os.flush();
                }
                break;
            }

            default:
                response.sendRedirect(request.getContextPath() + "/notice/list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // TODO: String loginId = (String) request.getSession().getAttribute("loginId");
        // TODO: int auth = (int) request.getSession().getAttribute("auth");
        String loginId = "user_1001";
        int auth = 3;

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) pathInfo = "";

        switch (pathInfo) {

            case "/insert": {
                if (auth != 3) {
                    response.sendRedirect(request.getContextPath() + "/notice/list");
                    return;
                }

                NoticeDTO dto = new NoticeDTO();
                dto.setEmpId(loginId);

                try {
                    // 업로드 폴더 없으면 생성
                    File uploadDir = new File(UPLOAD_PATH);
                    if (!uploadDir.exists()) uploadDir.mkdirs();

                    DiskFileItemFactory factory = new DiskFileItemFactory();
                    factory.setRepository(uploadDir);
                    factory.setSizeThreshold(1024 * 1024);

                    ServletFileUpload upload = new ServletFileUpload(factory);
                    upload.setFileSizeMax(1024 * 1024 * 10); // 10MB

                    // ★ getParameter() 보다 먼저 parseRequest() 해야 함
                    List<FileItem> items = upload.parseRequest(request);

                    for (FileItem fileItem : items) {
                        if (fileItem.isFormField()) {
                            // 일반 텍스트 필드
                            String fieldName = fileItem.getFieldName();
                            String value     = fileItem.getString("UTF-8");

                            if ("title".equals(fieldName))   dto.setTitle(value);
                            if ("content".equals(fieldName)) dto.setContent(value);

                        } else {
                            // 첨부파일
                            if (fileItem.getSize() > 0) {
                                String originName = fileItem.getName(); // 원본 파일명
                                String saveName  = System.currentTimeMillis() + "_" + originName;

                                fileItem.write(new File(uploadDir + "\\" + saveName));

                                dto.setOriginName(originName);
                                dto.setSaveName(saveName);
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                noticeService.insertNotice(dto);
                response.sendRedirect(request.getContextPath() + "/notice/list?page=1");
                break;
            }

            case "/update": {
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