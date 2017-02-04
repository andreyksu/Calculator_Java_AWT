package com.gmail.andreyksu.observers;

/**
 * Служит автоматического вызова после окончания расчета.
 * Вызов данного метода выполняет модель.
 */
public interface IResultObserver {

    void resultUpdate();

}
