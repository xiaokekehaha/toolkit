package com.zxsoft.toolkit.guava.base;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;

public class GoogleMapSetDemo {

	public static void main(String[] args) {

		Multimap<String, String> maps = ArrayListMultimap.create();
		maps.put("friends", "friend1");
		maps.put("friends", "friend1");
		maps.put("friends", "friend2");
		maps.put("classmates", "classmate1");
		maps.put("classmates", "classmate2");
		System.out.println(maps.get("friends"));
		System.out.println(ImmutableSet.copyOf(maps.get("friends")));

		Multiset<String> sets = HashMultiset.create();
		sets.add("friend1");
		sets.add("friend1");
		sets.add("friend1");
		sets.add("friend2");
		System.out.println(sets.count("friend1"));
		System.out.println(ImmutableSet.copyOf(sets));

	}
}
