package ru.andreyksu.annikonenkov.model;

import ru.andreyksu.annikonenkov.modelpack.pefrormcalc.ICalculator;
import ru.andreyksu.annikonenkov.modelpack.saver.ISaver;
import ru.andreyksu.annikonenkov.observers.IResultObserver;
import ru.andreyksu.annikonenkov.observers.ITimeObserver;

public interface ICalcModel {

    String performCalc(String str);

    String getResult();

    String getTime();

    String saveResult(String path);

    void setPerformCalc(ICalculator pci);

    void setSaver(ISaver saver);

    /**
     * This section for observer
     */
    void registerObserver(IResultObserver o);

    void removeObserver(IResultObserver o);

    void registerObserver(ITimeObserver o);

    void removeObserver(ITimeObserver o);
}
