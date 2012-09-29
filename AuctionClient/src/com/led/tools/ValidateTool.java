package com.led.tools;

public class ValidateTool {
	
	// 用于判断字符串是否为数字(包括小数）
	public static boolean isNumber(String number) {
		int n = 0;
		for (int i = 0; i < number.length(); i++) {
			if (!Character.isDigit(number.charAt(i))) {
				if (number.charAt(i) == '.') {
					n++;
					if (n > 1) {
						return false;
					}
				} else {
					return false;
				}
			}
		}
		return true;
	}
}
