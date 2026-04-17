package P01_auth;

import java.sql.Date;

public class LoginDTO {
	
	String empid;
	String ename;
	long phone;
	String password;
	String password2;
	String mgr;
	String license;
	String deptno;
	String deptname;
	int auth;

	
	int wo_qty;
	int prev_qty;
	
	String woid;
	String planid;
	Date workdate;
	String content;
    Date hiredate;
    
    
    
	
	
	
	
	
	@Override
	public String toString() {
		return "LoginDTO [empid=" + empid + ", ename=" + ename + ", phone=" + phone + ", password=" + password
				+ ", password2=" + password2 + ", mgr=" + mgr + ", license=" + license + ", deptno=" + deptno
				+ ", deptname=" + deptname + ", auth=" + auth + ", wo_qty=" + wo_qty + ", prev_qty=" + prev_qty
				+ ", woid=" + woid + ", planid=" + planid + ", workdate=" + workdate + ", content=" + content
				+ ", hiredate=" + hiredate + "]";
	}



	public String getWoid() {
		return woid;
	}



	public void setWoid(String woid) {
		this.woid = woid;
	}



	public String getPlanid() {
		return planid;
	}



	public void setPlanid(String planid) {
		this.planid = planid;
	}



	public Date getWorkdate() {
		return workdate;
	}



	public void setWorkdate(Date workdate) {
		this.workdate = workdate;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}


	

	public int getWo_qty() {
		return wo_qty;
	}

	public void setWo_qty(int wo_qty) {
		this.wo_qty = wo_qty;
	}

	public int getPrev_qty() {
		return prev_qty;
	}

	public void setPrev_qty(int prev_qty) {
		this.prev_qty = prev_qty;
	}
	
	
	

	
	
	

	public int getAuth() {
		return auth;
	}
	
	
	public void setAuth(int auth) {
		this.auth = auth;
	}

	public String getDeptname() {
		return deptname;
	}


	public void setDeptname(String deptname) {
		this.deptname = deptname;
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
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
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
	

}
