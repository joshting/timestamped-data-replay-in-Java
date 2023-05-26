package com.czy.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.czy.replay.DataSummary;
import com.czy.replay.ElapsedTime;
import com.czy.replay.Replay;
import com.czy.replay.ReplayControlEvent;
import com.czy.replay.ReplayListener;
import com.czy.replay.ReplaySpeedEnum;
import com.czy.replay.TimedData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PlayBackService {

	private ObjectMapper mapper = new ObjectMapper();

	private List<TimedData<Sensor>> dataList = new ArrayList<TimedData<Sensor>>();

	private SimpleDateFormat timeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	private Scanner keys = new Scanner(System.in);

	private String millisecToHumanReadable(long duration) {
		return String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(duration),
				TimeUnit.MILLISECONDS.toSeconds(duration)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
	}

	public void play(File file) {
		try {
			InputStream inputStream = new FileInputStream(file);
			try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
				String line;
				while ((line = br.readLine()) != null) {
					LowRateDataDto<Sensor> dto = mapper.readValue(line, new TypeReference<LowRateDataDto<Sensor>>() {
					});
					dataList.add(dto);
				}
				Replay<Sensor> replay = new Replay<Sensor>(100, 10, 10, dataList, new ReplayListener<Sensor>() {

					@Override
					public void onDataSummary(DataSummary dataSummary) {
						System.out.println(
								String.format("Summary: total points: %s, start time: %s, end time: %s, total time: %s",
										dataSummary.getTotalDataPoints(),
										timeFormat.format(new Date(dataSummary.getStartTime())),
										timeFormat.format(new Date(dataSummary.getEndTime())),
										millisecToHumanReadable(dataSummary.getTotalTime())));
					}

					@Override
					public void onDataItem(TimedData<Sensor> data) {
						Date time = new Date(data.getEpochTimeMs());
						System.out.println(timeFormat.format(time) + "  "
								+ new String(new char[data.getValue().getValue()]).replace('\0', ' ') + "*");
					}

					@Override
					public void onElapsedTime(ElapsedTime elapsedTime) {
//						System.out.println(elapsedTime.getElapsed() + "/" + elapsedTime.getTotal());
					}

					@Override
					public void onStart() {
						System.out.println("STARTED");
					}

					@Override
					public void onEnd() {
						System.out.println("ENDED");
					}

					@Override
					public void onControlEvent(ReplayControlEvent control) {
						System.out.println(control.getControl() + ": " + control.getValue());
					}

					@Override
					public void onDestroy() {
						System.out.println("DESTROYED");
					}
				});
				Thread th = new Thread(replay);
				th.start();
				System.out.println(
						"Enter command\n(0-pause, 1-play, 2-forward 2x, 3-forward 8x, 4-forward 0.5x, 5-rewind 4x, 6-stop, 7-toggle repeat, 8-seek, 9-destroy):");
				int n = keys.nextInt();
				while (true) {
					switch (n) {
					case 0:
						replay.pause();
						break;
					case 1:
						replay.play();
						break;
					case 2:
						replay.forward(ReplaySpeedEnum.SPEEDUP_2_00);
						break;
					case 3:
						replay.forward(ReplaySpeedEnum.SPEEDUP_8_00);
						break;
					case 4:
						replay.forward(ReplaySpeedEnum.SLOWDOWN_0_50);
						break;
					case 5:
						replay.rewind(ReplaySpeedEnum.SPEEDUP_4_00);
						break;
					case 6:
						replay.stop();
						break;
					case 7:
						replay.toggleRepeat();
						break;
					case 8:
						System.out.println("Enter epoch time to seek:");
						long seekLong = keys.nextLong();
						replay.seek(seekLong);
						break;
					case 9:
						replay.destroy();
						break;
					}
					System.out.println(
							"Enter command\n(0-pause, 1-play, 2-forward 2x, 3-forward 8x, 4-forward 0.5x, 5-rewind 4x, 6-stop, 7-toggle repeat, 8-seek, 9-destroy):");
					n = keys.nextInt();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
