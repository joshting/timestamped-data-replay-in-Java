package com.czy.replay;

import java.util.EventListener;

/**
 * Event listener for replay
 * 
 * @author Joshua
 *
 * @param <T>
 */
public interface ReplayListener<T> extends EventListener {

	void onDataSummary(DataSummary dataSummary);

	void onDataItem(TimedData<T> data);

	void onElapsedTime(ElapsedTime relativeTime);

	void onStart();

	void onEnd();

	void onControlEvent(ReplayControlEvent control);

	void onDestroy();

}
