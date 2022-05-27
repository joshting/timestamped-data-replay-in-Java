package com.czy.example;

import java.util.List;

public class Sensor {
	private String msgId;
	private String nodeId;
	private String name;
	private long timestamp;
	private List<Object> sensors;

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public List<Object> getSensors() {
		return sensors;
	}

	public void setSensors(List<Object> sensors) {
		this.sensors = sensors;
	}

}
