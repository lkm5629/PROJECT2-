package P07_work;

import java.sql.Date;

public class SearchDTO {
	
	int status;
	String sDate;
	String eDate;
	String keyword;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getsDate() {
		return sDate;
	}
	public void setsDate(String sDate) {
		this.sDate = sDate;
	}
	public String geteDate() {
		return eDate;
	}
	public void seteDate(String eDate) {
		this.eDate = eDate;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	
	@Override
	public String toString() {
		return "SearchDTO [status=" + status + ", sDate=" + sDate + ", eDate=" + eDate + ", keyword=" + keyword + "]";
	}
	
}
