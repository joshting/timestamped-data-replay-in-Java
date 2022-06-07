package com.czy.replay;

public class TimedDataImp implements TimedData<String> {

	private long timestamp;

	private String value;

	public TimedDataImp(long timestamp, String value) {
		this.timestamp = timestamp;
		this.value = value;
	}

	@Override
	public long getEpochTimeMs() {
		return timestamp;
	}

	@Override
	public String getValue() {
		return value;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
