package com.gmail.andreyksu.modelpack;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.gmail.andreyksu.modelpack.pefrormcalc.CalculatorInterface;
import com.gmail.andreyksu.modelpack.pefrormcalc.PerformCalcByJS;
import com.gmail.andreyksu.modelpack.pefrormcalc.polishreversenotation.CalculatorWithRPN;
import com.gmail.andreyksu.modelpack.saver.SaverClassToFile;
import com.gmail.andreyksu.modelpack.saver.SaverInterface;
import com.gmail.andreyksu.observers.ResultObserverInterface;
import com.gmail.andreyksu.observers.TimeObserverInterface;

public class CalcModel implements CalcModelInterface {

    private List<ResultObserverInterface> resultCalcObserver =
            new ArrayList<ResultObserverInterface>();

    private List<TimeObserverInterface> timeObserver = new ArrayList<TimeObserverInterface>();

    private ExecutorService es = Executors.newSingleThreadExecutor();

    private SaverInterface saver;

    private CalculatorInterface pci;

    private String expression;

    private String resultString;

    public CalcModel() {
        pci = new CalculatorWithRPN();
        saver = new SaverClassToFile();
        srartNotifyTime();
    }

    public void setPerformCalc(CalculatorInterface pci) {
        this.pci = pci;
    }

    public String performCalc(String str) {
        expression = str;
        if (str == null || str.length() == 0) {
            resultString = "Expression is empty!";
            notifyResultObserver();
            return resultString;
        }
        resultString = pci.performingCalculations(str);
        notifyResultObserver();
        return resultString;
    }

    public String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh.mm.ss");
        return sdf.format(new Date());
    }

    public String getResult() {
        return resultString;
    }

    private void srartNotifyTime() {
        Thread thread = new Thread() {

            public void run() {
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        notifyTimeObserver();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.setDaemon(true);
        es.execute(thread);
    }

    synchronized private void notifyTimeObserver() {
        for (TimeObserverInterface observer : timeObserver) {
            observer.timeUpdate();
        }
    }

    private void notifyResultObserver() {
        for (ResultObserverInterface observer : resultCalcObserver) {
            observer.resultUpdate();
        }
    }

    public String saveResult(String path) {
        return saver.save(path, getTime(), resultString, expression);
    }

    public void setSaver(SaverInterface saver) {
        this.saver = saver;
    }

    public void registerObserver(ResultObserverInterface o) {
        resultCalcObserver.add(o);
    }

    public void removeObserver(ResultObserverInterface o) {
        resultCalcObserver.remove(o);
    }

    public void registerObserver(TimeObserverInterface o) {
        timeObserver.add(o);
    }

    public void removeObserver(TimeObserverInterface o) {
        timeObserver.remove(o);
    }

}
