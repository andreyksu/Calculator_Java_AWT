package com.gmail.andreyksu.modelpack;

import com.gmail.andreyksu.modelpack.pefrormcalc.CalculatorInterface;
import com.gmail.andreyksu.modelpack.saver.SaverInterface;
import com.gmail.andreyksu.observers.ResultObserverInterface;
import com.gmail.andreyksu.observers.TimeObserverInterface;

public interface CalcModelInterface {

    String performCalc(String str);

    String getResult();

    String getTime();

    String saveResult(String path);

    void setPerformCalc(CalculatorInterface pci);

    void setSaver(SaverInterface saver);

    /**
     * This section for observer
     */
    void registerObserver(ResultObserverInterface o);

    void removeObserver(ResultObserverInterface o);

    void registerObserver(TimeObserverInterface o);

    void removeObserver(TimeObserverInterface o);
}
