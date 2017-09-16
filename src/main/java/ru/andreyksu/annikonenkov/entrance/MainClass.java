package ru.andreyksu.annikonenkov.entrance;

import ru.andreyksu.annikonenkov.controller.CalcController;
import ru.andreyksu.annikonenkov.controller.ICalcController;
import ru.andreyksu.annikonenkov.model.CalcModel;
import ru.andreyksu.annikonenkov.model.ICalcModel;

public class MainClass {
 
    /**
     * Точка запуска программы. Инициируется модель и контроллер.
     */
    public static void main(String[] args) {
        ICalcModel calcModel = new CalcModel();
        ICalcController calcController = new CalcController(calcModel);
    }
}
