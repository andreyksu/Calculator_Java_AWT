package ru.andreyksu.annikonenkov.controller;

import ru.andreyksu.annikonenkov.observers.IResultObserver;
import ru.andreyksu.annikonenkov.observers.ITimeObserver;

public interface ICalcController extends IResultObserver, ITimeObserver {

	void performCalc();

	void saveResult();
	
}
