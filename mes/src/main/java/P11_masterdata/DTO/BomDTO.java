package P11_masterdata.DTO;

import java.math.BigDecimal;

public class BomDTO {
	int size; 
	int page;
	String bom_id;
	String parent_item_id;
	String bom_use;
	String item_name;
	String itemgroup_name;
	String keyword;
	String itemGroup;
	int g_id;
	int bom_detail_id;
	String child_item_id;
	String child_item_name;
	BigDecimal ea;
	String unit;
	int pay;
	
	int start;
	int end;
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
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
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getItemgroup_name() {
		return itemgroup_name;
	}
	public void setItemgroup_name(String itemgroup_name) {
		this.itemgroup_name = itemgroup_name;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getItemGroup() {
		return itemGroup;
	}
	public void setItemGroup(String itemGroup) {
		this.itemGroup = itemGroup;
	}
	public int getG_id() {
		return g_id;
	}
	public void setG_id(int g_id) {
		this.g_id = g_id;
	}
	public String getBom_id() {
		return bom_id;
	}
	public void setBom_id(String bom_id) {
		this.bom_id = bom_id;
	}
	public String getParent_item_id() {
		return parent_item_id;
	}
	public void setParent_item_id(String parent_item_id) {
		this.parent_item_id = parent_item_id;
	}
	public String getBom_use() {
		return bom_use;
	}
	public void setBom_use(String bom_use) {
		this.bom_use = bom_use;
	}
	public int getBom_detail_id() {
		return bom_detail_id;
	}
	public void setBom_detail_id(int bom_detail_id) {
		this.bom_detail_id = bom_detail_id;
	}
	public String getChild_item_id() {
		return child_item_id;
	}
	public void setChild_item_id(String child_item_id) {
		this.child_item_id = child_item_id;
	}
	public String getChild_item_name() {
		return child_item_name;
	}
	public void setChild_item_name(String child_item_name) {
		this.child_item_name = child_item_name;
	}
	public BigDecimal getEa() {
		return ea;
	}
	public void setEa(BigDecimal ea) {
		this.ea = ea;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getPay() {
		return pay;
	}
	public void setPay(int pay) {
		this.pay = pay;
	}
}