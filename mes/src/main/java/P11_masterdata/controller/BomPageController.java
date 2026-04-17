package P11_masterdata.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import P11_masterdata.DAO.BomDAO;
import P11_masterdata.DTO.BomDTO;

@WebServlet("/bom")
public class BomPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BomPageController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		int size = 5;
		int page = 1;

		String sSize = request.getParameter("size");
		String sPage = request.getParameter("page");
		String keyword = request.getParameter("keyword");
		String itemGroup = request.getParameter("itemGroup");

		if (keyword == null) {
			keyword = "";
		}
		if (itemGroup == null) {
			itemGroup = "";
		}

		try {
			if (sSize != null && !sSize.trim().equals("")) {
				size = Integer.parseInt(sSize);
			}
		} catch (Exception e) {
			size = 5;
		}

		try {
			if (sPage != null && !sPage.trim().equals("")) {
				page = Integer.parseInt(sPage);
			}
		} catch (Exception e) {
			page = 1;
		}

		BomDTO bomDTO = new BomDTO();
		bomDTO.setSize(size);
		bomDTO.setPage(page);
		bomDTO.setKeyword(keyword);
		bomDTO.setItemGroup(itemGroup);

		Map<String, Object> map = bomSelect(bomDTO);

		BomDAO bomDAO = new BomDAO();
		String nextBomId = bomDAO.selectNextBomId();

		request.setAttribute("bomList", map.get("list"));
		request.setAttribute("page", map.get("page"));
		request.setAttribute("size", map.get("size"));
		request.setAttribute("totalPage", map.get("totalPage"));
		request.setAttribute("totalCount", map.get("totalCount"));
		request.setAttribute("nextBomId", nextBomId);
		request.setAttribute("keyword", keyword);
		request.setAttribute("itemGroup", itemGroup);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/bom.jsp");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	public Map<String, Object> bomSelect(BomDTO bomDTO) {

		int size = bomDTO.getSize();
		int page = bomDTO.getPage();

		if (size == 0) {
			size = 5;
		}
		if (page == 0) {
			page = 1;
		}

		int end = page * size;
		int start = end - (size - 1);

		bomDTO.setStart(start);
		bomDTO.setEnd(end);
		bomDTO.setSize(size);
		bomDTO.setPage(page);

		BomDAO bomDAO = new BomDAO();

		List<BomDTO> list = bomDAO.selectBomPage(bomDTO);
		int totalCount = bomDAO.selectBomTotalCount(bomDTO);

		int totalPage = totalCount / size;
		if (totalCount % size != 0) {
			totalPage++;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("totalCount", totalCount);
		map.put("totalPage", totalPage);
		map.put("page", page);
		map.put("size", size);

		return map;
	}
}
