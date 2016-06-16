package com.zxsoft.toolkit.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class SimpleNIO {

	public static void readFromFile() {
		FileInputStream fis = null;
		FileChannel fc = null;
		try {
			fis = new FileInputStream("src/main/resources/nio.txt");
			fc = fis.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			fc.read(buffer);
			buffer.flip();
			while (buffer.hasRemaining()) {
				System.out.println(buffer.get());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fc.close();
				fis.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void writeToFile() {

		FileOutputStream fos = null;
		FileChannel fc = null;
		try {
			fos = new FileOutputStream("src/main/resources/nio.txt");
			fc = fos.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			buffer.put("hello".getBytes());
			buffer.flip();
			fc.write(buffer);
			System.out.println(buffer.remaining());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fc.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void copy() {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		FileChannel fc = null;
		try {
			fis = new FileInputStream("src/main/resources/redis.properties");
			fos = new FileOutputStream("src/main/resources/nio.txt");
			fc = fis.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			while (fc.read(buffer) != -1) {
				buffer.flip();
				while (buffer.hasRemaining()) {
					fos.write(buffer.get());
				}
				buffer.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fc.close();
				fos.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) throws IOException {
		//SimpleNIO.readFromFile();
		//SimpleNIO.writeToFile();
		SimpleNIO.copy();
	}
}
