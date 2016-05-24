package com.gmail.andreyksu.modelpack;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.gmail.andreyksu.modelpack.pefrormcalc.CalculatorInterface;
import com.gmail.andreyksu.modelpack.pefrormcalc.PerformCalcByJS;
import com.gmail.andreyksu.modelpack.saver.SaverClassToFile;
import com.gmail.andreyksu.modelpack.saver.SaverInterface;
import com.gmail.andreyksu.observers.ResultObserverInterface;
import com.gmail.andreyksu.observers.TimeObserverInterface;

public class CalcModel implements CalcModelInterface {

	private List<ResultObserverInterface> resultCalcObserver = new ArrayList<ResultObserverInterface>();

	private List<TimeObserverInterface> timeObserver = new ArrayList<TimeObserverInterface>();

	private SaverInterface saver;

	private CalculatorInterface pci;

	private String expression;

	private String resultString;

	private long deltaMillis = 0;

	public CalcModel() {
		pci = new PerformCalcByJS();
		saver = new SaverClassToFile();
	}

	@Override
	public void setPerformCalc(CalculatorInterface pci) {
		this.pci = pci;
	}

	@Override
	public String performCalc(String str) {
		expression = str;
		deltaMillis = System.currentTimeMillis();
		if (str == null || str.length() == 0) {
			resultString = "Expression is empty!!!";
			notifyResultObserver();
			return resultString;
		}
		resultString = pci.performingCalculations(str);
		deltaMillis = System.currentTimeMillis() - deltaMillis;
		notifyResultObserver();
		return resultString;
	}

	@Override
	public String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh.mm.ss");
		sdf.format(new Date());
		return new Date().toString();
	}

	@Override
	public String getComputingTime() {
		return new Long(deltaMillis).toString();
	}

	@Override
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
		thread.start();
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

	@Override
	public String saveResult(String path) {
		return saver.save(path, getTime(), resultString, expression);
	}

	@Override
	public void setSaver(SaverInterface saver) {
		this.saver = saver;
	}

	@Override
	public void registerObserver(ResultObserverInterface o) {
		resultCalcObserver.add(o);
	}

	@Override
	public void removeObserver(ResultObserverInterface o) {
		resultCalcObserver.remove(o);
	}

	@Override
	public void registerObserver(TimeObserverInterface o) {
		if (!timeObserver.isEmpty()) {
			srartNotifyTime();
		}
		timeObserver.add(o);

	}

	@Override
	public void removeObserver(TimeObserverInterface o) {
		timeObserver.remove(o);
	}

}
