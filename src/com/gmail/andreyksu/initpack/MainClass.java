package com.gmail.andreyksu.initpack;

import com.gmail.andreyksu.controlpack.CalcController;
import com.gmail.andreyksu.controlpack.CalcControllerInterface;
import com.gmail.andreyksu.modelpack.CalcModel;
import com.gmail.andreyksu.modelpack.CalcModelInterface;

public class MainClass {

    public static void main(String[] args) {
        CalcModelInterface calcMosel = new CalcModel();
        CalcControllerInterface calcController = new CalcController(calcMosel);

    }

}
