package P08_quality;

public class QcDefDTO {
	
	String defId;
	int dType;
	String dtName;
	int defCnt;
	String solution;
	String dispose;
	
	
	public String getDefId() {
		return defId;
	}
	public void setDefId(String defId) {
		this.defId = defId;
	}
	public int getdType() {
		return dType;
	}
	public void setdType(int dType) {
		this.dType = dType;
	}
	
	public String getDtName() {
		return dtName;
	}
	public void setDtName(String dtName) {
		this.dtName = dtName;
	}
	public int getDefCnt() {
		return defCnt;
	}
	public void setDefCnt(int defCnt) {
		this.defCnt = defCnt;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	
	public String getDispose() {
		return dispose;
	}
	public void setDispose(String dispose) {
		this.dispose = dispose;
	}
	
	
	@Override
	public String toString() {
		return "QcDefDTO [defId=" + defId + ", dType=" + dType + ", dtName=" + dtName + ", defCnt=" + defCnt
				+ ", solution=" + solution + ", dispose=" + dispose + "]";
	}

}
