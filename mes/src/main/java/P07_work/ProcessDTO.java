package P07_work;

public class ProcessDTO {
	
	String woId;
	String planId;
	String procId;
	String procName;
	String procInfo;
	String stepId;
	String stepName;
	int stepSeq;
	
	
	public String getWoId() {
		return woId;
	}
	public void setWoId(String woId) {
		this.woId = woId;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getProcId() {
		return procId;
	}
	public void setProcId(String procId) {
		this.procId = procId;
	}
	public String getProcName() {
		return procName;
	}
	public void setProcName(String procName) {
		this.procName = procName;
	}
	public String getProcInfo() {
		return procInfo;
	}
	public void setProcInfo(String procInfo) {
		this.procInfo = procInfo;
	}
	public String getStepId() {
		return stepId;
	}
	public void setStepId(String stepId) {
		this.stepId = stepId;
	}
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	
	public int getStepSeq() {
		return stepSeq;
	}
	public void setStepSeq(int stepSeq) {
		this.stepSeq = stepSeq;
	}
	
	
	@Override
	public String toString() {
		return "ProcessDTO [woId=" + woId + ", planId=" + planId + ", procId=" + procId + ", procName=" + procName
				+ ", procInfo=" + procInfo + ", stepId=" + stepId + ", stepName=" + stepName + ", stepSeq=" + stepSeq
				+ "]";
	}
	
}
