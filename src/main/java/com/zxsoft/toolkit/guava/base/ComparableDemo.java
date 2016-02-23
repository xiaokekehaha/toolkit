package com.zxsoft.toolkit.guava.base;

import com.google.common.collect.ComparisonChain;

public class ComparableDemo {

	public static class Person implements Comparable<Person> {

		private int age;
		private String name;

		public Person() {

		}

		public Person(int age, String name) {
			this.age = age;
			this.name = name;
		}

		@Override
		public int compareTo(Person o) {

			return ComparisonChain.start().compare(this.age, o.getAge()).compare(this.name, o.name).result();
			//compare(this.anEnum, that.anEnum, Ordering.natural().nullsLast())
		}

		public int getAge() {
			return age;
		}

		public String getName() {
			return name;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public static void main(String[] args) {
		Person p1 = new Person(2, "hello");
		Person p2 = new Person(1, "hello");
		Person p3 = new Person(2, "hf");
		System.out.println(p1.compareTo(p2) + ";" + p1.compareTo(p3) + ";" + p2.compareTo(p3));
	}
}
