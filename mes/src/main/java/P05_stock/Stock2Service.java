package P05_stock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stock2Service {

    Stock2DAO dao = new Stock2DAO();

    public Map getStockList(Stock2DTO dto) {
        int size  = dto.getSize();
        int page  = dto.getPage();
        int end   = size * page;
        int start = end - (size - 1);
        dto.setEnd(end);
        dto.setStart(start);

        List<Stock2DTO> list = dao.select(dto);
        int totalCount = dao.selectTotal(dto);

        Map map = new HashMap();
        map.put("list",       list);
        map.put("totalCount", totalCount);
        map.put("page",       page);
        map.put("size",       size);
        return map;
    }

    public List<Stock2DTO> getGroupList() {
        return dao.selectGroupList();
    }

    public int getTotalCount() {
        return dao.selectTotalCount();
    }

    public int getNormalCount() {
        return dao.selectNormalCount();
    }

    public int getLackCount() {
        return dao.selectLackCount();
    }

    // ★ 안전재고 수정
    public void updateSafeNo(String stockId, int safeNo) {
        dao.updateSafeNo(stockId, safeNo);
    }
}