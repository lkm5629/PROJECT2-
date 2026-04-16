package P07_work;

public class WoBOMDTO {
	
	String woId;
	String bomId;
	String pName;
	String pId;
	String cName;
	String cId;
	String spec;
	double ea;
	String unit;
	
	
	public String getWoId() {
		return woId;
	}
	public void setWoId(String woId) {
		this.woId = woId;
	}
	public String getBomId() {
		return bomId;
	}
	public void setBomId(String bomId) {
		this.bomId = bomId;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getcName() {
		return cName;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	public String getcId() {
		return cId;
	}
	public void setcId(String cId) {
		this.cId = cId;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public double getEa() {
		return ea;
	}
	public void setEa(double ea) {
		this.ea = ea;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	
	@Override
	public String toString() {
		return "WoBOMDTO [woId=" + woId + ", bomId=" + bomId + ", pName=" + pName + ", pId=" + pId + ", cName=" + cName
				+ ", cId=" + cId + ", spec=" + spec + ", ea=" + ea + ", unit=" + unit + "]";
	}
	
}
