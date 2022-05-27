package com.czy.example;

import com.czy.replay.TimedData;

public class LowRateDataDto<T> implements TimedData<T> {

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

}
