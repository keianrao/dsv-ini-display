
class PrinterTest {

public static void main(String... args) {
	new PrinterTest().testAll();
}

//  \\  //  \\  //  \\  //  \\  //  \\  //  \\

void testAll() {
	printNormal();
	printPropertyless();
}

void printPropertyless() {
	IniSection section = new IniSection();
	section.name = "Propertyless";	
	String printerSubmission = Printer.toString(section);

	StringBuilder sb = new StringBuilder();
	sb.append("[Propertyless]\n");

	assert sb.toString().equals(printerSubmission);
}

void printNormal() {
	IniSection section = new IniSection();
	section.name = "Normal";
	section.properties.add(new Property("Colour", "Yellow"));
	section.properties.add(new Property("Number", "2"));
	String printerSubmission = Printer.toString(section);

	StringBuilder sb = new StringBuilder();
	sb.append("[Normal]\n");
	sb.append("Colour=Yellow\n");
	sb.append("Number=2\n");
	assert sb.toString().equals(printerSubmission);
}

// Should we test for edge cases or assume Printer will never get them ?
// It is an openly usable object so it's rather weird to do the latter.

}