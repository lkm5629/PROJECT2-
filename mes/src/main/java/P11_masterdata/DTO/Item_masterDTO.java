package P11_masterdata.DTO;

public class Item_masterDTO {

	String item_id;
	String item_name;
	int g_id;
	String spec;
	String unit;
	int safe_qty;
	int pay;
	String itemgroup_name;

	int page;
	int size;
	int start;
	int end;

	String groupKeyword;
	String keyword;

	@Override
	public String toString() {
		return "Item_masterDTO [item_id=" + item_id + ", item_name=" + item_name + ", g_id=" + g_id + ", unit=" + unit
				+ ", safe_qty=" + safe_qty + ", pay=" + pay + ", itemgroup_name=" + itemgroup_name
				+ ", groupKeyword=" + groupKeyword + ", keyword=" + keyword + "]";
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public int getG_id() {
		return g_id;
	}

	public void setG_id(int g_id) {
		this.g_id = g_id;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getSafe_qty() {
		return safe_qty;
	}

	public void setSafe_qty(int safe_qty) {
		this.safe_qty = safe_qty;
	}

	public int getPay() {
		return pay;
	}

	public void setPay(int pay) {
		this.pay = pay;
	}

	public String getItemgroup_name() {
		return itemgroup_name;
	}

	public void setItemgroup_name(String itemgroup_name) {
		this.itemgroup_name = itemgroup_name;
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

	public String getGroupKeyword() {
		return groupKeyword;
	}

	public void setGroupKeyword(String groupKeyword) {
		this.groupKeyword = groupKeyword;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
