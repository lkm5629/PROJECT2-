package P08_quality;

public class QcDisposeDTO {
	
	String qcId;
	int dispose;
	int rework;
	
	
	public String getQcId() {
		return qcId;
	}
	public void setQcId(String qcId) {
		this.qcId = qcId;
	}
	public int getDispose() {
		return dispose;
	}
	public void setDispose(int dispose) {
		this.dispose = dispose;
	}
	public int getRework() {
		return rework;
	}
	public void setRework(int rework) {
		this.rework = rework;
	}
	
	@Override
	public String toString() {
		return "QcDisposeDTO [qcId=" + qcId + ", dispose=" + dispose + ", rework=" + rework + "]";
	}

}
