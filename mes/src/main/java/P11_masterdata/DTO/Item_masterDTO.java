package P11_masterdata.DTO;

public class Item_masterDTO {
//주석
	String item_id;
	String item_name;
	int g_id;
	int spec;
	String unit;
	String itemgroup_name;
	
	int page;
	int size;
	int start;
	int end;
	
	@Override
	public String toString() {
		return "Item_masterDTO [item_id=" + item_id + ", item_name=" + item_name + ", g_id=" + g_id + ", unit=" + unit
				+ ", itemgroup_name=" + itemgroup_name + "]";
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


	public String getUnit() {
		return unit;
	}


	public void setUnit(String unit) {
		this.unit = unit;
	}


	public String getItemgroup_name() {
		return itemgroup_name;
	}


	public void setItemgroup_name(String itemgroup_name) {
		this.itemgroup_name = itemgroup_name;
	}


	public void setSpec(int spec) {
		this.spec = spec;
		
	}
	public int getSpec() {
	    return spec;
	}


	public void setSize(int size) {
	    this.size = size;
	}

	public void setPage(int page) {
	    this.page = page;
	}

	public void setStart(int start) {
	    this.start = start;
	}

	public void setEnd(int end) {
	    this.end = end;
	}

	public int getPage() {
	    return page;
	}

	public int getSize() {
	    return size;
	}

	public int getStart() {
	    return start;
	}

	public int getEnd() {
	    return end;
	}


}

	