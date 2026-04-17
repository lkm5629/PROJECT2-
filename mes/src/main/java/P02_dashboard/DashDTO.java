package P02_dashboard;

import java.io.Serializable; // 1. import 추가
import java.sql.Date;

public class DashDTO {
	
	String dtype_name;
	String nboardno;
	String ntitle;
	String sboardno;
	String stitle;
	
	String ioreason;
	Date iotime;
	String itemid;
	
	int wo_qty;
	Date workdate;
	int wostatusno;
	String qcsdate;
	String qcedate;
	int qcstatusno;
	
	int complete;
	
	
	
	
	
	

	

	public String getNboardno() {
		return nboardno;
	}

	public void setNboardno(String nboardno) {
		this.nboardno = nboardno;
	}

	public String getNtitle() {
		return ntitle;
	}

	public void setNtitle(String ntitle) {
		this.ntitle = ntitle;
	}

	public String getSboardno() {
		return sboardno;
	}

	public void setSboardno(String sboardno) {
		this.sboardno = sboardno;
	}

	public String getStitle() {
		return stitle;
	}

	public void setStitle(String stitle) {
		this.stitle = stitle;
	}

	public String getDtype_name() {
		return dtype_name;
	}

	public void setDtype_name(String dtype_name) {
		this.dtype_name = dtype_name;
	}

	public String getIoreason() {
		return ioreason;
	}

	public void setIoreason(String ioreason) {
		this.ioreason = ioreason;
	}

	public Date getIotime() {
		return iotime;
	}

	public void setIotime(Date iotime) {
		this.iotime = iotime;
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public int getWo_qty() {
		return wo_qty;
	}

	public void setWo_qty(int wo_qty) {
		this.wo_qty = wo_qty;
	}

	public Date getWorkdate() {
		return workdate;
	}

	public void setWorkdate(Date workdate) {
		this.workdate = workdate;
	}

	public int getWostatusno() {
		return wostatusno;
	}

	public void setWostatusno(int wostatusno) {
		this.wostatusno = wostatusno;
	}

	public String getQcsdate() {
		return qcsdate;
	}

	public void setQcsdate(String qcsdate) {
		this.qcsdate = qcsdate;
	}

	public String getQcedate() {
		return qcedate;
	}

	public void setQcedate(String qcedate) {
		this.qcedate = qcedate;
	}

	public int getQcstatusno() {
		return qcstatusno;
	}

	public void setQcstatusno(int qcstatusno) {
		this.qcstatusno = qcstatusno;
	}

	public int getComplete() {
		return complete;
	}

	public void setComplete(int complete) {
		this.complete = complete;
	}



	


}
