package com.zxsoft.toolkit.listener;

public class Client {

	public static void main(String[] args) {
		School school = new School();
		Teacher teacher = new Teacher();
		Student student = new Student();
		school.addObserver(teacher);
		school.addObserver(student);
		school.statusOfRing("on");
		System.out.println("10分钟后");
		school.statusOfRing("on");
		System.out.println("10分钟后");
		school.statusOfRing("off");
	}
}
