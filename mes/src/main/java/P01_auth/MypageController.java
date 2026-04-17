package P01_auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/mypage")
public class MypageController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("/mypage doget 실행");
		// TODO Auto-generated method stub

		// 주소 : http://localhost:8080/mes/login.jsp

		// 한글깨짐 방지
		request.setCharacterEncoding("UTF-8");
//		response.setContentType("text/html; charset=UTF-8");

		// 마이페이지로
		request.getRequestDispatcher("/WEB-INF/views/P01_auth/mypage.jsp").forward(request, response);
		return;

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		System.out.println("/mypage dopost 실행");

		// 한글깨짐 방지
		request.setCharacterEncoding("UTF-8");
//				response.setContentType("text/html; charset=UTF-8");

		// 함수 모음집 소환
		LoginService s = new LoginService();
		// 데이터 바구니 소환
		LoginDTO d = new LoginDTO();

		// 전화번호. 숫자 21억 넘어서 long으로 저장.
		long phone = 0;

		// 이 값들이 있다면 정보 수정으로
		String mp_empid = request.getParameter("mp_empid");
		String mp_name = request.getParameter("mp_name");
		String mp_phone = request.getParameter("mp_phone");
		String mp_pw = request.getParameter("mp_pw");
		String mp_pw2 = request.getParameter("mp_pw2");

		// 정보 수정 로직
		if (!(mp_name == null && "".equals(mp_name)) || !(mp_phone == null && "".equals(mp_phone))
				|| !(mp_pw == null && "".equals(mp_pw))) {
			System.out.println("/login doget.edit 실행");

			// 만약 null이 아니라면 공백 제거 후 덮어씌우기
			if (mp_empid != null)
				mp_empid = mp_empid.trim();
			if (mp_name != null)
				mp_name = mp_name.trim();
			if (mp_phone != null)
				mp_phone = mp_phone.trim();
			if (mp_pw != null)
				mp_pw = mp_pw.trim();
			if (mp_pw2 != null)
				mp_pw2 = mp_pw2.trim();

			// empid 바구니에 넣기.
			d.setEmpid(mp_empid);

			// 값이 있고, 길이가 0이 아니라면 바구니에 넣기.
			// 이름
			if (mp_name != null && mp_name.length() > 0) {
				d.setEname(mp_name);
			}
			// 연락처
			if (mp_phone != null && mp_phone.length() > 0) {
				phone = Integer.parseInt(mp_phone);
				d.setPhone(phone);
			}
			// 비밀번호
			if ((mp_pw != null && mp_pw2 != null) && mp_pw.equals(mp_pw2) && mp_pw.length() > 0) {
				
				//비밀번호 SHA-256 암호화
				System.out.println("암호화 비밀번호 확인 : "+s.encrypt(mp_pw));
				
				
				d.setPassword(s.encrypt(mp_pw));
				
				
			} else if ((mp_pw != null && mp_pw2 != null) && !mp_pw.equals(mp_pw2) && mp_pw.length() > 0) {
				// 비밀번호가 일치하지 않으면 null 실행
				d.setPassword(null);

				System.out.println("비밀번호가 서로 일치하지 않습니다.");
				request.setAttribute("error", "비밀번호가 서로 일치하지 않습니다. 비밀번호 변경에 실패했습니다.");
			}

			if (s.edit(d) > 0) {
				System.out.println("정보수정이 완료 되었습니다.");
			} else {
				System.out.println("정보수정에 실패 했습니다.");
			}
			request.getRequestDispatcher("/mypage").forward(request, response);
			return;

		}

	}

}
