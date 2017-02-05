package com.gmail.andreyksu.controlpack;

import com.gmail.andreyksu.observers.IResultObserver;
import com.gmail.andreyksu.observers.ITimeObserver;

public interface ICalcController extends IResultObserver, ITimeObserver {

	void performCalc();

	void saveResult();
	
}
