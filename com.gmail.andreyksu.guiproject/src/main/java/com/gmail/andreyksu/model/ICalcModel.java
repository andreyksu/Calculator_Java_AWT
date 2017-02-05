package com.gmail.andreyksu.model;

import com.gmail.andreyksu.modelpack.pefrormcalc.ICalculator;
import com.gmail.andreyksu.modelpack.saver.ISaver;
import com.gmail.andreyksu.observers.IResultObserver;
import com.gmail.andreyksu.observers.ITimeObserver;

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
