package com.gmail.andreyksu.initpack;

import com.gmail.andreyksu.controlpack.CalcController;
import com.gmail.andreyksu.controlpack.ICalcController;
import com.gmail.andreyksu.modelpack.CalcModel;
import com.gmail.andreyksu.modelpack.ICalcModel;

public class MainClass {

    public static void main(String[] args) {
        ICalcModel calcMosel = new CalcModel();
        ICalcController calcController = new CalcController(calcMosel);

    }

}
