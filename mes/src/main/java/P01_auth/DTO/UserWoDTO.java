package P01_auth.DTO;

import java.sql.Date;

public class UserWoDTO {
	
	String empId;
	String eName;
	Long phone;
	int auth;
	Date hiredate;
	
	
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String geteName() {
		return eName;
	}
	public void seteName(String eName) {
		this.eName = eName;
	}
	public Long getPhone() {
		return phone;
	}
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	public int getAuth() {
		return auth;
	}
	public void setAuth(int auth) {
		this.auth = auth;
	}
	public Date getHiredate() {
		return hiredate;
	}
	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}
	
	
	@Override
	public String toString() {
		return "UserWoDTO [empId=" + empId + ", eName=" + eName + ", phone=" + phone + ", auth=" + auth + ", hiredate="
				+ hiredate + "]";
	}

}
