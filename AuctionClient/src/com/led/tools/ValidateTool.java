package com.led.tools;

public class ValidateTool {
	
	// �����ж��ַ����Ƿ�Ϊ����(����С����
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
