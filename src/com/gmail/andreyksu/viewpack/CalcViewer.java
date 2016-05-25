package com.gmail.andreyksu.viewpack;

import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.TextField;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.gmail.andreyksu.controlpack.ICalcController;

public class CalcViewer extends Frame implements ICalcViewer {

	private PanelCalc panelCalc;

	private Window hint;

	private ExecutorService es = Executors.newSingleThreadExecutor();

	public CalcViewer(ICalcController calcController) {
		setTitle("Calucation");
		panelCalc = new PanelCalc(calcController);
		add(panelCalc);
		setSize(panelCalc.getPreferredSize());
		setResizable(false);
		setLocation(500, 500);
		setVisible(true);
		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				System.exit(0);
			}
		});
	}

	private Point getPostionMous() {
		int x = (int) (this.getMousePosition().getX() + this.getLocation().getX());
		int y = (int) (this.getMousePosition().getY() + this.getLocation().getY());
		return new Point(x, y);
	}

	@Override
	public void showHint(String message) {
		if (hint != null) {
			return;
		}
		hint = new Window(this);
		MyCanvas canvas = new MyCanvas();
		hint.setLocation(getPostionMous());
		hint.setSize(300, 30);
		canvas.setMessage(message);
		hint.add(canvas);
		hint.setVisible(true);
	}

	private class MyCanvas extends Canvas {

		String message;

		public void setMessage(String str) {
			message = str;
		}

		public void paint(Graphics g) {
			g.setColor(Color.BLACK);
			g.drawRect(0, 0, this.getSize().width - 1, this.getSize().height - 1);
			g.drawString(message, 10, 20);
		}
	}

	@Override
	public void hideHint() {
		Runnable thread = new Sleeper();
		es.execute(thread);
	}

	class Sleeper implements Runnable {

		@Override
		public void run() {
			try {
				TimeUnit.MILLISECONDS.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (hint != null) {
				hint.dispose();
			}
			hint = null;
		}

	}

	@Override
	public Button getPerformanceButton() {
		return panelCalc.getPerformanceButton();
	}

	@Override
	public TextField getExpressionField() {
		return panelCalc.getExpressionField();
	}

	@Override
	public TextField getResultField() {
		return panelCalc.getResultField();
	}

	@Override
	public Button getSaveButton() {
		return panelCalc.getSaveButton();
	}

	@Override
	public TextField getPathField() {
		return panelCalc.getPathField();
	}

}
