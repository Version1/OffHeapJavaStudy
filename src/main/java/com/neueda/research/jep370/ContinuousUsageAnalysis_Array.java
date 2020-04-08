package com.neueda.research.jep370;

import static com.neueda.research.jep370.app.OhlcStatsGenerator.CANDLE_SIZE;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import com.neueda.research.jep370.app.ArrayOhlcStatsGenerator;

public class ContinuousUsageAnalysis_Array {

	private static AtomicBoolean isRunning = new AtomicBoolean(true);

	private static Random rand = new Random();

	private ArrayOhlcStatsGenerator asg = new ArrayOhlcStatsGenerator();
	
	private static int totalCondleCount = 0;
	
	public void doRun() {
		System.out.println("Starting Run at date "+new Date());

		while (isRunning.get()) {
			for (int i = 0; i < CANDLE_SIZE; i++) {
				asg.addCandle(rand.nextLong(), 
						rand.nextDouble(), 
						rand.nextDouble(), 
						rand.nextDouble(), 
						rand.nextDouble(),
						rand.nextLong());
				totalCondleCount++;
			}
			asg.reset();
		}
	}

	public static void main(String[] args) {
		scheduleRunEnd();
		startRun();
	}

	private static void startRun() {
		new ContinuousUsageAnalysis_Array().doRun();
	}

	private static void scheduleRunEnd() {
		Date terminationDate = Date.from(Instant.now().plus(5, ChronoUnit.MINUTES));
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("Ending run. Number of candles processed : "+totalCondleCount);
				isRunning = new AtomicBoolean(false);
			}
		}, terminationDate);
		System.out.println("Run schdeuled to end at date "+terminationDate);
	}
}
