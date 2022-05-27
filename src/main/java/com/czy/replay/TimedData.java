package com.czy.replay;

/**
 * Interface for timed data
 * 
 * @author Joshua
 *
 * @param <T>
 */
public interface TimedData<T> {

	long getEpochTimeMs();

	T getValue();

}
