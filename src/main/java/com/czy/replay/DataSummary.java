package com.czy.replay;

/**
 * Class to hold the summary of the currently loaded time series data. All the
 * time values are in milliseconds
 * 
 * @author Joshua
 *
 */
public class DataSummary {

	private int totalDataPoints;
	private long startTime;
	private long endTime;
	private long totalDuration;

	public DataSummary(int totalDataPoints, long startTime, long endTime, long totalDuration) {
		super();
		this.totalDataPoints = totalDataPoints;
		this.startTime = startTime;
		this.endTime = endTime;
		this.totalDuration = totalDuration;
	}

	public int getTotalDataPoints() {
		return totalDataPoints;
	}

	public void setTotalDataPoints(int totalDataPoints) {
		this.totalDataPoints = totalDataPoints;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public long getTotalTime() {
		return totalDuration;
	}

	public void setTotalTime(long totalDuration) {
		this.totalDuration = totalDuration;
	}
}
