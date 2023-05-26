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
	private int[] heatMap;

	public DataSummary(int totalDataPoints, long startTime, long endTime, long totalDuration, int[] heatMap) {
		super();
		this.totalDataPoints = totalDataPoints;
		this.startTime = startTime;
		this.endTime = endTime;
		this.totalDuration = totalDuration;
		this.heatMap = heatMap;
	}

	public int getTotalDataPoints() {
		return totalDataPoints;
	}


	public long getStartTime() {
		return startTime;
	}

	public long getEndTime() {
		return endTime;
	}


	public long getTotalTime() {
		return totalDuration;
	}

	public void setTotalTime(long totalDuration) {
		this.totalDuration = totalDuration;
	}
	
	public int[] getHeatMap() {
		return heatMap;
	}
	

}
