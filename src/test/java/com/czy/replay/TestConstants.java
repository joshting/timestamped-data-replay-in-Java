package com.czy.replay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestConstants {

	public static final List<TimedData<String>> SORTED_DATA = new ArrayList<TimedData<String>>(Arrays.asList(
			new TimedDataImp(1654580000l, "0"), new TimedDataImp(1654580100l, "1"), new TimedDataImp(1654580200l, "2"),
			new TimedDataImp(1654580300l, "3"), new TimedDataImp(1654580400l, "4"), new TimedDataImp(1654580500l, "5"),
			new TimedDataImp(1654580600l, "6"), new TimedDataImp(1654580700l, "7"), new TimedDataImp(1654580800l, "8"),
			new TimedDataImp(1654580900l, "9")));

	public static final List<TimedData<String>> UNSORTED_DATA = new ArrayList<TimedData<String>>(Arrays.asList(
			new TimedDataImp(1654580200l, "2"), new TimedDataImp(1654580400l, "4"), new TimedDataImp(1654580000l, "0"),
			new TimedDataImp(1654580100l, "1"), new TimedDataImp(1654580800l, "8"), new TimedDataImp(1654580600l, "6"),
			new TimedDataImp(1654580300l, "3"), new TimedDataImp(1654580700l, "7"), new TimedDataImp(1654580900l, "9"),
			new TimedDataImp(1654580500l, "5")));
}
