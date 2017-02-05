package com.gmail.andreyksu.controlpack;

import java.util.Date;

import com.gmail.andreyksu.modelpack.ICalcModel;
import com.gmail.andreyksu.observers.IResultObserver;
import com.gmail.andreyksu.observers.ITimeObserver;
import com.gmail.andreyksu.viewpack.CalcViewer;
import com.gmail.andreyksu.viewpack.ICalcViewer;

public class CalcController implements ICalcController {

    private ICalcModel calcModel;

    private ICalcViewer calcViewer;

    /**
     * Конструктор
     * 
     * @param calcModel Инициализируется въювер и присваевается ссылка на
     *            модель. Происходит регистрация наблюдателей за событиями;
     *            "расчет выполнен" и "время/таймер".
     */
    public CalcController(ICalcModel calcModel) {
        this.calcViewer = new CalcViewer(this);
        this.calcModel = calcModel;
        calcModel.registerObserver((IResultObserver) this);
        calcModel.registerObserver((ITimeObserver) this);
    }

    /**
     * Данный мотод вызывается автоматом в моделе после окончания расчета.
     * Получает результат у модельки и отображает результат во въюхе.
     */
    public void resultUpdate() {
        String result = calcModel.getResult();
        calcViewer.getResultField().setText(result);
        calcViewer.getPerformanceButton().setEnabled(true);

    }

    /**
     * Данный метод вызывается автоматом в отдельном потоке модели (в методе
     * resultUpdate()) каждую секунду, для отрисовки времени.
     */

    public void timeUpdate() {
        calcViewer.getClockPaint().setSeconds(new Date().getSeconds(),
                new Date().getMinutes(), new Date().getHours());
    }

    /**
     * Вызывается во въюхе при нажатии кнопки рассчитать. {@inheritDoc}
     */
    public void performCalc() {
        String expression = calcViewer.getExpressionField().getText();
        calcViewer.getPerformanceButton().setEnabled(false);
        calcModel.performCalc(expression);

    }

    /**
     * Вызывается во въюхе при нажатии кнопки сохранить. {@inheritDoc}
     */
    public void saveResult() {
        String message = null;
        String path = calcViewer.getPathField().getText();
        if (validatePath(path)) {
            message = calcModel.saveResult(path);
            if (message == null) {
                return;
            } else {
                setVisibleHint(message);
                return;
            }

        } else {
            message = "Path void or contain flaw!";
            setVisibleHint(message);
        }

    }

    /**
     * Проверяет путь на корреткность перед сохранением. TODO: Нужно
     * переосмыслить данный метод и его необходимость.
     * 
     * @param path
     * @return
     */
    private boolean validatePath(String path) {
        char[] b;
        if (path.isEmpty()) {
            return false;
        } else {
            b = path.toCharArray();
        }
        if (b[0] == ' ') {
            return false;
        }
        return true;
    }

    /**
     * Класс служит для открытия подсказки и закрытия ее по прошествии 1.5с.
     * Закрытие происходит в отдельном потоке. См. реализацию закрытия во въюхе.
     * 
     * @param message
     */
    public void setVisibleHint(String message) {
        calcViewer.showHint(message);
        calcViewer.hideHint();
    }
}
