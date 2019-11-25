import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.ActionEvent;

import org.junit.jupiter.api.Test;

class TestHexInterface {
	private CalcEngine engine;
	private HexUserInterface hexGui;
	private ActionEvent event;
	private HexToDeci converter;

	@Test
	void testActionPerformed() {

		// Input "A" as event
		engine = new CalcEngine();
		hexGui = new HexUserInterface(engine);
		event = new ActionEvent(hexGui, 0, "A");
		assertEquals("", hexGui.hexWord);
		hexGui.actionPerformed(event);
		assertEquals("A", hexGui.hexWord);
		hexGui.hexWord = "";
	}

	@Test
	void testAddition() {

		// Input "2E", "+" and "F2C" as event
		engine = new CalcEngine();
		hexGui = new HexUserInterface(engine);
		event = new ActionEvent(hexGui, 0, "2E");
		assertEquals("", hexGui.hexWord);
		hexGui.actionPerformed(event);
		assertEquals("2E", hexGui.hexWord);
		event = new ActionEvent(hexGui, 0, "+");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "F2C");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "=");
		hexGui.actionPerformed(event);
		assertEquals(3930, hexGui.calc.getDisplayValue());

		// Input "+/-" as event
		event = new ActionEvent(hexGui, 0, "+/-");
		hexGui.actionPerformed(event);
		assertEquals(-3930, hexGui.calc.getDisplayValue());

		// Calculate with value from previous calculation
		event = new ActionEvent(hexGui, 0, "+");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "20BC");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "=");
		hexGui.actionPerformed(event);
		assertEquals(4450, hexGui.calc.getDisplayValue());

	}
	
	@Test
	void testSubstraction() {

		// Input "2E", "-" and "F2C" as event
		engine = new CalcEngine();
		hexGui = new HexUserInterface(engine);
		event = new ActionEvent(hexGui, 0, "2E");
		assertEquals("", hexGui.hexWord);
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "-");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "F2C");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "=");
		hexGui.actionPerformed(event);
		assertEquals(-3838, hexGui.calc.getDisplayValue());

		// Calculate with value from previous calculation
		event = new ActionEvent(hexGui, 0, "-");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "20BC");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "=");
		hexGui.actionPerformed(event);
		assertEquals(-12218, hexGui.calc.getDisplayValue());

	}
	
	@Test
	void testMultiplication() {

		// Input "2E", "*" and "F2C" as event
		engine = new CalcEngine();
		hexGui = new HexUserInterface(engine);
		event = new ActionEvent(hexGui, 0, "2E");
		assertEquals("", hexGui.hexWord);
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "*");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "F2C");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "=");
		hexGui.actionPerformed(event);
		assertEquals(178664, hexGui.calc.getDisplayValue());

		// Calculate with value from previous calculation
		event = new ActionEvent(hexGui, 0, "*");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "2C");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "=");
		hexGui.actionPerformed(event);
		assertEquals(7861216, hexGui.calc.getDisplayValue());

	}

	@Test
	void testSingleDigitInput() {

		// test single digit hex input
		converter = new HexToDeci();
		assertEquals(15, converter.hexConverter("F", false));
	}

	@Test
	void testMultiDigitStringInput() {

		// test multi-digit hex input (only A-F)
		converter = new HexToDeci();
		assertEquals(254, converter.hexConverter("FE", false));
	}

	@Test
	void testMultiDigitStringNumberInput() {

		// test multi-digit hex input (A-F,0-9)
		converter = new HexToDeci();
		assertEquals(242, converter.hexConverter("F2", true));
	}

	@Test
	void testMultiDigitNumberStringInput() {

		// test multi-digit hex input (0-9,A-F)
		converter = new HexToDeci();
		assertEquals(47, converter.hexConverter("2F", true));
	}

	@Test
	void testCheckBox() {

		engine = new CalcEngine();
		hexGui = new HexUserInterface(engine);
		event = new ActionEvent(hexGui, 0, "hex");
		boolean selected = hexGui.HEX.isSelected();
		hexGui.HEX.setSelected(false);
		assertEquals(!selected, hexGui.HEX.isSelected());
	}

	@Test
	void testExceptions() {
		long value = 2147483648L;

		// Calculation would cause an overflow
		engine = new CalcEngine();
		hexGui = new HexUserInterface(engine);
		event = new ActionEvent(hexGui, 0, "7FFFFFFF");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "+");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "1");
		hexGui.actionPerformed(event);
		event = new ActionEvent(hexGui, 0, "=");
		assertFalse(value == hexGui.calc.getDisplayValue());
	}

}
