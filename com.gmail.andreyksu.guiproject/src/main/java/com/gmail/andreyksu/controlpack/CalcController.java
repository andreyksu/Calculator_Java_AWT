package com.gmail.andreyksu.controlpack;

import java.util.Date;

import com.gmail.andreyksu.modelpack.CalcModelInterface;
import com.gmail.andreyksu.observers.ResultObserverInterface;
import com.gmail.andreyksu.observers.TimeObserverInterface;
import com.gmail.andreyksu.viewpack.CalcViewer;
import com.gmail.andreyksu.viewpack.CalcViewerInterface;

public class CalcController implements CalcControllerInterface {

    private CalcModelInterface calcModel;

    private CalcViewerInterface calcViewer;

    public CalcController(CalcModelInterface calcModel) {
        this.calcViewer = new CalcViewer(this);
        this.calcModel = calcModel;
        calcModel.registerObserver((ResultObserverInterface) this);
        calcModel.registerObserver((TimeObserverInterface) this);
    }

    public void resultUpdate() {
        String result = calcModel.getResult();
        calcViewer.getResultField().setText(result);
        calcViewer.getPerformanceButton().setEnabled(true);

    }

    public void timeUpdate() {
        calcViewer.getClockPaint().setSeconds(new Date().getSeconds(),
                new Date().getMinutes(), new Date().getHours());
    }

    public void performCalc() {
        String expression = calcViewer.getExpressionField().getText();
        calcViewer.getPerformanceButton().setEnabled(false);
        calcModel.performCalc(expression);

    }

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

    public void setVisibleHint(String message) {
        calcViewer.showHint(message);
        calcViewer.hideHint();
    }
}
