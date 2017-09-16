package com.gmail.andreyksu.controller;

import com.gmail.andreyksu.observers.IResultObserver;
import com.gmail.andreyksu.observers.ITimeObserver;

public interface ICalcController extends IResultObserver, ITimeObserver {

	void performCalc();

	void saveResult();
	
}
