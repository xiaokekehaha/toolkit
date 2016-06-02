package com.zxsoft.toolkit.schedule;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduledExceutorCalWeiboToHBase {

	private static Logger logger = LoggerFactory.getLogger(ScheduledExceutorCalWeiboToHBase.class);
	public static AtomicLong counter = new AtomicLong(0L);

	public long getEarliestExecDate(long now) {
		Calendar cal_tmp = Calendar.getInstance();
		cal_tmp.setTimeInMillis(now);
		cal_tmp.set(cal_tmp.get(Calendar.YEAR), cal_tmp.get(Calendar.MONTH), cal_tmp.get(Calendar.DAY_OF_MONTH),
				cal_tmp.get(Calendar.HOUR_OF_DAY), cal_tmp.get(Calendar.MINUTE), 0);
		cal_tmp.set(Calendar.MILLISECOND, 0);
		logger.info("最近的执行时间:" + cal_tmp.getTimeInMillis());
		return cal_tmp.getTimeInMillis() + 60_000;
	}

	public static void main(String[] args) {
		ScheduledExceutorCalWeiboToHBase task = new ScheduledExceutorCalWeiboToHBase();
		//获取当前时间
		Calendar currentDate = Calendar.getInstance();
		long now = currentDate.getTimeInMillis();
		long earliest = task.getEarliestExecDate(now);
		Date earliestDate = new Date(earliest);
		System.out.println(currentDate.getTime() + "->" + currentDate.getTimeInMillis());
		System.out.println(earliestDate + "->" + earliestDate.getTime());
		long delay = earliestDate.getTime() - now;
		System.out.println("delay=" + delay);
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		ScheduledTask task1 = new ScheduledTask();
		//延时和执行的周期的单位要保持一致
		service.scheduleAtFixedRate(task1, delay, 1_000, TimeUnit.MILLISECONDS);
	}

}
