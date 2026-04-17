package P07_work;

import java.sql.Date;

public class LotDTO {
	
	String itemId;
	
	String lotId;
	double qty;
	String status;
	Date expire;
	
	
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
	public double getQty() {
		return qty;
	}
	public void setQty(double qty) {
		this.qty = qty;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getExpire() {
		return expire;
	}
	public void setExpire(Date expire) {
		this.expire = expire;
	}
	
	@Override
	public String toString() {
		return "LotDTO [itemId=" + itemId + ", lotId=" + lotId + ", qty=" + qty + ", status=" + status + ", expire="
				+ expire + "]";
	}
	
}
