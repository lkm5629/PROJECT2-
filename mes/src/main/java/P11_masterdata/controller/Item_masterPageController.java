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

import P11_masterdata.DAO.Item_masterDAO;
import P11_masterdata.DTO.Item_masterDTO;

@WebServlet("/itemmaster")
public class Item_masterPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Item_masterPageController() {
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
		String itemGroup = request.getParameter("itemGroup");
		String keyword = request.getParameter("keyword");

		if (sSize != null && !sSize.trim().equals("")) {
			try {
				size = Integer.parseInt(sSize);
			} catch (Exception e) {
				size = 5;
			}
		}

		if (sPage != null && !sPage.trim().equals("")) {
			try {
				page = Integer.parseInt(sPage);
			} catch (Exception e) {
				page = 1;
			}
		}

		if (size < 1) {
			size = 5;
		}

		if (page < 1) {
			page = 1;
		}

		Item_masterDTO item_masterDTO = new Item_masterDTO();
		item_masterDTO.setSize(size);
		item_masterDTO.setPage(page);
		item_masterDTO.setGroupKeyword(itemGroup);
		item_masterDTO.setKeyword(keyword);

		itemService service = new itemService();
		Map<String, Object> map = service.itemSelect(item_masterDTO);

		request.setAttribute("list", map.get("list"));
		request.setAttribute("allItemList", map.get("allItemList"));

		request.setAttribute("page", map.get("page"));
		request.setAttribute("size", map.get("size"));
		request.setAttribute("totalPage", map.get("totalPage"));

		request.setAttribute("totalCount", map.get("totalCount"));
		request.setAttribute("filteredCount", map.get("filteredCount"));

		request.setAttribute("finCount", map.get("finCount"));
		request.setAttribute("semiCount", map.get("semiCount"));
		request.setAttribute("rawCount", map.get("rawCount"));

		request.setAttribute("itemGroup", itemGroup);
		request.setAttribute("keyword", keyword);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/item_master.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public class itemService {

		public Map<String, Object> itemSelect(Item_masterDTO item_masterDTO) {

			int size = item_masterDTO.getSize();
			int page = item_masterDTO.getPage();

			if (size == 0) {
				size = 5;
			}
			if (page == 0) {
				page = 1;
			}

			int end = page * size;
			int start = end - (size - 1);

			item_masterDTO.setStart(start);
			item_masterDTO.setEnd(end);
			item_masterDTO.setSize(size);
			item_masterDTO.setPage(page);

			Item_masterDAO item_masterDAO = new Item_masterDAO();

			int totalCount = item_masterDAO.selectItemTotalCountAll();
			int filteredCount = item_masterDAO.selectItemTotalCount(item_masterDTO);

			int finCount = item_masterDAO.selectItemGroupCount(30);
			int semiCount = item_masterDAO.selectItemGroupCount(20);
			int rawCount = item_masterDAO.selectItemGroupCount(10);

			int totalPage = filteredCount / size;
			if (filteredCount % size != 0) {
				totalPage++;
			}
			if (totalPage == 0) {
				totalPage = 1;
			}

			if (page > totalPage) {
				page = totalPage;
				end = page * size;
				start = end - (size - 1);

				item_masterDTO.setPage(page);
				item_masterDTO.setStart(start);
				item_masterDTO.setEnd(end);
			}

			List<Item_masterDTO> list = item_masterDAO.selectItemPageList(item_masterDTO);
			List<Item_masterDTO> allItemList = item_masterDAO.selectItemList();

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("allItemList", allItemList);

			map.put("totalCount", totalCount);
			map.put("filteredCount", filteredCount);

			map.put("totalPage", totalPage);
			map.put("page", page);
			map.put("size", size);

			map.put("finCount", finCount);
			map.put("semiCount", semiCount);
			map.put("rawCount", rawCount);

			return map;
		}
	}
}
