package P07_work;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import P06_prod.DTO.PlanWoDTO;

public class WoService {
	
	public Map getList(WoDTO dto) {
		Map map = new HashMap();
		WoDAO dao = new WoDAO();
		
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
	
	public Map search(WoDTO dto, SearchDTO search) {
		Map map = new HashMap();
		WoDAO dao = new WoDAO();
		
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
	
	public WoDTO detail(WoDTO dto) {
		WoDAO dao = new WoDAO();
		return dao.detail(dto);
	}
	
	public List planList () {
		WoDAO dao = new WoDAO();
		return dao.planList();
	}
	
	public PlanWoDTO getPlan(PlanWoDTO dto) {
		WoDAO dao = new WoDAO();
		return dao.getPlan(dto);
	}
	
	public List searchWorker(String keyword) {
	    WoDAO dao = new WoDAO();
	    return dao.searchWorker(keyword);
	}
	
	public int addOrder(WoAddDTO dto) {
		WoDAO dao = new WoDAO();
		return dao.addOrder(dto);
	}

}
