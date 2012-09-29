package com.hello.jnitest;

public class Nadd {
	static {
		System.loadLibrary("Nadd");
	}
	public native int nadd(int a, int b);
}
