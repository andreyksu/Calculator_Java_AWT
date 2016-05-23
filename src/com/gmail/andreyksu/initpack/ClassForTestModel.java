package com.gmail.andreyksu.initpack;

import java.util.List;
import java.util.Scanner;

import com.gmail.andreyksu.modelpack.pefrormcalc.PerformClacByRPR;

public class ClassForTestModel {

	static Scanner in = new Scanner(System.in);;

	public static void main(String[] args) {
		PerformClacByRPR perf = new PerformClacByRPR();
		// String input = null;
		// do {
		// System.out.print(">>>>");
		// input = in.nextLine();
		// System.out.println(perf.validate(input));
		// System.out.println(perf.addZerroBeforeNegativeOperator(input));
		// } while (!input.equals("-1"));
		System.out.println(perf.perform("-(-8+2*(-5))/(-1+3*(-2)-(-4))"));
	}

}
