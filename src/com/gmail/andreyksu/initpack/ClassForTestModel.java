package com.gmail.andreyksu.initpack;

import java.util.List;
import java.util.Scanner;

import com.gmail.andreyksu.modelpack.pefrormcalc.polishreversenotation.CalculatorWithRPN;

public class ClassForTestModel {

	static Scanner in = new Scanner(System.in);;

	public static void main(String[] args) {
		CalculatorWithRPN perf = new CalculatorWithRPN();
//		System.out.println(perf.performingCalculations("-(-8.0+2.0*(-5))/(-1+3*(-2)-(-4))+4^2.0"));
		System.out.println(perf.performingCalculations("-(-8.0+2.0*(-5))/(-1+3*(-2)-(-4))+4*2.0"));
	}

}
