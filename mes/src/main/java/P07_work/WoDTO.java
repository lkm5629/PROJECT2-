package P07_work;

import java.sql.Date;

public class WoDTO {
	
	private String woId;
	private Date workDate;
	private String planId;
	private int wostatus;
	private int woQty;
	private String deleted;
	private String worker;
	private int planStatus;
	private int planQty;
	private int prevQty;
	private String itemId;
	private String director;
	private String itemName;
	private String unit;
	private int spec;
	private String group;
	private String wName;
	private String dName;
	
	private int size = 10;
	private int page = 1;
	
	
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
	
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	
	public int getWostatus() {
		return wostatus;
	}
	public void setWostatus(int wostatus) {
		this.wostatus = wostatus;
	}
	
	public int getWoQty() {
		return woQty;
	}
	public void setWoQty(int woQty) {
		this.woQty = woQty;
	}
	
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	
	public String getWorker() {
		return worker;
	}
	public void setWorker(String worker) {
		this.worker = worker;
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
	public int getPrevQty() {
		return prevQty;
	}
	public void setPrevQty(int prevQty) {
		this.prevQty = prevQty;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getSpec() {
		return spec;
	}
	public void setSpec(int spec) {
		this.spec = spec;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getwName() {
		return wName;
	}
	public void setwName(String wName) {
		this.wName = wName;
	}
	public String getdName() {
		return dName;
	}
	public void setdName(String dName) {
		this.dName = dName;
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
		return "WoDTO [woId=" + woId + ", workDate=" + workDate + ", planId=" + planId + ", wostatus=" + wostatus
				+ ", woQty=" + woQty + ", deleted=" + deleted + ", worker=" + worker + ", planStatus=" + planStatus
				+ ", planQty=" + planQty + ", prevQty=" + prevQty + ", itemId=" + itemId + ", director=" + director
				+ ", itemName=" + itemName + ", unit=" + unit + ", spec=" + spec + ", group=" + group + ", wName="
				+ wName + ", dName=" + dName + "]";
	}
	

}
