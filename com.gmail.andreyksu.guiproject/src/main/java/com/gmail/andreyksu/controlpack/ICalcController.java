package com.gmail.andreyksu.controlpack;

import com.gmail.andreyksu.observers.ResultObserverInterface;
import com.gmail.andreyksu.observers.TimeObserverInterface;

public interface CalcControllerInterface extends ResultObserverInterface, TimeObserverInterface {

	void performCalc();

	void saveResult();
	
}
