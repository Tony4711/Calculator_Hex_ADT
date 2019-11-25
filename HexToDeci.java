
public class HexToDeci implements HexToDecimal {

	private int[] hexNumber = { 10, 11, 12, 13, 14, 15 };
	private int hex = 0;
	private int a;
	private int length = 0;
	private int oldValue;
	private boolean isHexComplete;
	private String[] hexDigits;
	protected String hexWord = "";
	protected int hexToDezi = 0;

	public int hexConverter(String hexWord, boolean hasResult) {
		length = hexWord.length();
		hexDigits = new String[length];

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
		if (hexToDezi == 0 && hasResult)
			hexToDezi = oldValue;

		int returnValue = hexToDezi;

		return returnValue;
	}

	public void digitHexToDeci() {
		if (hexDigits[a].equals("0") || hexDigits[a].equals("1") || hexDigits[a].equals("2") || hexDigits[a].equals("3")
				|| hexDigits[a].equals("4") || hexDigits[a].equals("5") || hexDigits[a].equals("6")
				|| hexDigits[a].equals("7") || hexDigits[a].equals("8") || hexDigits[a].equals("9")) {
			int number = Integer.parseInt(hexDigits[a]);
			length -= 1;
			hexToDezi = (int) ((Math.pow(16, (length))) * number) + hexToDezi;
			hexDigits[a] = "0";

		}
	}

	public void stringHexToDeci() {
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

	public boolean getHexComplete() {
		return isHexComplete;
	}
	
	public void setHexComplete(boolean complete) {
		isHexComplete = complete;
	}

	public String getHexWord() {
		return hexWord;
	}
	
	public void setOldValue(int value) {
		oldValue = value;
	}

}
