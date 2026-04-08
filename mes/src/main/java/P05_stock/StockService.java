package P05_stock;

import java.util.ArrayList;
import java.util.List;

public class StockService {
	StockDAO stockDAO = new StockDAO();
	
	public List<StockDTO> select(StockDTO stockDTO){
		stockDAO.select(stockDTO);
		List<StockDTO> list = new ArrayList();
		return list;
		
	}
	
}
