package P08_quality;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import P01_auth.DTO.UserWoDTO;
import P07_work.SearchDTO;
import P07_work.WoDTO;
import P08_quality.QcDAO;
import P08_quality.QcDTO;

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
	
	public QcDTO detail(QcDTO dto) {
		QcDAO dao = new QcDAO();
		return dao.detail(dto);
	}
	
	public List defContent(String qcId) {
		QcDAO dao = new QcDAO();
		return dao.defContent(qcId);
	}
	
	public List getWoList() {
		QcDAO dao = new QcDAO();
		return dao.getWoList();
	}
	
	public WoDTO setWo(WoDTO dto) {
		QcDAO dao = new QcDAO();
		return dao.setWo(dto);
	}
	
	public List<UserWoDTO> searchWorker(String keyword) {
		QcDAO dao = new QcDAO();
		return dao.searchWorker(keyword);
	}
	
	public int addQc(QcAddDTO dto) {
		QcDAO dao = new QcDAO();
		return dao.addQc(dto);
	}
	
	public QcDTO getQc(String qcId) {
		QcDAO dao = new QcDAO();
		return dao.getQc(qcId);
	}
	
	public int modifyOrder(String qcId, QcAddDTO addDTO) {
		QcDAO dao = new QcDAO();
		return dao.modifyOrder(qcId, addDTO);
	}
	
	public int deleteQc(String qcId) {
		QcDAO dao = new QcDAO();
		return dao.deleteQc(qcId);
	}

}
