package com.czy.replay;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Replay<T> implements Runnable {

	Logger logger = Logger.getLogger(Replay.class.getName());

	private long tick;
	private long ticker = 0;
	private long lastElapsedTimeEmit = 0;
	private long startTime = 0;
	private long endTime = 0;
	private long totalTime = 0;
	private List<TimedData<T>> data;
	private TimedDataComparator<T> comparator = new TimedDataComparator<T>();
	private int dataSize;
	private int indexPointer = 0;
	private boolean play = false;
	private boolean reverse = false;
	private boolean repeat = false;
	private float speedMultiplier = 1.0f;
	private ReplayListener<T> replayEvent;
	private boolean destroy = false;

	/**
	 * Setup a Replay of time series data
	 * 
	 * @param tick        - interval between each sampling for data in milliseconds
	 *                    (between 10 to 10000) e.g. 100ms means 10 samples per
	 *                    second 25 samples per second means 1000 / 25 = 40ms
	 * @param data        - the data which is a List of TimedData. It is assumed
	 *                    that it is sorted in ascending time order even though it
	 *                    will be sorted internally
	 * @param replayEvent - callback of the event which will have intervals that
	 *                    does not exceed the tick
	 * @throws TickOutOfRangeException
	 */
	public Replay(long tick, List<TimedData<T>> data, ReplayListener<T> replayEvent) {
		if (tick < 10 || tick > 10000) {
			throw new IllegalArgumentException(
					String.format("Argument tick is set to %s. Allowable values are between 10 to 10000", tick));
		}
		this.tick = tick;
		this.data = data;
		this.data.sort(comparator);
		this.dataSize = this.data.size();
		this.startTime = this.data.get(0).getEpochTimeMs();
		this.endTime = this.data.get(this.dataSize - 1).getEpochTimeMs();
		this.totalTime = this.endTime - this.startTime;
		logger.log(Level.INFO,
				String.format("Setup replay -- data points: %s, total time: %s ms", this.dataSize, this.totalTime));
		if (dataSize == Integer.MAX_VALUE) {
			logger.log(Level.WARNING, "The data size may have exceed the limit of java.util.List");
		}
		this.replayEvent = replayEvent;
		this.replayEvent.onDataSummary(new DataSummary(this.dataSize, this.startTime, this.endTime, this.totalTime));
	}

	@Override
	public void run() {
		indexPointer = 0;
		ticker = data.get(indexPointer).getEpochTimeMs();
		lastElapsedTimeEmit = ticker;
		while (!destroy) {
			while (indexPointer >= 0 && indexPointer < dataSize && play && !destroy) {
				if (play) {
					this.detectStartAndEnd();
				}
				try {
					if (reverse) {
						while (indexPointer >= 0 && data.get(indexPointer).getEpochTimeMs() >= ticker) {
							this.replayEvent.onDataItem(data.get(indexPointer));
							indexPointer--;
						}
						ticker -= this.tick;
					} else {
						while (indexPointer < dataSize && data.get(indexPointer).getEpochTimeMs() <= ticker) {
							this.replayEvent.onDataItem(data.get(indexPointer));
							indexPointer++;
						}
						ticker += this.tick;
					}
					// only emit elapsed time every ~ 1 second if tick is less than 1 second
					if (Math.abs(ticker - lastElapsedTimeEmit) > 990) {
						this.replayEvent.onElapsedTime(new ElapsedTime(this.totalTime, this.ticker - this.startTime,
								this.endTime - this.ticker));
						lastElapsedTimeEmit = ticker;
					}
					Thread.sleep(Math.round(tick / speedMultiplier));
				} catch (InterruptedException e) {
					logger.log(Level.WARNING, "Thread interrupted - " + e.getMessage());
				}
			}
		}
	}

	/**
	 * Stop the playback and reset
	 */
	public void stop() {
		this.play = false;
		this.speedMultiplier = 1;
		this.reverse = false;
		this.indexPointer = 0;
		ticker = data.get(0).getEpochTimeMs();
		this.replayEvent.onControlEvent(new ReplayControlEvent(ReplayControlEnum.STOP, ""));
	}

	/**
	 * Pause the playback
	 */
	public void pause() {
		this.play = false;
		this.replayEvent.onControlEvent(new ReplayControlEvent(ReplayControlEnum.PAUSE, ""));
	}

	/**
	 * Play - reset to forward at speed 1x if not already happening
	 */
	public void play() {
		// emit play event if it is not normal play forward at speed 1x
		if (this.reverse || this.speedMultiplier != 1 || !this.play) {
			this.replayEvent.onControlEvent(new ReplayControlEvent(ReplayControlEnum.PLAY, ""));
		}
		this.reverse = false;
		this.speedMultiplier = 1;
		if (!this.play) {
			this.play = true;
		}
	}

	/**
	 * Forwad the playback at given speed
	 * 
	 * @param speed
	 */
	public void forward(ReplaySpeedEnum speed) {
		this.reverse = false;
		this.speedMultiplier = speed.getSpeedMultiplier();
		if (!this.play) {
			this.play = true;
		}
		this.replayEvent.onControlEvent(
				new ReplayControlEvent(ReplayControlEnum.FORWARD, String.valueOf(this.speedMultiplier)));
	}

	/**
	 * Rewind the playback at given speed
	 * 
	 * @param speed
	 */
	public void rewind(ReplaySpeedEnum speed) {
		this.reverse = true;
		this.speedMultiplier = speed.getSpeedMultiplier();
		if (!this.play) {
			this.play = true;
		}
		this.replayEvent
				.onControlEvent(new ReplayControlEvent(ReplayControlEnum.REWIND, String.valueOf(this.speedMultiplier)));
	}

	/**
	 * Repeat
	 */
	public void toggleRepeat() {
		this.repeat = !this.repeat;
		this.replayEvent.onControlEvent(new ReplayControlEvent(ReplayControlEnum.REPEAT, String.valueOf(this.repeat)));
	}

	/**
	 * Seek the data series by time. If successful, goes to closest data point.
	 * 
	 * @param seekTime
	 */
	public void seek(long seekTime) {
		if (seekTime >= 0 || seekTime <= this.totalTime) {
			this.replayEvent.onControlEvent(new ReplayControlEvent(ReplayControlEnum.SEEK, String.valueOf(seekTime)));
			this.indexPointer = binarySearchClosest(this.data, seekTime);
			ticker = data.get(this.indexPointer).getEpochTimeMs();
		}
	}

	public void destroy() {
		this.play = false;
		this.destroy = true;
		this.replayEvent.onDestroy();
	}

	private void detectStartAndEnd() {
		if (indexPointer == 0) {
			this.replayEvent.onStart();
			if (this.reverse) {
				this.play = false;
			}
		} else if (indexPointer == this.dataSize - 1) {
			this.replayEvent.onEnd();
			if (!this.reverse) {
				this.play = false;
			}
		}

		// if play stopped, reset indexPointer and ticker
		if (!this.play) {
			this.indexPointer = 0;
			ticker = data.get(0).getEpochTimeMs();
			if (this.repeat) {
				this.play = true;
			}
		}
	}

	private int binarySearchClosest(List<TimedData<T>> data, long targetTime) {
		int lastIndex = data.size() - 1;

		if (targetTime < data.get(0).getEpochTimeMs()) {
			return 0;
		}

		if (targetTime > data.get(lastIndex).getEpochTimeMs()) {
			return lastIndex;
		}

		int lo = 0;
		int hi = lastIndex;

		while (lo <= hi) {
			int mid = (hi + lo) / 2;

			if (targetTime < data.get(mid).getEpochTimeMs()) {
				hi = mid - 1;
			} else if (targetTime > data.get(mid).getEpochTimeMs()) {
				lo = mid + 1;
			} else {
				return mid;
			}
		}
		return (data.get(lo).getEpochTimeMs() - targetTime) < (targetTime - data.get(hi).getEpochTimeMs()) ? lo : hi;
	}

}
