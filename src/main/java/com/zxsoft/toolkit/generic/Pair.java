package com.zxsoft.toolkit.generic;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class Pair<T> {

	private T first;
	private T second;

	public Pair() {
	}

	public Pair(T first, T second) {
		this.first = first;
		this.second = second;
	}

	public T getFirst() {
		return this.first;
	}

	public T getSecond() {
		return this.second;
	}

	void setFirst(T first) {
		this.first = first;
	}

	void setSecond(T second) {
		this.second = second;
	}

	/**
	 * 如果调用makePair(Employee.class),Employee.class是Class<Employee>的一个对象;
	 * Class类是泛型的,String.class实际上是一个Class<String>类的对象
	 * @param cl
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static <T> Pair<T> makePair(Class<T> cl) throws InstantiationException, IllegalAccessException {
		return new Pair<>(cl.newInstance(), cl.newInstance());
	}

	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException {
		Class<?> cl = Class.forName("com.zxsoft.toolkit.generic.Pair");
		System.out.println(cl);
		//获取类名class, interface, array class, primitive type, or void
		System.out.println("类名:" + cl.getName());
		System.out.println("获取类型名:" + cl.getTypeName());
		//如果class是array，才会有容器类型，否则为null
		System.out.println("容器内部元素类型:" + cl.getComponentType());

		Method[] method = cl.getDeclaredMethods();
		for (int i = 0; i < method.length; i++) {
			System.out.println("显式声明的方法名: " + method[i] + ",返回类型: " + method[i].getReturnType());
		}

		Method[] mt = cl.getMethods();
		for (int i = 0; i < mt.length; i++) {
			Type type = mt[i].getGenericReturnType();
			System.out.println("所有方法名-" + i + ":" + mt[i] + ";   " + "函数的返回类型:" + type);
		}

		//如果这个类型被声明为一个泛型类型，此方法获得泛型类型变量(如T),否则返回长度为0的数组
		TypeVariable<?>[] tv = cl.getTypeParameters();
		for (int i = 0; i < tv.length; i++) {
			System.out.println("泛型类型变量" + i + ":" + tv[i]);
		}

		//  Object class, an interface, a primitive type, or void, then null
		Class<?> cla = cl.getSuperclass();
		System.out.println("超类:" + cla);

		Constructor<?>[] cons = cl.getConstructors();
		for (int i = 0; i < cons.length; i++) {
			System.out.println("public类型的构造函数" + i + ":" + cons[i]);
		}

		//所有的构造函数，包括默认的构造函数
		Constructor<?>[] consd = cl.getDeclaredConstructors();
		for (int i = 0; i < consd.length; i++) {
			System.out.println("所有的构造函数" + i + ":" + consd[i]);
		}
	}
}
