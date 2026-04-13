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

@WebServlet("/Item_masterPageController")
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

		Item_masterDTO item_masterDTO = new Item_masterDTO();
		item_masterDTO.setSize(size);
		item_masterDTO.setPage(page);

		itemService service = new itemService();
		Map<String, Object> map = service.itemSelect(item_masterDTO);

		request.setAttribute("list", map.get("list"));
		request.setAttribute("page", map.get("page"));
		request.setAttribute("size", map.get("size"));
		request.setAttribute("totalPage", map.get("totalPage"));
		request.setAttribute("totalCount", map.get("totalCount"));

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

			int start = 0;
			int end = 0;

			end = page * size;
			start = end - (size - 1);

			item_masterDTO.setStart(start);
			item_masterDTO.setEnd(end);
			item_masterDTO.setSize(size);
			item_masterDTO.setPage(page);

			Item_masterDAO item_masterDAO = new Item_masterDAO();

			List<Item_masterDTO> list = item_masterDAO.selectItemPageList(item_masterDTO);
			int totalCount = item_masterDAO.selectItemTotalCount();

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
}
