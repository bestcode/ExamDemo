package com.migu.schedule.info;

public class MyTaskInfo {

	private TaskInfo obj;
	private int consumption;

	public MyTaskInfo(TaskInfo obj) {
		this.obj = obj;
	}

	public MyTaskInfo(TaskInfo obj, int taskId, int nodeId) {
		this.obj = obj;
		obj.setNodeId(nodeId);
		obj.setTaskId(taskId);
	}

	public MyTaskInfo(TaskInfo obj, int taskId, int nodeId, int consumption) {
		this.obj = obj;
		obj.setNodeId(nodeId);
		obj.setTaskId(taskId);
		this.consumption = consumption;
	}

	public int getConsumption() {

		return consumption;
	}

	public void setConsumption(int consumption) {
		this.consumption = consumption;
	}

	public TaskInfo getObj() {
		return obj;
	}

	public void setObj(TaskInfo obj) {
		this.obj = obj;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MyTaskInfo) {
			return (this.obj.getTaskId() == ((MyTaskInfo) obj).getObj().getTaskId());
		}
		else
			return false;
	}

	@Override
	public int hashCode() {
		return this.obj.getNodeId();
	}

}
