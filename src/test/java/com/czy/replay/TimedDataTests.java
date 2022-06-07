package com.czy.replay;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class TimedDataTests {

	private TimedDataComparator<String> comparator = new TimedDataComparator<String>();

	@Test
	void TimedDataImplementationTest() {
		TimedData<String> timedData = new TimedDataImp(194832000000l, "CZY");
		assertEquals(timedData.getEpochTimeMs(), 194832000000l);
		assertEquals(timedData.getValue(), "CZY");
	}

	@Test
	void TimedDataSortingTest() {
		List<TimedData<String>> data = TestConstants.UNSORTED_DATA;
		assertEquals(data.get(0).getValue(), "2");
		assertEquals(data.get(1).getValue(), "4");
		assertEquals(data.get(2).getValue(), "0");
		assertEquals(data.get(9).getValue(), "5");
		data.sort(comparator);
		assertEquals(data.get(0).getValue(), "0");
		assertEquals(data.get(1).getValue(), "1");
		assertEquals(data.get(2).getValue(), "2");
		assertEquals(data.get(9).getValue(), "9");
	}

}
