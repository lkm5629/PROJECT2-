package P03_suggestion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import P03_suggestion.SuggestionDAO;
import P03_suggestion.SuggestionDTO;

public class SuggestionService {

    SuggestionDAO suggestionDAO = new SuggestionDAO();

    // ── 목록 조회 (페이지네이션) ──────────────────────────────────────────
    public Map<String, Object> getList(SuggestionDTO dto) {

        int size = dto.getSize();
        int page = dto.getPage();

        // rownum 범위 계산 (EmpService와 동일한 공식)
        int end   = size * page;
        int start = end - (size - 1);

        dto.setEnd(end);
        dto.setStart(start);

        List<SuggestionDTO> list = suggestionDAO.selectList(dto);
        int totalCount = suggestionDAO.selectTotal();

        Map<String, Object> map = new HashMap<>();
        map.put("list",       list);
        map.put("totalCount", totalCount);

        return map;
    }

    // ── 단건 조회 + 조회수 증가 ───────────────────────────────────────────
    public SuggestionDTO getDetail(int boardno) {
        suggestionDAO.updateViews(boardno);
        return suggestionDAO.selectOne(boardno);
    }

    // ── 등록 ─────────────────────────────────────────────────────────────
    public int insert(SuggestionDTO dto) {
        return suggestionDAO.insert(dto);
    }

    // ── 삭제 ─────────────────────────────────────────────────────────────
    public int delete(int boardno) {
        return suggestionDAO.delete(boardno);
    }
}