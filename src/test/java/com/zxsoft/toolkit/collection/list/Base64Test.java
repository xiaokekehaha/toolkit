package com.zxsoft.toolkit.collection.list;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.junit.Assert;
import org.junit.Test;

public class Base64Test {

	@Test
	public void test() throws UnsupportedEncodingException {
		String text = "我是中国人";
		byte[] byteByText = text.getBytes();
		Base64.Encoder encoder = Base64.getEncoder();
		Base64.Decoder decoder = Base64.getDecoder();
		String afterEncoder = encoder.encodeToString(byteByText);
		byte[] afterDecoder = decoder.decode(afterEncoder);
		Assert.assertEquals("5oiR5piv5Lit5Zu95Lq6", afterEncoder);
		Assert.assertEquals(text, new String(afterDecoder, "UTF-8"));
	}
}
