package P01_auth;

import java.io.Serializable;

public class DashDTO {
	
	String dtype_name;
	String nboardno;
	String ntitle;
	String sboardno;
	String stitle;

	

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

	@Override
	public String toString() {
		return "DashDTO [dtype_name=" + dtype_name + ", nboardno=" + nboardno + ", ntitle=" + ntitle + ", sboardno="
				+ sboardno + ", stitle=" + stitle + "]";
	}

	


}
