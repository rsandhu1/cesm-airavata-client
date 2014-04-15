package org.cesm.airavata.message;

import java.util.List;

public class Message {
	
	private String experimentID;
	private String workflowName;
	private String hostName;
	private String queueName;
	private String serviceName;
	private String userDN;
	private List<String> inputs;
	private int processorCount;
	private int hostCount;
	private int walltime;
	
	
	public int getProcessorCount() {
		return processorCount;
	}
	public void setProcessorCount(int processorCount) {
		this.processorCount = processorCount;
	}
	public int getHostCount() {
		return hostCount;
	}
	public void setHostCount(int hostCount) {
		this.hostCount = hostCount;
	}
	public int getWalltime() {
		return walltime;
	}
	public void setWalltime(int walltime) {
		this.walltime = walltime;
	}
	public String getExperimentID() {
		return experimentID;
	}
	public void setExperimentID(String experimentID) {
		this.experimentID = experimentID;
	}
	
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getQueueName() {
		return queueName;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	public List<String> getInputs() {
		return inputs;
	}
	public void setInputs(List<String> inputs) {
		this.inputs = inputs;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getWorkflowName() {
		return workflowName;
	}
	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}
	public String getUserDN() {
		return userDN;
	}
	public void setUserDN(String userDN) {
		this.userDN = userDN;
	}
}
