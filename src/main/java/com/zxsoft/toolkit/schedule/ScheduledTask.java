package com.zxsoft.toolkit.schedule;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduledTask implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(ScheduledTask.class);
	private long minStamp;
	private long maxStamp;

	@Override
	public void run() {
		Calendar current = Calendar.getInstance();
		long now = current.getTimeInMillis();
		maxStamp = this.getMaxStamp(now);
		minStamp = this.getMinStamp(now);
		if (minStamp == maxStamp) {
			ScheduledExceutorCalWeiboToHBase.counter.set(0);
		} else {
			ScheduledExceutorCalWeiboToHBase.counter.incrementAndGet();
		}
		logger.info("[" + minStamp + "," + maxStamp + "]" + ":" + ScheduledExceutorCalWeiboToHBase.counter);
	}

	//对分钟和秒数进行清除
	private long getMinStamp(long now) {
		Calendar cal_tmp = Calendar.getInstance();
		cal_tmp.setTimeInMillis(now);
		cal_tmp.set(cal_tmp.get(Calendar.YEAR), cal_tmp.get(Calendar.MONTH), cal_tmp.get(Calendar.DAY_OF_MONTH),
				cal_tmp.get(Calendar.HOUR_OF_DAY), 0, 0);
		cal_tmp.set(Calendar.MILLISECOND, 0);
		return cal_tmp.getTimeInMillis();
	}

	//对秒数进行清除
	private long getMaxStamp(long now) {
		Calendar cal_tmp = Calendar.getInstance();
		cal_tmp.setTimeInMillis(now);
		cal_tmp.set(cal_tmp.get(Calendar.YEAR), cal_tmp.get(Calendar.MONTH), cal_tmp.get(Calendar.DAY_OF_MONTH),
				cal_tmp.get(Calendar.HOUR_OF_DAY), cal_tmp.get(Calendar.MINUTE), 0);
		cal_tmp.set(Calendar.MILLISECOND, 0);
		return cal_tmp.getTimeInMillis();
	}

}
