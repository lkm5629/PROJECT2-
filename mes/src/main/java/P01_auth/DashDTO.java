package P01_auth;

public class DashDTO {
	
	String dtype_name;
	String boardno;
	String title;

	public String getBoardno() {
		return boardno;
	}

	public void setBoardno(String boardno) {
		this.boardno = boardno;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDtype_name() {
		return dtype_name;
	}

	public void setDtype_name(String dtype_name) {
		this.dtype_name = dtype_name;
	}

	@Override
	public String toString() {
		return "DashDTO [dtype_name=" + dtype_name + ", boardno=" + boardno + ", title=" + title + "]";
	}


}
