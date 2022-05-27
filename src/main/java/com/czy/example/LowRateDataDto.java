package com.czy.example;

import com.czy.replay.TimedData;

public class LowRateDataDto<T> implements TimedData<T> {

	private String type;
	private long timestamp;
	private T data;

	@Override
	public long getEpochTimeMs() {
		return this.timestamp;
	}

	@Override
	public T getValue() {
		return data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
