package com.czy.replay;

import java.util.Comparator;

/**
 * Comparator implementation for TimedData
 * 
 * @author Joshua
 *
 * @param <T>
 */
public class TimedDataComparator<T> implements Comparator<TimedData<T>> {

	@Override
	public int compare(TimedData<T> o1, TimedData<T> o2) {
		if (o1.getEpochTimeMs() > o2.getEpochTimeMs()) {
			return 1;
		} else if (o1.getEpochTimeMs() < o2.getEpochTimeMs()) {
			return -1;
		} else {
			return 0;
		}
	}

}
