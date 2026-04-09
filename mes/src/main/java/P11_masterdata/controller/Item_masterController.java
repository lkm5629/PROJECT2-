package P11_masterdata.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import P11_masterdata.DAO.Item_masterDAO;
import P11_masterdata.DTO.Item_masterDTO;

@WebServlet("/itemMaster")
public class Item_masterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Item_masterController() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//한글 깨짐 방지
		request.setCharacterEncoding("utf-8");
	    response.setContentType("text/html; charset=utf-8;");
	    
	    //DAO 불러오기
	    Item_masterDAO dao = new Item_masterDAO();
	    List<Item_masterDTO> itemList = dao.selectItemList();
	    
	    //item_master.jsp로 forward
	    request.setAttribute("itemList", itemList);
	    request.getRequestDispatcher("/item_master.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
