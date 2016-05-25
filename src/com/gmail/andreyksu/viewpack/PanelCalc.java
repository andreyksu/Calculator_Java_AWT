package com.gmail.andreyksu.viewpack;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

import com.gmail.andreyksu.controlpack.ICalcController;

public class PanelCalc extends Panel {

	private ICalcController calcController;

	private EnterPanel enterPanel = new EnterPanel();

	private PerformPanel performPanel = new PerformPanel();

	private ResultPanel resultPanel = new ResultPanel();

	private SavePanel savePanel = new SavePanel();

	private SavePanelButton savePanelButton = new SavePanelButton();

	public PanelCalc(ICalcController calcController) {
		super(new GridLayout(5, 1, 5, 5));
		this.calcController = calcController;
		setPreferredSize(new Dimension(330, 255));
		add(enterPanel);
		add(performPanel);
		add(resultPanel);
		add(savePanel);
		add(savePanelButton);
	}

	public TextField getExpressionField() {
		// TODO Auto-generated method stub
		return enterPanel.getExpressionField();
	}

	public Button getPerformanceButton() {
		// TODO Auto-generated method stub
		return performPanel.getPerformButton();
	}

	public TextField getResultField() {
		// TODO Auto-generated method stub
		return resultPanel.getResultField();
	}

	public TextField getPathField() {
		// TODO Auto-generated method stub
		return savePanel.getSaveFiled();
	}

	public Button getSaveButton() {
		// TODO Auto-generated method stub
		return savePanelButton.getButton();
	}

	private class EnterPanel extends Panel {

		private Label enerLabel = new Label(String.format("%s", "Expression:"));

		private TextField expressionField = new TextField(25);

		EnterPanel() {
			super(new FlowLayout(FlowLayout.RIGHT, 5, 5));
			add(enerLabel);
			addListeners();
			add(expressionField);
		}

		public TextField getExpressionField() {
			return expressionField;
		}

		void addListeners() {
			expressionField.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {
					performPanel.getPerformButton().setEnabled(true);
				}

				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void keyPressed(KeyEvent e) {
					// TODO Auto-generated method stub

				}
			});
		}

	}

	private class PerformPanel extends Panel {

		private Button performButton = new Button("PerformCalc");

		PerformPanel() {
			super(new FlowLayout(FlowLayout.RIGHT, 5, 5));
			// performButton.setPreferredSize(new Dimension(210, 35));
			addListners();
			add(performButton);
		}

		private void addListners() {
			performButton.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					calcController.performCalc();
				}
			});

		}

		public Button getPerformButton() {
			return performButton;
		}

	}

	private class ResultPanel extends Panel {

		private Label resultLabel = new Label(String.format("%s", "Result:"));

		private TextField resultField = new TextField(25);

		ResultPanel() {
			super(new FlowLayout(FlowLayout.RIGHT, 5, 5));
			resultField.setEditable(false);
			add(resultLabel);
			add(resultField);
		}

		public TextField getResultField() {
			return resultField;
		}
	}

	private class SavePanel extends Panel {

		private Label pathLabel = new Label(String.format("%s", "Path:"));

		private TextField pathToFile = new TextField(25);

		SavePanel() {
			super(new FlowLayout(FlowLayout.RIGHT, 5, 5));
			add(pathLabel);
			add(pathToFile);
			addListeners();
		}

		private void addListeners() {
			pathToFile.addTextListener(new TextListener() {

				@Override
				public void textValueChanged(TextEvent e) {

				}
			});

			pathToFile.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {
				}

				@Override
				public void keyReleased(KeyEvent e) {
				}

				@Override
				public void keyPressed(KeyEvent e) {
				}
			});
		}

		public TextField getSaveFiled() {
			return pathToFile;
		}
	}

	private class SavePanelButton extends Panel {

		private Button saveButton = new Button("Save");

		SavePanelButton() {
			super(new FlowLayout(FlowLayout.RIGHT, 5, 5));
			// saveButton.setPreferredSize(new Dimension(210, 35));
			addListners();
			add(saveButton);

		}

		public Button getButton() {
			return saveButton;
		}

		private void addListners() {
			saveButton.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					calcController.saveResult();

				}
			});
		}

	}

}
