package P01_auth;

import java.sql.Date;

public class LoginDTO {
	
	String empid;
	String ename;
	String phone;
	String password;
	String password2;
	String mgr;
	String license;
	String deptno;
	
	
	@Override
	public String toString() {
		return "LoginDTO [empid=" + empid + ", ename=" + ename + ", phone=" + phone + ", password=" + password
				+ ", password2=" + password2 + ", mgr=" + mgr + ", license=" + license + ", deptno=" + deptno
				+ ", hiredate=" + hiredate + "]";
	}
	
	
	public String getEmpid() {
		return empid;
	}
	public void setEmpid(String empid) {
		this.empid = empid;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword2() {
		return password2;
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	public String getMgr() {
		return mgr;
	}
	public void setMgr(String mgr) {
		this.mgr = mgr;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getDeptno() {
		return deptno;
	}
	public void setDeptno(String deptno) {
		this.deptno = deptno;
	}
	public Date getHiredate() {
		return hiredate;
	}
	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}
	Date hiredate;
	

}
