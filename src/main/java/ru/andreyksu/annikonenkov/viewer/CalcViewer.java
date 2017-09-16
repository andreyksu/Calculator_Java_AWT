package ru.andreyksu.annikonenkov.viewer;

import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.FlowLayout;
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

import ru.andreyksu.annikonenkov.controller.ICalcController;

public class CalcViewer extends Frame implements ICalcViewer {

	private PanelCalc panelCalc;

	private PanelClock panelClock;

	private Window hint;

	private ExecutorService es = Executors.newSingleThreadExecutor();

	/**
	 * Во въювер добавляем лейаут и две панельки: панелька калькулятора и часов.
	 * Устанавливаем мин. размер окна и вешаем обработчик на закрытие окна.
	 * 
	 * @param calcController
	 */
	public CalcViewer(ICalcController calcController) {
		setLayout(new FlowLayout());
		setTitle("Calucation");
		panelCalc = new PanelCalc(calcController);
		add(panelCalc);
		panelClock = new PanelClock(calcController);
		add(panelClock);
		setSize(715, 275);
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

	/**
	 * Здесь мы рисуем сообщение в виде хинта. Рисуем через канвас.
	 * 
	 * @author andrey
	 *
	 */
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

	/**
	 * Скрывает хинт/подсказку о невалидности строки для файла сохранения
	 * результатов. Закрытие происходит через 1,5, отдельном потоке.
	 * {@inheritDoc}
	 */
	public void hideHint() {
		Runnable thread = new Sleeper();
		es.execute(thread);
	}

	class Sleeper implements Runnable {

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

	public Button getPerformanceButton() {
		return panelCalc.getPerformanceButton();
	}

	public TextField getExpressionField() {
		return panelCalc.getExpressionField();
	}

	public TextField getResultField() {
		return panelCalc.getResultField();
	}

	public Button getSaveButton() {
		return panelCalc.getSaveButton();
	}

	public TextField getPathField() {
		return panelCalc.getPathField();
	}

	public PanelClock getClockPaint() {
		return panelClock;
	}

}
