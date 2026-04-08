package P05_stock;

import java.sql.Date;

public class StockDTO {
	
	// 외래키는 제외하고 구분함
	// 제고용 DTO
	
	
	private String stock_id;
	private Integer stock_no;
	private Integer safe_no; 
	
	// 입출고용 DTO
	
	private String io_id;
	private Date io_time;
	private String deleted;
	private int io_type;
	private String io_reason;
	
	// 거래처 아이디
	private String vender_id;
	private String vender_name;
	
	// 제품 아이디
	private String item_id;
	private String itemgroup_name;
	private String Item_name;
	private String unit;
	private Integer sepc;
	
	// lot 코드
	String lot_id;
	int lot_qty;
	Date expiry_date;
	String lotdeleted;
	
	// user_info
	String emp_id;
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getDept_no() {
		return dept_no;
	}
	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}
	public int getRetire() {
		return retire;
	}
	public void setRetire(int retire) {
		this.retire = retire;
	}
	String ename;
	String dept_no;
	int retire;
	
	
	
	
	public String getStock_id() {
		return stock_id;
	}
	public void setStock_id(String stock_id) {
		this.stock_id = stock_id;
	}
	public Integer getStock_no() {
		return stock_no;
	}
	public void setStock_no(Integer stock_no) {
		this.stock_no = stock_no;
	}
	public Integer getSafe_no() {
		return safe_no;
	}
	public void setSafe_no(Integer safe_no) {
		this.safe_no = safe_no;
	}
	public String getIo_id() {
		return io_id;
	}
	public void setIo_id(String io_id) {
		this.io_id = io_id;
	}
	public Date getI0_time() {
		return io_time;
	}
	public void setI0_time(Date i0_time) {
		this.io_time = i0_time;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getIo_reason() {
		return io_reason;
	}
	public void setIo_reason(String io_reason) {
		this.io_reason = io_reason;
	}
	public String getVender_id() {
		return vender_id;
	}
	public void setVender_id(String vender_id) {
		this.vender_id = vender_id;
	}
	public String getVender_name() {
		return vender_name;
	}
	public void setVender_name(String vender_name) {
		this.vender_name = vender_name;
	}
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	public String getItemgroup_name() {
		return itemgroup_name;
	}
	public void setItemgroup_name(String itemgroup_name) {
		this.itemgroup_name = itemgroup_name;
	}
	public String getItem_name() {
		return Item_name;
	}
	public void setItem_name(String item_name) {
		Item_name = item_name;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Integer getSepc() {
		return sepc;
	}
	public void setSepc(Integer sepc) {
		this.sepc = sepc;
	}
	public String getLot_id() {
		return lot_id;
	}
	public void setLot_id(String lot_id) {
		this.lot_id = lot_id;
	}
	public int getLot_qty() {
		return lot_qty;
	}
	public void setLot_qty(int lot_qty) {
		this.lot_qty = lot_qty;
	}
	public Date getExpiry_date() {
		return expiry_date;
	}
	public void setExpiry_date(Date expiry_date) {
		this.expiry_date = expiry_date;
	}
	public String getLotdeleted() {
		return lotdeleted;
	}
	public void setLotdeleted(String lotdeleted) {
		this.lotdeleted = lotdeleted;
	}
	
	public int getIo_type() {
		return io_type;
	}
	public void setIo_type(int io_type) {
		this.io_type = io_type;
	}
	@Override
	public String toString() {
		return "StockDTO [stock_id=" + stock_id + ", stock_no=" + stock_no + ", safe_no=" + safe_no + ", io_id=" + io_id
				+ ", io_time=" + io_time + ", deleted=" + deleted + ", io_type=" + io_type + ", io_reason=" + io_reason
				+ ", vender_id=" + vender_id + ", vender_name=" + vender_name + ", item_id=" + item_id
				+ ", itemgroup_name=" + itemgroup_name + ", Item_name=" + Item_name + ", unit=" + unit + ", sepc="
				+ sepc + ", lot_id=" + lot_id + ", lot_qty=" + lot_qty + ", expiry_date=" + expiry_date
				+ ", lotdeleted=" + lotdeleted + "]";
	}
	
	
	
}
