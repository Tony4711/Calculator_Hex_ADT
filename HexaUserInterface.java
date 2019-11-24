import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HexaUserInterface extends UserInterface implements ActionListener {

	protected String hexWord = "";
	protected int hexToDezi = 0;
	private JPanel hexButtonPanel;
	private JPanel checkBoxPanel;
	private JToggleButton HEX;
	private String[] hexDigits;
	private String command;
	private int[] hexNumber = { 10, 11, 12, 13, 14, 15 };
	private int hex = 0;
	private int a;
	private int length = 0;
	private int oldValue;
	private boolean hexComplete;

	protected HexaUserInterface(CalcEngine engine) {
		super(engine);
		addHexInterface();
	}

	private void addHexInterface() {
		checkBoxPanel = new JPanel(new GridLayout(1, 1));
		hexButtonPanel = new JPanel(new GridLayout(6, 3));
		buttonPanel.add(hexButtonPanel);

		addButton(hexButtonPanel, "F");
		addButton(hexButtonPanel, "E");
		addButton(hexButtonPanel, "D");
		addButton(hexButtonPanel, "C");
		addButton(hexButtonPanel, "B");
		addButton(hexButtonPanel, "A");

		HEX = new JCheckBox("hex", true);
		checkBoxPanel.add(new JLabel(" "));
		checkBoxPanel.add(new JLabel(" "));
		checkBoxPanel.add(new JLabel(" "));
		checkBoxPanel.add(new JLabel(" "));
		checkBoxPanel.add(HEX);
		HEX.addActionListener(this);
		contentPane.add(hexButtonPanel, BorderLayout.WEST);
		contentPane.add(checkBoxPanel, BorderLayout.SOUTH);

		frame.pack();
	}

	public void actionPerformed(ActionEvent event) {
		command = event.getActionCommand();

		if (HEX.isSelected()) {
			setPanelEnabled(hexButtonPanel, true);
			hexConverter();
		} else {
			setPanelEnabled(hexButtonPanel, false);
			calculateDeci();
		}
		checkOperator();
		redisplay();
	}

	private void calculateDeci() {
		if (command.equals("0") || command.equals("1") || command.equals("2") || command.equals("3")
				|| command.equals("4") || command.equals("5") || command.equals("6") || command.equals("7")
				|| command.equals("8") || command.equals("9")) {
			hexToDezi = Integer.parseInt(command);
			calc.numberPressed(hexToDezi);
			hexToDezi = 0;
		}
	}

	private void hexConverter() {
		length = hexWord.length();
		if ((command.equals("=") || command.equals("+") || command.equals("-") || command.equals("*"))) {
			hexDigits = new String[length];
			hexComplete = true;

			int i = 0;
			for (int n = length; n > 0; n--) {
				int end = (length - n) + 1;
				String h = hexWord.substring(i, end);
				hexDigits[i] = h;
				i++;
			}
			for (a = 0; a < hexDigits.length; a++) {
				digitHexToDeci();
				stringHexToDeci();
			}
			if (hexToDezi == 0 && calc.getHasResult())
				hexToDezi = oldValue;
			if (!(hexToDezi == 0))
				calc.numberPressed(hexToDezi);
			hexToDezi = 0;
			hexWord = "";
		} else {
			if (command.equals("hex") || command.equals("+/-"))
				hexWord = "";
			else {
				if (hexWord.length() == 9)
					calc.maxIntReached();
				else {
					hexComplete = false;
					hexWord = hexWord + command;
				}
			}
		}
	}

	private void digitHexToDeci() {
		if (hexDigits[a].equals("0") || hexDigits[a].equals("1") || hexDigits[a].equals("2") || hexDigits[a].equals("3")
				|| hexDigits[a].equals("4") || hexDigits[a].equals("5") || hexDigits[a].equals("6")
				|| hexDigits[a].equals("7") || hexDigits[a].equals("8") || hexDigits[a].equals("9")) {
			int number = Integer.parseInt(hexDigits[a]);
			length -= 1;
			hexToDezi = (int) ((Math.pow(16, (length))) * number) + hexToDezi;
			hexDigits[a] = "0";

		}
	}

	protected void stringHexToDeci() {
		if (hexDigits[a].equals("A") || hexDigits[a].equals("B") || hexDigits[a].equals("C") || hexDigits[a].equals("D")
				|| hexDigits[a].equals("E") || hexDigits[a].equals("F")) {
			switch (hexDigits[a]) {
			case "A":
				hex = hexNumber[0];
				break;
			case "B":
				hex = hexNumber[1];
				break;
			case "C":
				hex = hexNumber[2];
				break;
			case "D":
				hex = hexNumber[3];
				break;
			case "E":
				hex = hexNumber[4];
				break;
			case "F":
				hex = hexNumber[5];
				break;
			}
			length -= 1;
			hexToDezi = (int) ((Math.pow(16, (length))) * hex) + hexToDezi;
			hexDigits[a] = "0";

		}
	}
	
	private void checkOperator() {
		if (command.equals("*"))
			calc.multi();
		else if (command.equals("+"))
			calc.plus();
		else if (command.equals("-"))
			calc.minus();
		else if (command.equals("=")) {
			calc.equals();
			oldValue = calc.getleftOperand();
		} else if (command.equals("DEL")) {
			hexWord = "";
			hexToDezi = 0;
			calc.clear();
		} else if (command.equals("?"))
			showInfo();
		else if (command.equals("+/-")) 
			calc.negate();
	}

	protected void redisplay() {
		if (calc.getErrorChar() == '~') {
			display.setText("Max int value reached");
			calc.setErrorChar('?');
		} else if (calc.getErrorChar() == '!') {
			display.setText("A key sequence error has occurred.");
			calc.setErrorChar('?');
		} else {
			if (HEX.isSelected() == true) {
				if (!hexComplete) {
					display.setText(hexWord);
				} else {
					String hex = (Integer.toHexString(calc.getDisplayValue())).toUpperCase();
					display.setText("" + hex);
				}
			} else
				display.setText("" + calc.getDisplayValue());
		}
	}

	/*
	 * From Stack Overflow
	 * https://stackoverflow.com/questions/10985734/java-swing-enabling-disabling-
	 * all-components-in-jpanel
	 * 
	 * @author Kesavamoorthi
	 */
	void setPanelEnabled(JPanel panel, Boolean isEnabled) {
		panel.setEnabled(isEnabled);

		Component[] components = panel.getComponents();

		for (int i = 0; i < components.length; i++) {
			if (components[i].getClass().getName() == "javax.swing.JPanel") {
				setPanelEnabled((JPanel) components[i], isEnabled);
			}
			components[i].setEnabled(isEnabled);
		}
	}
}