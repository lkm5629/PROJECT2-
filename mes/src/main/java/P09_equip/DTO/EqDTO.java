package P09_equip.DTO;

import java.sql.Date;

public class EqDTO {
	// eq
	String eqId;
	String eqName;
	
	// runTime
	int totalMin;
	int runMin;
	int stopMin;
	double runRate;
	String status;
	
	// page
	int size;
	int page;
	
	
	public String getEqId() {
		return eqId;
	}
	public void setEqId(String eqId) {
		this.eqId = eqId;
	}
	public String getEqName() {
		return eqName;
	}
	public void setEqName(String eqName) {
		this.eqName = eqName;
	}
	public int getTotalMin() {
		return totalMin;
	}
	public void setTotalMin(int totalMin) {
		this.totalMin = totalMin;
	}
	public int getRunMin() {
		return runMin;
	}
	public void setRunMin(int runMin) {
		this.runMin = runMin;
	}
	public int getStopMin() {
		return stopMin;
	}
	public void setStopMin(int stopMin) {
		this.stopMin = stopMin;
	}
	public double getRunRate() {
		return runRate;
	}
	public void setRunRate(double runRate) {
		this.runRate = runRate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
		return "EqDTO [eqId=" + eqId + ", eqName=" + eqName + ", totalMin=" + totalMin + ", runMin=" + runMin
				+ ", stopMin=" + stopMin + ", runRate=" + runRate + ", status=" + status + ", size=" + size + ", page="
				+ page + "]";
	}
	
}
