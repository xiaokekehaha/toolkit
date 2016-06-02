package com.zxsoft.toolkit.listener;

import java.util.Observable;

public class School extends Observable {

	private String status = "";

	public void statusOfRing(String status) {
		if (this.status.equals(status)) {
			System.out.println("status of ring has not changed.");
			return;
		}
		this.status = status;
		setChanged();
		notifyObservers(this.status);
	}

}
