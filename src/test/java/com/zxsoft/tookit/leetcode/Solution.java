package com.zxsoft.tookit.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * leetcode
 * @author fgq
 *
 */
public class Solution {

	public int hammingWeight(int n) {
		int ones = 0;
		while (n != 0) {
			ones = ones + (n & 1);
			n = n >>> 1;
		}
		return ones;
	}

	public int firstUniqChar(String s) {
		int value = -1;
		if (s != null) {
			if (s.length() == 1)
				value = 0;
			else {
				for (int i = 0; i < s.length(); i++) {
					value = i;
					for (int j = 0; j < s.length(); j++) {
						if (j != i) {
							if (s.charAt(i) == s.charAt(j)) {
								value = -1;
								break;
							}
						}
					}
					if (value == i) {
						break;
					}
				}
			}
		}
		return value;
		//		int freq [] = new int[26];
		//        for(int i = 0; i < s.length(); i ++)
		//            freq [s.charAt(i) - 'a'] ++;
		//        for(int i = 0; i < s.length(); i ++)
		//            if(freq [s.charAt(i) - 'a'] == 1)
		//                return i;
		//        return -1;
	}

	public boolean isSameTree(TreeNode p, TreeNode q) {
		boolean same = false;
		if (p == null && q == null) {
			same = true;
		}
		if (p != null && q != null) {
			if (p.val == q.val) {
				if (isSameTree(p.left, q.left) && isSameTree(p.right, q.right)) {
					same = true;
				}
			}
		}
		return same;

	}

	public int[] intersection(int[] nums1, int[] nums2) {

		if (nums1 == null || nums1.length < 1 || nums2 == null || nums2.length < 1) {
			return new int[0];
		}

		Set<Integer> result = new HashSet<>();
		for (int i = 0; i < nums1.length; i++) {
			for (int j = 0; j < nums2.length; j++) {
				if (nums1[i] == nums2[j]) {
					result.add(nums1[i]);
					break;
				}
			}
		}

		if (result.size() > 0) {
			int[] inter = new int[result.size()];
			int k = 0;
			for (Integer i : result) {
				inter[k++] = i;
			}
			return inter;
		} else {
			return new int[0];
		}

	}

	public void moveZeroes(int[] nums) {
		if (nums != null && nums.length > 1) {
			for (int i = 0; i < nums.length; i++) {
				if (nums[i] == 0) {
					for (int j = i + 1; j < nums.length; j++) {
						if (nums[j] != 0) {
							nums[i] = nums[j];
							nums[j] = 0;
							break;
						}
					}
				}
			}
		}
	}

	public TreeNode invertTree(TreeNode root) {
		if (root == null) {
			return null;
		}
		TreeNode temp_left = invertTree(root.left);
		TreeNode temp_right = invertTree(root.right);
		root.left = temp_right;
		root.right = temp_left;
		return root;
	}

	/**
	 * 为什么一个超时一个不超时
	 * @param root
	 * @return
	 */
	public int maxDepth(TreeNode root) {
		if (root == null) {
			return 0;
		}
		//		return 1 + (maxDepth(temp_root.left) > maxDepth(temp_root.right) ? maxDepth(temp_root.left)
		//				: maxDepth(temp_root.right));
		return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));

	}

	/**
	 * 1.考虑判空;2.考虑不为空但是长度为0的情况;3.不论总长度是奇还是偶只需要互换（count/2）次即可。
	 * @param s
	 * @return
	 */
	public String reverseString(String s) {

		if (s == null) {
			return null;
		}
		if (s.length() == 0) {
			return s;
		}

		int count = s.length();
		char[] temp = new char[count];
		for (int i = 0; i < count; i++) {
			temp[i] = s.charAt(i);
		}
		for (int i = 0; i < (count / 2); i++) {
			char t = temp[i];
			temp[i] = temp[count - i - 1];
			temp[count - i - 1] = t;
		}
		return new String(temp);
	}

	/**
	 * 1.Character;2.Set.contains()函数名称;
	 * 3.for (int i = 0, j = 0; i < s.length(); i++) i,j声明数据类型时只需一个int说明
	 * @param s
	 * @return
	 */
	public String reverseVowels(String s) {
		Set<Character> vowels = new HashSet<>();
		vowels.add('a');
		vowels.add('e');
		vowels.add('i');
		vowels.add('o');
		vowels.add('u');
		vowels.add('A');
		vowels.add('E');
		vowels.add('I');
		vowels.add('O');
		vowels.add('U');

		char[] temp = new char[s.length()];
		int[] rev = new int[s.length()];
		int count = 0;
		for (int i = 0, j = 0; i < s.length(); i++) {
			if (vowels.contains(s.charAt(i))) {
				count++;
				rev[j++] = i;
			}
			temp[i] = s.charAt(i);
		}

		if (count < 2) {
			return s;
		}

		for (int i = 0; i < count / 2; i++) {
			char t = temp[rev[i]];
			temp[rev[i]] = temp[rev[count - i - 1]];
			temp[rev[count - i - 1]] = t;

		}
		return new String(temp);
	}

}
