import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class HexaUserInterface extends UserInterface implements ActionListener {

	private String hexWord = "";
	private int hexToDezi = 0;
	private JPanel hexButtonPanel;
	private JToggleButton HEX;
	private String[] hexDigits;

	public HexaUserInterface(CalcEngine engine) {
		super(engine);
		addHexInterface();
	}

	private void addHexInterface() {

		hexButtonPanel = new JPanel(new GridLayout(7, 3));
		buttonPanel.add(hexButtonPanel);
		addButton(hexButtonPanel, "F");
		addButton(hexButtonPanel, "E");
		addButton(hexButtonPanel, "D");

		addButton(hexButtonPanel, "C");
		addButton(hexButtonPanel, "B");
		addButton(hexButtonPanel, "A");

		HEX = new JCheckBox("hex", true);
		buttonPanel.add(HEX);
		HEX.addActionListener(this);
		contentPane.add(hexButtonPanel, BorderLayout.WEST);

		frame.pack();

	}

	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		int[] hexNumber = { 10, 11, 12, 13, 14, 15 };
		int result = 0;
		int hex = 0;
		double power = 0;

		if (HEX.isSelected() == true) {

			setPanelEnabled(hexButtonPanel, true);
			int length = hexWord.length();

			if ((command.equals("=") || command.equals("+") || command.equals("-") || command.equals("*"))
					&& length != 0) {

				hexDigits = new String[hexWord.length()];

				int i = 0;

				for (int n = hexWord.length(); n > 0; n--) {
					int end = (length - n) + 1;
					String h = hexWord.substring(i, end);
					hexDigits[i] = h;
					i++;
				}
				for (int a = 0; a < hexDigits.length; a++) {
					if (hexDigits[a].contains("0") || hexDigits[a].contains("1") || hexDigits[a].contains("2")
							|| hexDigits[a].contains("3") || hexDigits[a].contains("4") || hexDigits[a].contains("5")
							|| hexDigits[a].contains("6") || hexDigits[a].contains("7") || hexDigits[a].contains("8")
							|| hexDigits[a].contains("9")) {
						int number = Integer.parseInt(hexDigits[a]);
						length -= 1;
						power = Math.pow(16, (length));
						result = (int) power;
						result *= number;
						hexToDezi += result;
						hexDigits[a] = "0";
						
					}
					if (hexDigits[a].contains("A") || hexDigits[a].contains("B") || hexDigits[a].contains("C")
							|| hexDigits[a].contains("D") || hexDigits[a].contains("E") || hexDigits[a].contains("F")) {
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
						power = Math.pow(16, (length));
						result = (int) power;
						result *= hex;
						hexToDezi += result;
						hexDigits[a] = "0";
					}
				}
				calc.numberPressed(hexToDezi);
				hexToDezi = 0;
				hexWord = "";
			} else if(!(command.equals("=") || command.equals("+") || command.equals("-") || command.equals("*")))
				hexWord = hexWord + command;
		} else {
			setPanelEnabled(hexButtonPanel, false);
			if (command.equals("0") || command.equals("1") || command.equals("2") || command.equals("3")
					|| command.equals("4") || command.equals("5") || command.equals("6") || command.equals("7")
					|| command.equals("8") || command.equals("9")) {
				hexToDezi = Integer.parseInt(command);
				calc.numberPressed(hexToDezi);
			}
		}

		if (command.equals("*"))
			calc.multi();
		else if (command.equals("+"))
			calc.plus();
		else if (command.equals("-"))
			calc.minus();
		else if (command.equals("="))
			calc.equals();
		else if (command.equals("CE")) {
			hexWord = "";
			hexToDezi = 0;
			result = 0;
			calc.clear();
		} else if (command.equals("?"))
			showInfo();

		redisplay();

	}

	public void redisplay() {
		if (HEX.isSelected() == true) {
				String hex = (Integer.toHexString(calc.getDisplayValue())).toUpperCase();
				display.setText("" + hex);
		} else
			display.setText("" + calc.getDisplayValue());
	}

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
