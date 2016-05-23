package com.gmail.andreyksu.controlpack;

import com.gmail.andreyksu.modelpack.CalcModelInterface;
import com.gmail.andreyksu.observers.ResultObserverInterface;
import com.gmail.andreyksu.viewpack.CalcViewer;
import com.gmail.andreyksu.viewpack.CalcViewerInterface;

public class CalcController implements CalcControllerInterface {

	private CalcModelInterface calcModel;

	private CalcViewerInterface calcViewer;

	public CalcController(CalcModelInterface calcModel) {
		this.calcViewer = new CalcViewer(this);
		this.calcModel = calcModel;
		calcModel.registerObserver((ResultObserverInterface) this);
	}

	@Override
	public void resultUpdate() {
		String result = calcModel.getResult();
		calcViewer.getResultField().setText(result);
		calcViewer.getPerformanceButton().setEnabled(true);

	}

	@Override
	public void timeUpdate() {

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
