package com.gmail.andreyksu.initpack;

import com.gmail.andreyksu.controlpack.CalcController;
import com.gmail.andreyksu.controlpack.CalcControllerInterface;
import com.gmail.andreyksu.modelpack.CalcModel;
import com.gmail.andreyksu.modelpack.CalcModelInterface;

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
        CalcModelInterface calcModel = new CalcModel();
        CalcControllerInterface calcController = new CalcController(calcModel);
    }
}
