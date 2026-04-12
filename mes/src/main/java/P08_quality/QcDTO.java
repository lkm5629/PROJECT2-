package P08_quality;

import java.sql.Date;

public class QcDTO {
	
	// wo
	String woId;
	int qty;
	String itemId;
	String iName;
	
	// qc
	String qcId;
	Date sDate;
	Date eDate;
	int qcStatus;
	String content;
	
	// user
	String dId;
	String dName;
	String wId;
	String wName;
	
	// defect
	int defSum;
	
	// page
	int size;
	int page;
	
	
	public String getWoId() {
		return woId;
	}
	public void setWoId(String woId) {
		this.woId = woId;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getiName() {
		return iName;
	}
	public void setiName(String iName) {
		this.iName = iName;
	}
	public String getQcId() {
		return qcId;
	}
	public void setQcId(String qcId) {
		this.qcId = qcId;
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
	public int getQcStatus() {
		return qcStatus;
	}
	public void setQcStatus(int qcStatus) {
		this.qcStatus = qcStatus;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getdId() {
		return dId;
	}
	public void setdId(String dId) {
		this.dId = dId;
	}
	public String getdName() {
		return dName;
	}
	public void setdName(String dName) {
		this.dName = dName;
	}
	public String getwId() {
		return wId;
	}
	public void setwId(String wId) {
		this.wId = wId;
	}
	public String getwName() {
		return wName;
	}
	public void setwName(String wName) {
		this.wName = wName;
	}
	public int getDefSum() {
		return defSum;
	}
	public void setDefSum(int defSum) {
		this.defSum = defSum;
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
		return "QcDTO [woId=" + woId + ", qty=" + qty + ", itemId=" + itemId + ", iName=" + iName + ", qcId=" + qcId
				+ ", sDate=" + sDate + ", eDate=" + eDate + ", qcStatus=" + qcStatus + ", content=" + content + ", dId="
				+ dId + ", dName=" + dName + ", wId=" + wId + ", wName=" + wName + ", defSum=" + defSum + ", size="
				+ size + ", page=" + page + "]";
	}

}
