package P03_notice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoticeService {

    NoticeDAO noticeDAO = new NoticeDAO();

    // ── 목록 ─────────────────────────────────────
    public Map getListNotice(NoticeDTO noticeDTO) {

        int size = noticeDTO.getSize();
        int page = noticeDTO.getPage();

        int end   = size * page;
        int start = end - (size - 1);

        noticeDTO.setStart(start);
        noticeDTO.setEnd(end);

        List list      = noticeDAO.selectAllNotice(noticeDTO);
        int totalCount = noticeDAO.selectNoticeTotal(noticeDTO.getKeyword());  // ① 검색어 전달

        Map map = new HashMap();
        map.put("list",       list);
        map.put("totalCount", totalCount);

        return map;
    }

    // ── 단건 조회 ─────────────────────────────────
    public NoticeDTO selectOneNotice(String boardno) {
        return noticeDAO.selectOneNotice(boardno);
    }

    // ── 조회수 증가 ───────────────────────────────
    public void updateViews(String boardno) {
        noticeDAO.updateViews(boardno);
    }

    // ── 등록 ─────────────────────────────────────
    public int insertNotice(NoticeDTO noticeDTO) {
        return noticeDAO.insertNotice(noticeDTO);
    }

    // ── 수정 ─────────────────────────────────────
    public int updateNotice(NoticeDTO noticeDTO) {
        return noticeDAO.updateNotice(noticeDTO);
    }

    // ── 삭제 ─────────────────────────────────────
    public int deleteNotice(String boardno) {
        return noticeDAO.deleteNotice(boardno);
    }
}