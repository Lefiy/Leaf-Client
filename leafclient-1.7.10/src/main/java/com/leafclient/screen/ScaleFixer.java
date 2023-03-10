package com.leafclient.screen;

public class ScaleFixer {
	
	private static int scale;
	private static double displayW, displayH, abjustW, abjustH;
	
	public static int disW(int amount) {
		return (int)((double)(amount / scale) * displayW * abjustW);
	}
	
	public static int disH(int amount) {
		return (int)((double)(amount / scale) * displayH * abjustH);
	}
	
	public static int disWB(int amount) {
		return (int)((double)(amount * scale) / displayW / abjustW);
	}
	
	public static int disHB(int size) {
		return (int)((double)(size / scale) * displayH * abjustH);
	}
	
	public static void setup(int scale, double displayW, double displayH, double abjustW, double abjustH) {
		ScaleFixer.scale = scale;
		ScaleFixer.displayW = displayW;
		ScaleFixer.displayH = displayH;
		ScaleFixer.abjustW = abjustW;
		ScaleFixer.abjustH = abjustH;
	}
}