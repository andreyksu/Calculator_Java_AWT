package com.gmail.andreyksu.initpack;

import com.gmail.andreyksu.controlpack.CalcController;
import com.gmail.andreyksu.controlpack.ICalcController;
import com.gmail.andreyksu.modelpack.CalcModel;
import com.gmail.andreyksu.modelpack.ICalcModel;

public class MainClass {
    
    // TODO 
    //перейти на log4j, все логгировать в файл, никаких конслольных выводов, нужно в мевен включить l4j
    //Убрать в интерфейсах I
    //Написать Junit тесты на все классы.
    //Документировать методы + описание проектика составные части и как запускать
    
    

    /**
     * Точка запуска программы.
     * Инициируется модель и контроллер. 
     */
    public static void main(String[] args) {
        ICalcModel calcModel = new CalcModel();
        ICalcController calcController = new CalcController(calcModel);
    }
}
