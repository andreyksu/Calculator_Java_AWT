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

	public CalcController(ICalcModel calcModel) {
		this.calcViewer = new CalcViewer(this);
		this.calcModel = calcModel;
		calcModel.registerObserver((IResultObserver) this);
		calcModel.registerObserver((ITimeObserver) this);
	}

	@Override
	public void resultUpdate() {
		String result = calcModel.getResult();
		calcViewer.getResultField().setText(result);
		calcViewer.getPerformanceButton().setEnabled(true);

	}

	@Override
	public void timeUpdate() {
	    calcViewer.getClockPaint().setSeconds(new Date().getSeconds(), new Date().getMinutes(),new Date().getHours());
//		System.out.println("TimerInController");
	}

	@Override
	public void performCalc() {
		String expression = calcViewer.getExpressionField().getText();
		calcViewer.getPerformanceButton().setEnabled(false);
		calcModel.performCalc(expression);

	}

	@Override
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
			message = "Incorrect path. Void or contain flaw!";
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
