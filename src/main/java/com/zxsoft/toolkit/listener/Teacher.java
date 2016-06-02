package com.zxsoft.toolkit.listener;

import java.util.Observable;
import java.util.Observer;

public class Teacher implements Observer {

	private String status;

	@Override
	public void update(Observable o, Object arg) {
		this.status = (String) arg;
		System.out.println("the status of ring is " + arg);
	}
}
