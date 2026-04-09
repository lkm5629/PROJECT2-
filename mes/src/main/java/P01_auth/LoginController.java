package P01_auth;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		// 한글깨짐 방지
		request.setCharacterEncoding("UTF-8");
//		response.setContentType("text/html; charset=UTF-8");
		
		
		String id = request.getParameter("login_id");
		String pw = request.getParameter("login_pw");
		if(id != null && pw != null) { 			
			
			LoginDTO d = new LoginDTO();
			d.setEmpid(id);
			d.setPassword(pw);			
			
			
			LoginService s = new LoginService();
			List<LoginDTO> list = s.login(d);
			
			if(list.size() == 1  ) {
				System.out.println("로그인 성공");
				
				request.setAttribute("list", list);
				request.getRequestDispatcher("/mypage.jsp").forward(request, response);
				return;
				
			} else {
				System.out.println("로그인 실패");
			}			
			
		}
		
		
		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}
