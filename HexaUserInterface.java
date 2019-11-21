import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;

public class HexaUserInterface extends UserInterface implements ActionListener {

	private String hexWord = "";
	private int hexToDezi = 0;

	public HexaUserInterface(CalcEngine engine) {
		super(engine);
		addHexInterface();
	}

	private void addHexInterface() {

		addButton(buttonPanel, "F");
		addButton(buttonPanel, "E");
		addButton(buttonPanel, "D");
		buttonPanel.add(new JLabel(" "));

		addButton(buttonPanel, "C");
		addButton(buttonPanel, "B");
		addButton(buttonPanel, "A");
	}

	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		int[] hexNumber = { 10, 11, 12, 13, 14, 15 };
		int result = 0;
		int hex = 0;
		double power = 0;

		if (command.equals("=") || command.equals("+") || command.equals("-") || command.equals("*")) {

			for (int n = hexWord.length(); n > 0; n--) {
				String h = hexWord.substring(hexWord.length() - n);

				if (h.contains("0") || h.contains("1") || h.contains("2") || h.contains("3") || h.contains("4")
						|| h.contains("5") || h.contains("6") || h.contains("7") || h.contains("8")
						|| h.contains("9")) {
					String statement = h.substring((h.length() - n), ((h.length() - n) + 1));
					int number = Integer.parseInt(statement);
					power = Math.pow(16, (n - 1));
					result = (int) power;
					result *= number;
					hexToDezi += result;
					calc.numberPressed(hexToDezi);

				}
				if (h.contains("A") || h.contains("B") || h.contains("C") || h.contains("D") || h.contains("E")
						|| h.contains("F")) {
					String statement = h.substring((h.length() - n), ((h.length() - n) + 1));
					switch (statement) {
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

					power = Math.pow(16, (n - 1));
					result = (int) power;
					result *= hex;
					hexToDezi += result;
					calc.numberPressed(hexToDezi);
				}
			}
			hexWord = "";
		} 
		else hexWord = hexWord + command;

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
		if (calc.getDisplayValue() >= 0) {
			String hex = (Integer.toHexString(hexToDezi)).toUpperCase();
			display.setText("" + hex);
		} else
			display.setText("" + calc.getDisplayValue());
	}
}
