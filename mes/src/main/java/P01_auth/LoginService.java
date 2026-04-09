package P01_auth;

import java.util.List;

public class LoginService {
	
	public List<LoginDTO> login(LoginDTO d) {
		LoginDAO a = new LoginDAO();
		return a.login(d);
		
		
	}

}
