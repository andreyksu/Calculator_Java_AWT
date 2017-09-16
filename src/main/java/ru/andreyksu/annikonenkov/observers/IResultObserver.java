package ru.andreyksu.annikonenkov.observers;

/**
 * Служит автоматического вызова после окончания расчета.
 * Вызов данного метода выполняет модель.
 */
public interface IResultObserver {

    void resultUpdate();

}
