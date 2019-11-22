import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.ActionEvent;

import org.junit.jupiter.api.Test;

class TestHexInterface {
	private CalcEngine engine;
	private HexaUserInterface hexGui;
	private ActionEvent event;

	@Test
	void testActionPerformed() {

		// test single digit hex input
		engine = new CalcEngine();
		hexGui = new HexaUserInterface(engine);
		event = new ActionEvent(hexGui, 0, "A");
		assertEquals("", hexGui.hexWord);
		hexGui.actionPerformed(event);
		assertEquals("A", hexGui.hexWord);
		hexGui.hexWord="";

		// test multi-digit hex input (only A-F)
		event = new ActionEvent(hexGui, 0, "AA");
		assertEquals("", hexGui.hexWord);
		hexGui.actionPerformed(event);
		assertEquals("AA", hexGui.hexWord);
		hexGui.hexWord="";

		// test multi-digit hex input (A-F,0-9)
		event = new ActionEvent(hexGui, 0, "F2");
		assertEquals("", hexGui.hexWord);
		hexGui.actionPerformed(event);
		assertEquals("F2", hexGui.hexWord);
		hexGui.hexWord="";

		// test multi-digit hex input (0-9,A-F)
		event = new ActionEvent(hexGui, 0, "2F");
		assertEquals("", hexGui.hexWord);
		hexGui.actionPerformed(event);
		assertEquals("2F", hexGui.hexWord);
		hexGui.hexWord="";
	}
	
	@Test
	void testHexConverter() {
		
		// test hex to deci algorithm
		engine = new CalcEngine();
		hexGui = new HexaUserInterface(engine);
		event = new ActionEvent(hexGui, 0, "2F");
		assertEquals("", hexGui.hexWord);
		hexGui.actionPerformed(event);
		assertEquals("2F", hexGui.hexWord);
		event = new ActionEvent(hexGui, 0, "+");
		hexGui.actionPerformed(event);
		int test = hexGui.calc.getDisplayValue();
		assertEquals(47, test);
		hexGui.hexWord = "";
		test = 0;
		
	}

}
