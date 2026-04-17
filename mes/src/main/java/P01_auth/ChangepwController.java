package P01_auth;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/changepw")
public class ChangepwController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("/changepw doget 실행");
		// TODO Auto-generated method stub

		// 주소 : http://localhost:8080/mes/login.jsp

		// 한글깨짐 방지
		request.setCharacterEncoding("UTF-8");
//		response.setContentType("text/html; charset=UTF-8");

		// 비밀번호 변경으로
		request.getRequestDispatcher("/WEB-INF/views/P01_auth/changepw.jsp").forward(request, response);
		return;

	}



	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		System.out.println("/changepw dopost 실행");

		// TODO Auto-generated method stub

		// 주소 : http://localhost:8080/mes/login.jsp

		// 한글깨짐 방지
		request.setCharacterEncoding("UTF-8");
//		response.setContentType("text/html; charset=UTF-8");
		// 함수 모음집 소환
		LoginService s = new LoginService();
		// 데이터 바구니 소환
		LoginDTO d = new LoginDTO();

		// 전화번호. 숫자 21억 넘어서 long으로 저장.
		long phone = 0;
		
		

		// 이 값들이 있다면 비밀번호 변경으로
		String change_empid = request.getParameter("change_empid");
		String change_phone = request.getParameter("change_phone");
		String change_pw = request.getParameter("change_pw");
		String change_pw2 = request.getParameter("change_pw2");

		// 비밀번호 변경 로직
		if (!(change_empid == null && "".equals(change_empid)) && !(change_phone == null && "".equals(change_phone))
				&& !(change_pw == null && "".equals(change_pw)) && !(change_pw2 == null && "".equals(change_pw2))) {
			System.out.println("/changepw doget.changepw 실행");
			

			// 만약 null이 아니라면 공백 제거 후 덮어씌우기
			if (change_empid != null)
				change_empid = change_empid.trim();
			if (change_phone != null)
				change_phone = change_phone.trim();
			if (change_pw != null)
				change_pw = change_pw.trim();
			if (change_pw2 != null)
				change_pw2 = change_pw2.trim();
			

			// empid 바구니에 넣기.
			d.setEmpid(change_empid);

			// 값이 있고, 길이가 0이 아니라면 바구니에 넣기.
			// 연락처
			if (change_phone != null && change_phone.length() > 0) {
				phone = Integer.parseInt(change_phone);
				d.setPhone(phone);
			}
			// 비밀번호
			if ((change_pw != null && change_pw2 != null) && change_pw.equals(change_pw2) && change_pw.length() > 0) {
				
				//비밀번호 SHA-256 암호화
				System.out.println("암호화 비밀번호 확인 : "+s.encrypt(change_pw));
				
				d.setPassword(s.encrypt(change_pw));
				
			} else if ((change_pw != null && change_pw2 != null) && !change_pw.equals(change_pw2)
					&& change_pw.length() > 0) {
				// 비밀번호가 일치하지 않으면 null 실행
				d.setPassword(null);

				System.out.println("비밀번호가 서로 일치하지 않습니다.");
				request.setAttribute("error", "비밀번호가 서로 일치하지 않습니다. 비밀번호 변경에 실패했습니다.");
			}

			if (s.changepw(d) > 0) {
				System.out.println("비밀번호 변경이 완료 되었습니다.");
				request.setAttribute("error", "비밀번호 변경이 완료되었습니다!");
			} else {
				System.out.println("비밀번호 변경에 실패 했습니다.");
				request.setAttribute("error", "비밀번호 변경에 실패했습니다. 다시 시도해주세요.");
			}
			request.getRequestDispatcher("/WEB-INF/views/P01_auth/login.jsp").forward(request, response);
			return;

		}

	}

}
