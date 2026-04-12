package P07_work;

import java.sql.Date;

public class WoAddDTO {
	
	String woId;
	String workDate;
	String planId;
	int woStatus = 10;
	int woQty;
	String worker;
	String content;
	
	public String getWoId() {
		return woId;
	}
	public void setWoId(String woId) {
		this.woId = woId;
	}
	public String getWorkDate() {
		return workDate;
	}
	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public int getWoStatus() {
		return woStatus;
	}
	public void setWoStatus(int woStatus) {
		this.woStatus = woStatus;
	}
	public int getWoQty() {
		return woQty;
	}
	public void setWoQty(int woQty) {
		this.woQty = woQty;
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
	
	
	@Override
	public String toString() {
		return "WoAddDTO [woId=" + woId + ", workDate=" + workDate + ", planId=" + planId + ", woStatus=" + woStatus
				+ ", woQty=" + woQty + ", worker=" + worker + ", content=" + content + "]";
	}

}
