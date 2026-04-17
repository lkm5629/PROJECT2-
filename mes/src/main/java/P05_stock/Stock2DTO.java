package P05_stock;

public class Stock2DTO {

    // stock 테이블
    private String stock_id;
    private String item_id;
    private Integer stock_no;
    private String deleted;

    // item 테이블 (JOIN)
    private Integer safe_qty;
    private String item_name;
    private String g_id;
    private String spec;
    private String unit;

    // 필터
    private String filterGId;
    private String filterKeyword;
    private String filterStock;  // "normal" | "lack" | null

    // 페이징
    private int page;
    private int size;
    private int start;
    private int end;

    public String getStock_id() { return stock_id; }
    public void setStock_id(String stock_id) { this.stock_id = stock_id; }

    public String getItem_id() { return item_id; }
    public void setItem_id(String item_id) { this.item_id = item_id; }

    public Integer getStock_no() { return stock_no; }
    public void setStock_no(Integer stock_no) { this.stock_no = stock_no; }

    public String getDeleted() { return deleted; }
    public void setDeleted(String deleted) { this.deleted = deleted; }

    public Integer getSafe_qty() { return safe_qty; }
    public void setSafe_qty(Integer safe_qty) { this.safe_qty = safe_qty; }

    public String getItem_name() { return item_name; }
    public void setItem_name(String item_name) { this.item_name = item_name; }

    public String getG_id() { return g_id; }
    public void setG_id(String g_id) { this.g_id = g_id; }

    public String getSpec() { return spec; }
    public void setSpec(String spec) { this.spec = spec; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public String getFilterGId() { return filterGId; }
    public void setFilterGId(String filterGId) { this.filterGId = filterGId; }

    public String getFilterKeyword() { return filterKeyword; }
    public void setFilterKeyword(String filterKeyword) { this.filterKeyword = filterKeyword; }

    public String getFilterStock() { return filterStock; }
    public void setFilterStock(String filterStock) { this.filterStock = filterStock; }

    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }

    public int getStart() { return start; }
    public void setStart(int start) { this.start = start; }

    public int getEnd() { return end; }
    public void setEnd(int end) { this.end = end; }
}