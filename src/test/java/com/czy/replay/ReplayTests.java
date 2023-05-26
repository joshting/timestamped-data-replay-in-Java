package com.czy.replay;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ReplayTests {

	private DataSummary summary = new DataSummary(0, 0l, 0l, 0l, new int[10]);
	private TimedData<String> currentData = null;
	private boolean started = false;

	@Test
	void PlayPauseTest() throws InterruptedException {
		summary = new DataSummary(0, 0l, 0l, 0l, new int[10]);
		currentData = null;
		started = false;
		Replay<String> replay = new Replay<String>(100, 10, 10, TestConstants.UNSORTED_DATA, new ReplayListener<String>() {

			@Override
			public void onDataSummary(DataSummary dataSummary) {
				summary = dataSummary;
			}

			@Override
			public void onDataItem(TimedData<String> data) {
				currentData = data;
			}

			@Override
			public void onElapsedTime(ElapsedTime relativeTime) {
			}

			@Override
			public void onStart() {
				started = true;
			}

			@Override
			public void onEnd() {
			}

			@Override
			public void onControlEvent(ReplayControlEvent control) {
			}

			@Override
			public void onDestroy() {
			}

		});
		Thread th = new Thread(replay);
		assertEquals(summary.getTotalDataPoints(), 10);
		assertEquals(summary.getStartTime(), 1654580000l);
		assertEquals(summary.getEndTime(), 1654580900l);
		assertEquals(summary.getTotalTime(), 900l);
		assertEquals(started, false);
		th.start();
		replay.play();
		Thread.sleep(350l);
		replay.pause();
		assertEquals(started, true);
		assertEquals(currentData.getValue(), "3");
		replay.play();
		Thread.sleep(450l);
		assertEquals(currentData.getValue(), "7");
	}
}
