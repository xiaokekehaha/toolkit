package com.zxsoft.toolkit.httpclient.browser;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Simproxy {

	/*
	 *	@param  对话内容;
	 * @return  回应内容;
	 * @throws ClientProtocolException 如果出现协议错误
	 * @throws  IOException
	 */
	public String chatWithSimsimi(String text) throws ClientProtocolException, IOException {

		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet(String.format(
					"http://www.simsimi.com//requestChat?lc=ch&ft=1.0&req=%s&uid=66013359", text));
			httpGet.addHeader("Host", "www.simsimi.com");
			httpGet.addHeader("User-Agent",
					"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");
			httpGet.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
			httpGet.addHeader("Accept-Language", "en-US,en;q=0.8");
			httpGet.addHeader("Accept-Encoding", "gzip,deflate,sdch");
			httpGet.addHeader("Content-Type", "application/json; charset=utf-8");
			httpGet.addHeader("X-Requested-With", "XMLHttpRequest");
			httpGet.addHeader("Referer", "http://www.simsimi.com/talk/Language?LC=ch");
			httpGet.addHeader("Cookie", "_gat=1");
			httpGet.addHeader("Connection", "keep-alive");
			httpGet.addHeader("DNT", "1");

			CloseableHttpResponse response = httpClient.execute(httpGet);
			try {
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode >= 200 && statusCode < 300) {
					result = EntityUtils.toString(response.getEntity());
				}
			} finally {
				response.close();
			}
		} finally {
			httpClient.close();
		}
		return result;
	}

	public static void main(String[] args) throws ClientProtocolException, IOException {
		Simproxy proxy = new Simproxy();
		System.out.println(proxy.chatWithSimsimi("你好"));
	}
}
