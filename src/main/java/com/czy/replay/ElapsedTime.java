package com.czy.replay;

/**
 * Class to hold the the elapsed time details
 * 
 * @author Joshua
 *
 */
public class ElapsedTime {
	private long total;
	private long elapsed;
	private long remaining;

	public ElapsedTime(long total, long elapsed, long remaining) {
		super();
		this.total = total;
		this.elapsed = elapsed;
		this.remaining = remaining;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getElapsed() {
		return elapsed;
	}

	public void setElapsed(long elapsed) {
		this.elapsed = elapsed;
	}

	public long getRemaining() {
		return remaining;
	}

	public void setRemaining(long remaining) {
		this.remaining = remaining;
	}
}
