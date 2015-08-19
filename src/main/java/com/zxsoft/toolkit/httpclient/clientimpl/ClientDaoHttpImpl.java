package com.zxsoft.toolkit.httpclient.clientimpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import zx.soft.utils.json.JsonUtils;

public class ClientDaoHttpImpl {

	private CloseableHttpClient httpClient;

	public ClientDaoHttpImpl() {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(200);
		cm.setDefaultMaxPerRoute(20);
		HttpHost localhost = new HttpHost("localhost", 80);
		cm.setMaxPerRoute(new HttpRoute(localhost), 50);
		httpClient = HttpClients.custom().setConnectionManager(cm)
				.setRetryHandler(new DefaultHttpRequestRetryHandler()).build();
	}

	public void doPost(String url, String data) {

		HttpContext context = HttpClientContext.create();
		try {
			HttpPost httpPost = new HttpPost(url);
			HttpEntity entity = new StringEntity(data, ContentType.create("application/json", "UTF-8"));
			httpPost.setEntity(entity);
			//强烈建议每个线程（执行一次请求）有自己的HttpContext实例
			CloseableHttpResponse response = httpClient.execute(httpPost, context);
			response.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void close() {
		try {
			httpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ClientDaoHttpImpl client = new ClientDaoHttpImpl();
		String num = null;
		int threadnum = 10;
		int postnum = 10000;
		List<Thread> threads = new ArrayList<>();
		for (int i = 0; i < threadnum; i++) {
			List<String> nums = new ArrayList<>();
			for (int j = 0; j < postnum; j++) {
				num = String.valueOf(i) + String.valueOf(j);
				nums.add(num);
			}
			Thread thread = new Thread(new PostThread(client, JsonUtils.toJsonWithoutPretty(nums)));
			threads.add(thread);
		}
		for (int i = 0; i < threadnum; i++) {
			threads.get(i).start();
		}

		for (int i = 0; i < threadnum; i++) {
			try {
				threads.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		client.close();
	}

}
