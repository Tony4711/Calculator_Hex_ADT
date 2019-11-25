import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HexUserInterface extends UserInterface implements ActionListener {

	protected String hexWord = "";
	protected int hexToDezi = 0;
	private JPanel hexButtonPanel;
	private JPanel checkBoxPanel;
	protected JToggleButton HEX;
	private String command;
	private HexToDecimal hexToDeci;

	protected HexUserInterface(CalcEngine engine) {
		super(engine);
		addHexInterface();
	}

	private void addHexInterface() {
		checkBoxPanel = new JPanel(new GridLayout(1, 1));
		hexButtonPanel = new JPanel(new GridLayout(6, 3));

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
		hexToDeci = new HexToDeci();
		command = event.getActionCommand();
		int dezi = 0;
		
		if (HEX.isSelected()) {
			setPanelEnabled(hexButtonPanel, true);
			if (command.equals("hex"))
				command = "";
			else {
				if (hexWord.length() == 9)
					calc.maxIntReached();
				else if (!(command.equals("=") || command.equals("+") || command.equals("-") || command.equals("*")
						|| command.equals("+/-"))) {
					hexToDeci.setHexComplete(false);
					hexWord = hexWord + command;
				}
			}
			if (command.equals("=") || command.equals("+") || command.equals("-") || command.equals("*")) {
				hexToDeci.setHexComplete(true);
				dezi = hexToDeci.hexConverter(hexWord, calc.getHasResult());
				hexWord = "";
			}
			if (dezi != 0)
				calc.numberPressed(dezi);
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

	private void checkOperator() {
		if (command.equals("*"))
			calc.multi();
		else if (command.equals("+"))
			calc.plus();
		else if (command.equals("-"))
			calc.minus();
		else if (command.equals("=")) {
			calc.equals();
			hexToDeci.setOldValue(calc.getleftOperand());
		} else if (command.equals("DEL")) {
			hexWord = "";
			hexToDezi = 0;
			calc.clear();
		} else if (command.equals("+/-"))
			calc.negate();
		else if (command.equals("?"))
			showInfo();
	}

	protected void redisplay() {
		if (calc.getErrorChar() == '>') {
			display.setText("Max int value reached");
			calc.setErrorChar('?');
		} else if (calc.getErrorChar() == '!') {
			display.setText("A key sequence error has occurred.");
			calc.setErrorChar('?');
		} else {
			if (HEX.isSelected() == true) {
				if (!hexToDeci.getHexComplete() && hexWord != "") {
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