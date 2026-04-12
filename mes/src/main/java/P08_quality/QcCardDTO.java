package P08_quality;

public class QcCardDTO {
	
	int total;
	int pass;
	int defect;
	Double avgDefect;
	
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPass() {
		return pass;
	}
	public void setPass(int pass) {
		this.pass = pass;
	}
	public int getDefect() {
		return defect;
	}
	public void setDefect(int defect) {
		this.defect = defect;
	}
	public Double getAvgDefect() {
		return avgDefect;
	}
	public void setAvgDefect(Double avgDefect) {
		this.avgDefect = avgDefect;
	}
	
	
	@Override
	public String toString() {
		return "QcCardDTO [total=" + total + ", pass=" + pass + ", defect=" + defect + ", avgDefect=" + avgDefect
				+ ", getTotal()=" + getTotal() + ", getPass()=" + getPass() + ", getDefect()=" + getDefect()
				+ ", getAvgDefect()=" + getAvgDefect() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
}
