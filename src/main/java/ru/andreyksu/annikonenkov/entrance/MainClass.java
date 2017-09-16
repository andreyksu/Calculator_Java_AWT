package com.gmail.andreyksu.entrance;

import com.gmail.andreyksu.controller.CalcController;
import com.gmail.andreyksu.controller.ICalcController;
import com.gmail.andreyksu.model.CalcModel;
import com.gmail.andreyksu.model.ICalcModel;

public class MainClass {
 
    /**
     * Точка запуска программы. Инициируется модель и контроллер.
     */
    public static void main(String[] args) {
        ICalcModel calcModel = new CalcModel();
        ICalcController calcController = new CalcController(calcModel);
    }
}
