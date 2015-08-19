package com.zxsoft.toolkit.httpclient.clientimpl;

public class PostThread implements Runnable {

	private String num;
	private ClientDaoHttpImpl client;
	private static final String URL = "http://192.168.6.126:8888/http/nums";

	public PostThread(ClientDaoHttpImpl client, String num) {
		this.client = client;
		this.num = num;
	}

	@Override
	public void run() {
		client.doPost(URL, this.num);
	}
}
