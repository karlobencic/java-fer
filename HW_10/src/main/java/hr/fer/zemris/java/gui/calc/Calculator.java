package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import javax.swing.*;

import hr.fer.zemris.java.gui.layouts.*;

/**
 * The {@code Calculator} class implements Calculator GUI and offers a
 * connection between the GUI and the {@link CalculatorManager}. It uses
 * {@link CalcLayout} to draw the GUI layout and {@link RCPosition} to position
 * the buttons in the layout.
 * 
 * @author Karlo Bencic
 * 
 */
public class Calculator extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The calculator manager. */
	private CalculatorManager manager = new CalculatorManager();

	/** The invert flag. */
	private boolean invert;

	/** The result display. */
	private JLabel display;

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Calculator().setVisible(true);
			}
		});
	}

	/**
	 * Instantiates a new calculator.
	 */
	public Calculator() {
		initGUI();
	}

	/**
	 * Initializess the GUI.
	 */
	private void initGUI() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel(new CalcLayout(10));

		display = new JLabel(manager.getDisplayValue());
		display.setHorizontalAlignment(JTextField.RIGHT);
		display.setBackground(Color.orange);
		display.setOpaque(true);
		panel.add(display, new RCPosition(1, 1));

		drawNumbers(panel);
		drawBinaryOperators(panel);
		drawUnaryOperators(panel);
		drawCalcModifiers(panel);

		getContentPane().add(panel);
		pack();
	}

	/**
	 * Draws calculator modifier buttons such as cls, res, push, pop, inv and
	 * sets action listeners.
	 *
	 * @param panel
	 *            the GUI panel
	 */
	private void drawCalcModifiers(JPanel panel) {
		JButton clr = new JButton("clr");
		clr.addActionListener(e -> action(((JButton) e.getSource()).getText(), CalculatorKey.CLR_RESET));
		panel.add(clr, new RCPosition(1, 7));

		JButton res = new JButton("res");
		res.addActionListener(e -> action(((JButton) e.getSource()).getText(), CalculatorKey.CLR_RESET));
		panel.add(res, new RCPosition(2, 7));

		JButton push = new JButton("push");
		push.addActionListener(e -> action(((JButton) e.getSource()).getText(), CalculatorKey.PUSH_POP));
		panel.add(push, new RCPosition(3, 7));

		JButton pop = new JButton("pop");
		pop.addActionListener(e -> action(((JButton) e.getSource()).getText(), CalculatorKey.PUSH_POP));
		panel.add(pop, new RCPosition(4, 7));

		JCheckBox inv = new JCheckBox("Inv");
		inv.addActionListener(e -> invert = !invert);
		panel.add(inv, new RCPosition(5, 7));
	}

	/**
	 * Draws calculator unary operator buttons and sets action listeners.
	 *
	 * @param panel
	 *            the GUI panel
	 */
	private void drawUnaryOperators(JPanel panel) {
		JButton dot = new JButton(".");
		dot.addActionListener(e -> action(((JButton) e.getSource()).getText(), CalculatorKey.DOT_SIGN));
		panel.add(dot, new RCPosition(5, 5));

		JButton changeSign = new JButton("+/-");
		changeSign.addActionListener(e -> action(((JButton) e.getSource()).getText(), CalculatorKey.DOT_SIGN));
		panel.add(changeSign, new RCPosition(5, 4));

		JButton inverse = new JButton("1/x");
		inverse.addActionListener(new UnaryOperatorAction(value -> 1 / value));
		panel.add(inverse, new RCPosition(2, 1));

		JButton log = new JButton("log");
		log.addActionListener(new UnaryOperatorAction(value -> Math.log10(value), value -> Math.pow(10, value)));
		panel.add(log, new RCPosition(3, 1));

		JButton ln = new JButton("ln");
		ln.addActionListener(new UnaryOperatorAction(value -> Math.log(value), value -> Math.exp(value)));
		panel.add(ln, new RCPosition(4, 1));

		JButton sin = new JButton("sin");
		sin.addActionListener(new UnaryOperatorAction(value -> Math.sin(value), value -> Math.asin(value)));
		panel.add(sin, new RCPosition(2, 2));

		JButton cos = new JButton("cos");
		cos.addActionListener(new UnaryOperatorAction(value -> Math.cos(value), value -> Math.acos(value)));
		panel.add(cos, new RCPosition(3, 2));

		JButton tan = new JButton("tan");
		tan.addActionListener(new UnaryOperatorAction(value -> Math.tan(value), value -> Math.atan(value)));
		panel.add(tan, new RCPosition(4, 2));

		JButton ctg = new JButton("ctg");
		ctg.addActionListener(new UnaryOperatorAction(value -> 1 / Math.tan(value), value -> Math.atan(1 / value)));
		panel.add(ctg, new RCPosition(5, 2));
	}

	/**
	 * Draws calculator binary operator buttons and sets action listeners.
	 *
	 * @param panel
	 *            the GUI panel
	 */
	private void drawBinaryOperators(JPanel panel) {

		JButton eq = new JButton("=");
		eq.addActionListener(new BinaryOperatorAction(null));
		panel.add(eq, new RCPosition(1, 6));

		JButton div = new JButton("/");
		div.addActionListener(new BinaryOperatorAction((first, second) -> first / second));
		panel.add(div, new RCPosition(2, 6));

		JButton mul = new JButton("*");
		mul.addActionListener(new BinaryOperatorAction((first, second) -> first * second));
		panel.add(mul, new RCPosition(3, 6));

		JButton sub = new JButton("-");
		sub.addActionListener(new BinaryOperatorAction((first, second) -> first - second));
		panel.add(sub, new RCPosition(4, 6));

		JButton add = new JButton("+");
		add.addActionListener(new BinaryOperatorAction((first, second) -> first + second));
		panel.add(add, new RCPosition(5, 6));

		JButton xn = new JButton("x^n");
		xn.addActionListener(new BinaryOperatorAction((first, second) -> Math.pow(first, second),
				(first, second) -> Math.pow(first, 1 / second)));
		panel.add(xn, new RCPosition(5, 1));
	}

	/**
	 * Draws calculator number buttons and sets action listeners.
	 *
	 * @param panel
	 *            the GUI panel
	 */
	private void drawNumbers(JPanel panel) {
		JButton bt0 = new JButton("0");
		bt0.addActionListener(e -> action(((JButton) e.getSource()).getText(), CalculatorKey.NUMBER));
		panel.add(bt0, new RCPosition(5, 3));

		for (int num = 1, x = 4, y = 3; num < 10; num++, y++) {
			JButton btn = new JButton(String.valueOf(num));
			btn.addActionListener(e -> action(((JButton) e.getSource()).getText(), CalculatorKey.NUMBER));
			panel.add(btn, new RCPosition(x, y));

			if (y == 5) {
				x--;
				y = 2;
			}
		}
	}

	/**
	 * Calculator key press action.
	 *
	 * @param key
	 *            the key name
	 * @param type
	 *            the key type
	 */
	private void action(String key, CalculatorKey type) {
		manager.keyPressed(null, null, key, type);
		String state = manager.getDisplayValue();
		display.setText(state);
	}

	/**
	 * The {@code UnaryOperatorAction} class represents a unary operator action.
	 * 
	 * @author Karlo Bencic
	 * 
	 */
	private class UnaryOperatorAction implements ActionListener {

		/** The regular operator. */
		private UnaryOperator<Double> regular;

		/** The inverse operator. */
		private UnaryOperator<Double> inverse;

		/**
		 * Instantiates a new unary operator action.
		 *
		 * @param regular
		 *            the regular operator
		 */
		public UnaryOperatorAction(UnaryOperator<Double> regular) {
			this.regular = regular;
		}

		/**
		 * Instantiates a new unary operator action.
		 *
		 * @param regular
		 *            the regular operator
		 * @param inverse
		 *            the inverse operator
		 */
		public UnaryOperatorAction(UnaryOperator<Double> regular, UnaryOperator<Double> inverse) {
			this.regular = regular;
			this.inverse = inverse;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String key = ((JButton) e.getSource()).getText();

			manager.keyPressed(null, invert ? inverse : regular, key, CalculatorKey.UNARY);
			String state = manager.getDisplayValue();
			display.setText(state);
		}
	}

	/**
	 * The {@code BinaryOperatorAction} class represents a binary operator
	 * action.
	 * 
	 * @author Karlo Bencic
	 * 
	 */
	private class BinaryOperatorAction implements ActionListener {

		/** The regular operator. */
		private BinaryOperator<Double> regular;

		/** The reverse operator. */
		private BinaryOperator<Double> reverse;

		/**
		 * Instantiates a new binary operator action.
		 *
		 * @param regular
		 *            the regular operator
		 */
		public BinaryOperatorAction(BinaryOperator<Double> regular) {
			this.regular = regular;
		}

		/**
		 * Instantiates a new binary operator action.
		 *
		 * @param regular
		 *            the regular operator
		 * @param reverse
		 *            the reverse operator
		 */
		public BinaryOperatorAction(BinaryOperator<Double> regular, BinaryOperator<Double> reverse) {
			this.regular = regular;
			this.reverse = reverse;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String key = ((JButton) e.getSource()).getText();

			manager.keyPressed(invert ? reverse : regular, null, key, CalculatorKey.BINARY);
			String state = manager.getDisplayValue();
			display.setText(state);
		}
	}
}
