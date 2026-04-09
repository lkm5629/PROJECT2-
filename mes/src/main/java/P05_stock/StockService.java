package P05_stock;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockService {
    StockDAO stockDAO = new StockDAO();
    
    // 입출고 목록 조회 (페이징 처리 포함)
    public Map getListStock(StockDTO stockDTO) {
        
        // DTO에서 페이징 정보 추출
        int size = stockDTO.getSize(); // 페이지당 표시 건수
        int page = stockDTO.getPage(); // 현재 페이지 번호
        
        int start = 0,    end = 0;
        
        // 페이징 범위 계산
        // ex) size=10, page=2 이면 end=20, start=11
        end = size * page;
        start = end - (size - 1);
        
        // 계산된 범위를 DTO에 세팅 후 DAO로 전달
        stockDTO.setEnd(end);
        stockDTO.setStart(start);
        
        // 페이징 적용된 목록 조회
        List<StockDTO> list = stockDAO.select(stockDTO);
        
        // 전체 건수 조회 (페이징 UI 계산용)
        int totalCount = stockDAO.selectTotal();
        
        // 목록과 전체 건수를 map에 담아 Controller로 반환
        Map map = new HashMap();
        map.put("list", list);
        map.put("totalCount", totalCount);
        map.put("page", page);   
        map.put("size", size);
        
        return map;
    }
    
    public void insert(StockDTO dto) {
        stockDAO.insert(dto);
    }
    
    public List<StockDTO> getVendorList() {
        return stockDAO.selectVendorList();
    }

    public List<StockDTO> getItemList() {
        return stockDAO.selectItemList();
    }
}