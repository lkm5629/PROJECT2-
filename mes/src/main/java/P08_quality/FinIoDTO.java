package P08_quality;

import java.sql.Date;

public class FinIoDTO {
	
	int qty;
	
	String itemId;
	String lotId;
	
	String empId;
	
	Date date;

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

	public String getLotId() {
		return lotId;
	}

	public void setLotId(String lotId) {
		this.lotId = lotId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "FinIoDTO [qty=" + qty + ", itemId=" + itemId + ", lotId=" + lotId + ", empId=" + empId + ", date="
				+ date + "]";
	}
	

}
