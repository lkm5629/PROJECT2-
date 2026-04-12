package P08_quality;

import java.util.HashMap;
import java.util.Map;

import P07_work.SearchDTO;
import P07_work.WoDAO;
import P07_work.WoDTO;

public class QcService {
	
	public Map getList(QcDTO dto) {
		Map map = new HashMap();
		QcDAO dao = new QcDAO();
		
		int size = dto.getSize();
		int page = dto.getPage();
		
		int start = 0, end = 0;
		
		end = size*page;
		start = end - (size-1);
		
		int cnt = dao.count();
		
		map.put("list", dao.getList(start, end));
		map.put("totalPage", (int)Math.ceil ((double)cnt/size));
		map.put("size", size);
		map.put("page", page);
		
		return map;
	}
	
	public QcCardDTO getCard() {
		QcDAO dao = new QcDAO();
		return dao.getCard();
	}
	
	public Map search(QcDTO dto, SearchDTO search) {
		Map map = new HashMap();
		QcDAO dao = new QcDAO();
		
		int size = dto.getSize();
		int page = dto.getPage();
		
		int start = 0, end = 0;
		
		end = size*page;
		start = end - (size-1);
		
		int cnt = dao.countSearch(search);
		
		map.put("list", dao.search(start, end, search));
		map.put("totalPage", (int)Math.ceil ((double)cnt/size));
		map.put("size", size);
		map.put("page", page);
		
		return map;
	}

}
