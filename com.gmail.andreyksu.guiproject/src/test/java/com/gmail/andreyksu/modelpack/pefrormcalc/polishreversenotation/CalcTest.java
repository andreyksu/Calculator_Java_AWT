package com.gmail.andreyksu.modelpack.pefrormcalc.polishreversenotation;


import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;
import com.gmail.andreyksu.modelpack.pefrormcalc.polishreversenotation.CalculatorWithRPN;

public class CalcTest {
    
    @Test
    public void firstPassTestRPNCalc() throws Exception{
        String expected = "4.0";
        CalculatorWithRPN calcRPN = new CalculatorWithRPN();
        String result = calcRPN.performingCalculations("-(2*(-08.0+2.0*(-5))/(-1+3*(-2)-(-4)))+4*2.0^2");
        assertArrayEquals(expected.toCharArray(), result.toCharArray());
    }
    
    @Test
    public void secondPassTestRPNCalc() throws Exception{
        String expected = "6.947368421";
        CalculatorWithRPN calcRPN = new CalculatorWithRPN();
        String tmpResult = calcRPN.performingCalculations("-(22*(-08.0+2.0*(-5))/(-1+3*(-2)-(-4^3)))");
        String result = tmpResult.substring(0, 11);
        assertArrayEquals(expected.toCharArray(), result.toCharArray());
    }
    
    @Test
    public void therdPassTestRPNCalc() throws Exception{
        String expected = "6.0";
        CalculatorWithRPN calcRPN = new CalculatorWithRPN();
        String result = calcRPN.performingCalculations("2+2*2");
        assertArrayEquals(expected.toCharArray(), result.toCharArray());
    }
    

}
