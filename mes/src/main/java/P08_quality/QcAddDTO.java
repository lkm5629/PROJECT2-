package P08_quality;

import java.sql.Date;

public class QcAddDTO {
	
	String woId;
    Date sDate;
    String director;
    String worker;
    String content;
    int status;
    
	public String getWoId() {
		return woId;
	}
	public void setWoId(String woId) {
		this.woId = woId;
	}
	public Date getsDate() {
		return sDate;
	}
	public void setsDate(Date sDate) {
		this.sDate = sDate;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getWorker() {
		return worker;
	}
	public void setWorker(String worker) {
		this.worker = worker;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	@Override
	public String toString() {
		return "QcAddDTO [woId=" + woId + ", sDate=" + sDate + ", director=" + director + ", worker=" + worker
				+ ", content=" + content + ", status=" + status + "]";
	}
    
}
