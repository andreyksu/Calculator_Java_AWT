package com.gmail.andreyksu.viewer;

import java.awt.Button;
import java.awt.TextField;

public interface ICalcViewer {

    TextField getExpressionField();

    Button getPerformanceButton();

    TextField getResultField();

    Button getSaveButton();

    TextField getPathField();

    void showHint(String message);

    void hideHint();

    public PanelClock getClockPaint();

}
