package com.gmail.andreyksu.modelpack;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.gmail.andreyksu.modelpack.pefrormcalc.ICalculator;
import com.gmail.andreyksu.modelpack.pefrormcalc.PerformCalcByJS;
import com.gmail.andreyksu.modelpack.pefrormcalc.polishreversenotation.CalculatorWithRPN;
import com.gmail.andreyksu.modelpack.saver.SaverClassToFile;
import com.gmail.andreyksu.modelpack.saver.ISaver;
import com.gmail.andreyksu.observers.IResultObserver;
import com.gmail.andreyksu.observers.ITimeObserver;

public class CalcModel implements ICalcModel {

    private List<IResultObserver> resultCalcObserver =
            new ArrayList<IResultObserver>();

    private List<ITimeObserver> timeObserver = new ArrayList<ITimeObserver>();

    private ExecutorService es = Executors.newSingleThreadExecutor();

    private ISaver saver;

    private ICalculator pci;

    private String expression;

    private String resultString;

    public CalcModel() {
        // pci = new CalculatorWithRPN();
        pci = new PerformCalcByJS();
        saver = new SaverClassToFile();
        srartNotifyTime();
    }

    // TODO: Нужно сделать переключение через радио-кнопку во въюхе.
    public void setPerformCalc(ICalculator pci) {
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

    /**
     * Метод служит для получения времени в момент записи результатов расчетов в
     * файл.
     */

    public String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh.mm.ss");
        return sdf.format(new Date());
    }

    public String getResult() {
        return resultString;
    }

    /**
     * Солужит для обновления времени во въюхе. В отдельном потоке каждую
     * секунду запускает перерисовку во въюхе. Вызов идет через Контроллер.
     */
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
        for (ITimeObserver observer : timeObserver) {
            observer.timeUpdate();
        }
    }

    private void notifyResultObserver() {
        for (IResultObserver observer : resultCalcObserver) {
            observer.resultUpdate();
        }
    }

    public String saveResult(String path) {
        return saver.save(path, getTime(), resultString, expression);
    }

    public void setSaver(ISaver saver) {
        this.saver = saver;
    }

    public void registerObserver(IResultObserver o) {
        resultCalcObserver.add(o);
    }

    public void removeObserver(IResultObserver o) {
        resultCalcObserver.remove(o);
    }

    public void registerObserver(ITimeObserver o) {
        timeObserver.add(o);
    }

    public void removeObserver(ITimeObserver o) {
        timeObserver.remove(o);
    }

}
