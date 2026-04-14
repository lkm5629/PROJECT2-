package P09_equip;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import P07_work.SearchDTO;
import P07_work.WoDAO;
import P07_work.WoDTO;
import P09_equip.DTO.EqDTO;
import P09_equip.DTO.EqLogDTO;
import P09_equip.DTO.EqSearchDTO;

public class EqService {
	EqDAO dao = new EqDAO();
	
	public Map getList(EqDTO dto) {
		Map map = new HashMap();
		
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
	
	public List getAllList() {
		return dao.getAllList();
	}

	public Map search(EqDTO dto, EqSearchDTO search) {
		Map map = new HashMap();
		
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
	
	public EqDTO detail(EqDTO dto) {
		return dao.setting(dto);
	}
	
	public List getLog(EqLogDTO dto) {
		return dao.getLog(dto);
	}

}
