package P07_work;

import java.sql.Date;

public class WoDTO {
	// wo
	String woId;
	Date workDate;
	int woStatus;
	int woQty;
	int prevQty;
	String worker;
	String wName;
	String content;
	String deleted;
	
	// plan
	String planId;
	Date sDate;
	Date eDate;
	int planStatus;
	int planQty;
	int planPrev;
	String director;
	String dName;
	
	// item
	String itemId;
	String itemName;
	String uni;
	String spec;
	String group;
	
	// page
	int size;
	int page;
	
	public String getWoId() {
		return woId;
	}
	public void setWoId(String woId) {
		this.woId = woId;
	}
	public Date getWorkDate() {
		return workDate;
	}
	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
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
	public int getPrevQty() {
		return prevQty;
	}
	public void setPrevQty(int prevQty) {
		this.prevQty = prevQty;
	}
	public String getWorker() {
		return worker;
	}
	public void setWorker(String worker) {
		this.worker = worker;
	}
	public String getwName() {
		return wName;
	}
	public void setwName(String wName) {
		this.wName = wName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public Date getsDate() {
		return sDate;
	}
	public void setsDate(Date sDate) {
		this.sDate = sDate;
	}
	public Date geteDate() {
		return eDate;
	}
	public void seteDate(Date eDate) {
		this.eDate = eDate;
	}
	public int getPlanStatus() {
		return planStatus;
	}
	public void setPlanStatus(int planStatus) {
		this.planStatus = planStatus;
	}
	public int getPlanQty() {
		return planQty;
	}
	public void setPlanQty(int planQty) {
		this.planQty = planQty;
	}
	public int getPlanPrev() {
		return planPrev;
	}
	public void setPlanPrev(int planPrev) {
		this.planPrev = planPrev;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getdName() {
		return dName;
	}
	public void setdName(String dName) {
		this.dName = dName;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getUni() {
		return uni;
	}
	public void setUni(String uni) {
		this.uni = uni;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}

	
	
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
	
	
	@Override
	public String toString() {
		return "WoDTO [woId=" + woId + ", workDate=" + workDate + ", woStatus=" + woStatus + ", woQty=" + woQty
				+ ", prevQty=" + prevQty + ", worker=" + worker + ", wName=" + wName + ", content=" + content
				+ ", deleted=" + deleted + ", planId=" + planId + ", sDate=" + sDate + ", eDate=" + eDate
				+ ", planStatus=" + planStatus + ", planQty=" + planQty + ", planPrev=" + planPrev + ", director="
				+ director + ", dName=" + dName + ", itemId=" + itemId + ", itemName=" + itemName + ", uni=" + uni
				+ ", spec=" + spec + ", group=" + group + "]";
	}
	
}
