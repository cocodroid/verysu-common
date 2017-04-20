package com.verysu.basic.util;

import java.util.Stack;

public class IdUtil {
	private static String[] str62keys = { "0", "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
			"k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
			"x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
			"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
			"X", "Y", "Z" };

	public static String IntToEnode62(Integer int10) {
		String s62 = "";
		int r = 0;
		while (int10 != 0) {
			r = int10 % 62;
			s62 = str62keys[r] + s62;
			int10 = (int) Math.floor(int10 / 62.0);
		}
		return s62;
	}

	public static String Id2Mid(String str10) {
		String mid = "";
		int count = 1;
		for (int i = str10.length() - 7; i > -7; i = i - 7) // 从最后往前以7字节为一组读取字符
		{
			int offset = i < 0 ? 0 : i;
			int len = i < 0 ? str10.length() % 7 : 7;
			String temp = str10.substring(offset, offset + len);
			String url = IntToEnode62(Integer.valueOf(temp));
			if (count != 3) {// z xghm uXym 生成的链接从右往左的前2组，4位一组，不足4位的补0
				for (int j = 0; j < 4 - url.length(); j++) {
					url = "0" + url;
				}
			}
			mid = url + mid;
			count++;
		}
		return mid;
	}

	public static final char[] array = { 'q', 'w', 'e', 'r', 't', 'y', 'u',
			'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z',
			'x', 'c', 'v', 'b', 'n', 'm', '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P',
			'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V',
			'B', 'N', 'M' };

	public static String _10_to_62(long number) {
		Long rest = number;
		Stack<Character> stack = new Stack<Character>();
		StringBuilder result = new StringBuilder(0);
		while (rest != 0) {
			stack.add(array[new Long((rest - (rest / 62) * 62)).intValue()]);
			rest = rest / 62;
		}
		for (; !stack.isEmpty();) {
			result.append(stack.pop());
		}
		return result.toString();

	}

	public static long _62_to_10(String sixty_str) {
		int multiple = 1;
		long result = 0;
		Character c;
		for (int i = 0; i < sixty_str.length(); i++) {
			c = sixty_str.charAt(sixty_str.length() - i - 1);
			result += _62_value(c) * multiple;
			multiple = multiple * 62;
		}
		return result;
	}

	private static int _62_value(Character c) {
		for (int i = 0; i < array.length; i++) {
			if (c == array[i]) {
				return i;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		long i = 2139669l;
		System.out.println(i);
		System.out.println(_10_to_62(i));
		System.out.println(_62_to_10(_10_to_62(i)));
		System.out.println(IntToEnode62(414135151));
		System.out.println(Id2Mid("21411526464737"));
	}
	
}
