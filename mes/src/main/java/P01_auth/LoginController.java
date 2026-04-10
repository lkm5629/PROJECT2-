package P01_auth;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("/login doget 실행" );
		// TODO Auto-generated method stub
		
		// 주소 : http://localhost:8080/mes/login.jsp

		// 한글깨짐 방지
		request.setCharacterEncoding("UTF-8");
//		response.setContentType("text/html; charset=UTF-8");
		//함수 모음집 소환
		LoginService s = new LoginService();
		// 데이터 바구니 소환
		LoginDTO d = new LoginDTO();
		
		
		
		//전화번호. 숫자 21억 넘어서 long으로 저장.
		long phone = 0;
		
		
		//이 값이 있다면 로그인
		String id = request.getParameter("login_id");
		String pw = request.getParameter("login_pw");
		
		
		//로그인 로직
		if( (id != null && pw != null) && id.trim().length() > 0 && pw.trim().length() > 0) { 	
			System.out.println("/login doget.login 실행" );
			
			
			//값 저장
			d.setEmpid(id);
			d.setPassword(pw);			
			
			// 로그인 함수 소환후 함수 작동결과를 변수에 담기
			List<LoginDTO> list = s.login(d);
			
			//로그인 함수 작동 결과 값이 있다면
			if(list.size() == 1  ) {
				System.out.println("로그인 성공");
				
				//세션 소환
				HttpSession session = request.getSession();				
				
				session.setAttribute("dto", list.get(0));
			    session.setAttribute("login", "true");
				response.sendRedirect(request.getContextPath() + "/mypage.jsp");
				return;
				
				//없다면 로그인 실패 메세지와 함께 로그인 페이지로.
			} else {
				System.out.println("로그인 실패");
				request.setAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
				request.setAttribute("empid", id);
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}			
			
		}
		
		//이 값이 있다면 로그아웃으로
		String logout = request.getParameter("logout");
		
		//로그아웃 로직 
		//이 값이면 로그아웃로직 실행
		if ("logout".equals(logout)){
			
			//세션 소환
			HttpSession outSession = request.getSession(false);
			
			//세션이 있다면 삭제
			if(outSession != null) {
				outSession.invalidate();
			}
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;			
			
		}
		
		
		//이 값들이 있다면 회원가입
		String join_name = request.getParameter("join_name");
		String join_phone = request.getParameter("join_phone");
		String join_dept = request.getParameter("join_dept");
		String join_license = request.getParameter("join_license");
		String join_mgr = request.getParameter("join_mgr");
		String join_pw = request.getParameter("join_pw");
		String join_pw2 = request.getParameter("join_pw2");
		
		if(join_phone != null) {
			phone = Integer.parseInt(join_phone);
		}
		
		
		//회원가입 로직
		if( join_name != null && join_pw != null && join_pw2 != null && join_mgr != null) {
			System.out.println("/login doget.join 실행" );

				d.setEname(join_name);
				d.setPhone(phone);
				d.setDeptno(join_dept);
				d.setMgr(join_mgr);
				d.setLicense(join_license);
				d.setPassword(join_pw);
				d.setPassword2(join_pw2);

				List list = s.join(d);
				
				//여서 에러남. if( list.get(0) > 0)했는데 안되었음. 원인은 리스트에 다른 
				//형태데이터 넣어서 안됨. 해결책은 앞에 클래스 명을 붙여 형변환 후 사용하는 것.
				//그냥은 Object 처리 되어서 숫자 비교가 안되는 거였음.
				if (!list.isEmpty() && (int)list.get(0) > 0) {
					System.out.println("회원가입이 완료되었습니다!");
					
					//값 들려서 그? 다른 창 소환.
					request.setAttribute("list", (LoginDTO)list.get(1));
					request.getRequestDispatcher("/joinResult.jsp").forward(request, response);
					return;
					
					
				} else {
					System.out.println("회원가입에 실패했습니다. 값을 모두 입력해주세요.");
					
					//에러값과 함께 회원가입 페이지로 추방
					request.setAttribute("error", "회원가입에 실패했습니다. 다시.");
					request.getRequestDispatcher("/join.jsp").forward(request, response);
				}		
			
		}
		
		// 이 값들이 있다면 정보 수정으로
		String mp_empid = request.getParameter("mp_empid");
		String mp_name = request.getParameter("mp_name");
		String mp_phone = request.getParameter("mp_phone");
		String mp_pw = request.getParameter("mp_pw");
		String mp_pw2 = request.getParameter("mp_pw2");
		
		
		// 정보 수정 로직
		if( !("".equals(mp_name)) || !("".equals(mp_phone)) || !("".equals(mp_pw)) ) {
			System.out.println("/login doget.edit 실행" );

			
			//만약 null이 아니라면 공백 제거 후 덮어씌우기
			if (mp_empid != null) mp_empid = mp_empid.trim();
			if (mp_name != null) mp_name = mp_name.trim();
			if (mp_phone != null) mp_phone = mp_phone.trim();
			if (mp_pw != null) mp_pw = mp_pw.trim();
			if (mp_pw2 != null) mp_pw2 = mp_pw2.trim();
			
			//empid 바구니에 넣기.
			d.setEmpid(mp_empid);
			
			//값이 있고, 길이가 0이 아니라면 바구니에 넣기.
			//이름
			if( mp_name != null && mp_name.length() > 0) {
				d.setEname(mp_name);
			} 
			//연락처
			if( mp_phone != null && mp_phone.length() > 0) {
				phone = Integer.parseInt(mp_phone);
				d.setPhone(phone);
			} 
			//비밀번호			
			if( (mp_pw != null && mp_pw2 != null) && mp_pw.equals(mp_pw2) && mp_pw.length() > 0 ) {
				d.setPassword(mp_pw);
			} else if((mp_pw != null && mp_pw2 != null) && !mp_pw.equals(mp_pw2) && mp_pw.length() > 0 ) {
				//비밀번호가 일치하지 않으면 null 실행
				d.setPassword(null);
				
				System.out.println("비밀번호가 서로 일치하지 않습니다.");
				request.setAttribute("error", "비밀번호가 서로 일치하지 않습니다. 비밀번호 변경에 실패했습니다.");
			}
			
			
			
			if(s.edit(d) > 0) {
				System.out.println("정보수정이 완료 되었습니다.");
			} else {
				System.out.println("정보수정에 실패 했습니다.");				
			}
			request.getRequestDispatcher("/mypage.jsp").forward(request, response);
			
		}
		
		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}
