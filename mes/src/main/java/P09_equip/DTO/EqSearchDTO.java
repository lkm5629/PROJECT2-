package P09_equip.DTO;

public class EqSearchDTO {
	
	String status;
	String keyword;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	@Override
	public String toString() {
		return "EqSearchDTO [status=" + status + ", keyword=" + keyword + "]";
	}

}
