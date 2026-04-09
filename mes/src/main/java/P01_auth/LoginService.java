package P01_auth;

import java.util.ArrayList;
import java.util.List;

public class LoginService {
	
	public List<LoginDTO> login(LoginDTO d) {
		System.out.println("/login service.login() 실행 ");
		LoginDAO a = new LoginDAO();
		return a.login(d);				
	}
	
	public int edit(LoginDTO d) {
		System.out.println("/login service.edit() 실행 ");
		LoginDAO a = new LoginDAO();
		LoginDTO dto = a.editCheck(d);
		
		if( !dto.getEname().equals(d.getEname()) ) {
			
		} else {
			d.setEname(dto.getEname());
		}
		if( !dto.getPassword().equals(d.getPassword()) ) {
			
		} else {
			d.setPassword(dto.getPassword());
		}
		if( dto.getPhone() != d.getPhone() ) {
			
		} else {
			d.setPassword(dto.getPassword());
		}
		
		return a.edit(d);
		
		
	}
	
	
	public List join(LoginDTO d) {
		System.out.println("/login service.join() 실행 ");
		
		//바구니 소환
		List list = new ArrayList();
		
		//실무 함수뭉치 소환
		LoginDAO a = new LoginDAO();
		
		//회원가입
		list.add(a.join(d));
		
		//회원가입 후 생성되는 empno를 비롯한 다른 것들.
	    list.add(a.empno(d));
	     
	     return list;
	}

}
