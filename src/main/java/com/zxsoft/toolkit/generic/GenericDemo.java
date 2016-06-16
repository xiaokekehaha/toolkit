package com.zxsoft.toolkit.generic;

public class GenericDemo {

	public static void main(String[] args) {
		Manager ceo = new Manager("a", 10);
		Manager cfo = new Manager("b", 20);
		Pair<Manager> buddies = new Pair<Manager>(ceo, cfo);
		printBuddies(buddies);
		ceo.setBonus(100);
		cfo.setBonus(50);
		printBuddies(buddies);
		Manager[] managers = { ceo, cfo };
		Pair<Employee> result = new Pair<>();
		minmaxBonus(managers, result);
		System.out.println("first:" + result.getFirst().getName() + ",second:" + result.getSecond().getName());
		maxminBouns(managers, result);
		System.out.println("first:" + result.getFirst().getName() + ",second:" + result.getSecond().getName());

	}

	public static void printBuddies(Pair<? extends Employee> p) {
		Employee first = p.getFirst();
		Employee second = p.getSecond();
		System.out.println(first.getName() + first.getBonus() + " and " + second.getName() + second.getBonus()
				+ " are buddies.");
	}

	public static void minmaxBonus(Manager[] a, Pair<? super Manager> result) {
		if (a == null || a.length == 0)
			return;
		Manager min = a[0];
		Manager max = a[0];
		for (int i = 1; i < a.length; i++) {
			if (min.getBonus() > a[i].getBonus())
				min = a[i];
			if (max.getBonus() < a[i].getBonus())
				max = a[i];
		}

		result.setFirst(min);
		result.setSecond(max);

	}

	public static void maxminBouns(Manager[] a, Pair<? super Manager> result) {
		minmaxBonus(a, result);
		PairAlg.swap(result);
	}

	static class PairAlg {

		public static boolean hasNulls(Pair<?> p) {
			return p.getFirst() == null || p.getSecond() == null;
		}

		public static void swap(Pair<?> p) {
			swapHelper(p);
		}

		public static <T> void swapHelper(Pair<T> p) {
			T t = p.getFirst();
			p.setFirst(p.getSecond());
			p.setSecond(t);
		}
	}
}
