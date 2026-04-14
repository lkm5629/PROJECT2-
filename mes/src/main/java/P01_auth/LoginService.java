package P01_auth;

import java.util.ArrayList;
import java.util.List;

public class LoginService {
	
	public List<LoginDTO> login(LoginDTO d) {
		System.out.println("/login service.login() 실행 ");
		
		//실무 함수 소환
		LoginDAO a = new LoginDAO();
		
		//로그인 함수 실행결과 리턴
		return a.login(d);				
	}
	
	public int edit(LoginDTO d) {
		System.out.println("/login service.edit() 실행 ");
		
		//실무 함수 소환
		LoginDAO a = new LoginDAO();
		
		//일단 수정요청자의 각 정보들을 조회해서 호출
		LoginDTO dto = a.editCheck(d);
		
		System.out.println(dto);
		System.out.println(d);
		
		
		//controller에서 	받아온 값이 null이거나 ""이라면 기존 값 덮어 씌우기	
		if(   d.getEname() == null ||
				( d.getEname() == null && d.getEname().trim().length() == 0 ) ) {
			d.setEname(dto.getEname());			
		} 
		
		if(d.getPassword() == null 
				|| (d.getPassword() != null && d.getPassword().trim().length() == 0) ) {
			d.setPassword(dto.getPassword());
		} 
		
		if( d.getPhone() == 0 ) {
			d.setPhone(dto.getPhone());			
		} 
		
		//정보수정 실행 결과 리턴.
		return a.edit(d);
		
		
	}
	
	public int changepw(LoginDTO d) {
		System.out.println("/login service.changepw() 실행 ");
		
		//실무 함수 소환
		LoginDAO a = new LoginDAO();
		
		
		
		//정보수정 실행 결과 리턴.
		return a.changepw(d);
		
		
	}
	
	
	public List join(LoginDTO d) {
		System.out.println("/login service.join() 실행 ");
		
		//바구니 소환
		List list = new ArrayList();
		
		//실무 함수 소환
		LoginDAO a = new LoginDAO();
		
		//회원가입
		list.add(a.join(d));
		
		//회원가입 후 생성되는 empno를 비롯한 다른 것들.
	    list.add(a.empno(d));
	     
	     return list;
	}
	
	
	public int readEmp() {
		System.out.println("/login service.readEmp() 실행 ");
		
		//실무 함수 소환
		LoginDAO a = new LoginDAO();
		
		//로그인 함수 실행결과 리턴
		return a.readEmp();				
	}
	
	public List<LoginDTO> paging(int start_no, int countPageNo ) {
		System.out.println("/login service.paging() 실행 ");
		
		//실무 함수 소환
		LoginDAO a = new LoginDAO();
		
		//로그인 함수 실행결과 리턴
		return a.paging(start_no, countPageNo);				
	}

}
