package P09_equip.DTO;

import java.sql.Date;
import java.sql.Timestamp;

public class EqLogDTO {
	
	String logId;
	String eqId;
	String eqName;
	String wId;
	String wName;
	
	Timestamp sTime;
	Timestamp eTime;
	
	String inspType;
	String inspContent;
	
	int page;
	int size;
	
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
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
	public Timestamp getsTime() {
		return sTime;
	}
	public void setsTime(Timestamp sTime) {
		this.sTime = sTime;
	}
	public Timestamp geteTime() {
		return eTime;
	}
	public void seteTime(Timestamp eTime) {
		this.eTime = eTime;
	}
	public String getInspType() {
		return inspType;
	}
	public void setInspType(String inspType) {
		this.inspType = inspType;
	}
	public String getInspContent() {
		return inspContent;
	}
	public void setInspContent(String inspContent) {
		this.inspContent = inspContent;
	}
	
	
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	
	
	@Override
	public String toString() {
		return "EqLogDTO [logId=" + logId + ", eqId=" + eqId + ", eqName=" + eqName + ", wId=" + wId + ", wName="
				+ wName + ", sTime=" + sTime + ", eTime=" + eTime + ", inspType=" + inspType + ", inspContent="
				+ inspContent + "]";
	}

}
