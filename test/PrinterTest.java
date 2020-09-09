
class PrinterTest {

public static void main(String... args) {
	new PrinterTest().testAll();
}

//  \\  //  \\  //  \\  //  \\  //  \\  //  \\

void testAll() {
	printNormal();
	printPropertyless();
	printDifferentOrder();
}

void printPropertyless() {
	Models.IniSection section = new Models.IniSection();
	section.name = "Propertyless";	
	String printerSubmission = Printer.toString(section);

	StringBuilder sb = new StringBuilder();
	sb.append("[Propertyless]\n");

	assert sb.toString().equals(printerSubmission);
}

void printNormal() {
	Models.IniSection section = new Models.IniSection();
	section.name = "Normal";
	section.properties.add(new Models.Property("Colour", "Yellow"));
	section.properties.add(new Models.Property("Number", "2"));
	String printerSubmission = Printer.toString(section);

	StringBuilder sb = new StringBuilder();
	sb.append("[Normal]\n");
	sb.append("Colour=Yellow\n");
	sb.append("Number=2\n");
	assert sb.toString().equals(printerSubmission);
}

void printDifferentOrder() {
	Models.IniSection section1 = new Models.IniSection();
	Models.IniSection section2 = new Models.IniSection();
	section1.name = section2.name = "[Coffee]";
	Models.Property property1 = new Models.Property("Colour", "Brown");
	Models.Property property2 = new Models.Property("Taste", "Bitter");	
	section1.properties.add(property1);
	section1.properties.add(property2);
	section2.properties.add(property2);
	section2.properties.add(property1);
		
	// We won't test that !property1.equals(property2),
	// that is not the job of the printer's tests.
	// What we are testing is..
	assert !Printer.toString(section1).equals(Printer.toString(section2));
}

// Should we test for edge cases or assume Printer will never get them ?
// It is an openly usable object so it's rather weird to do the latter.

}
