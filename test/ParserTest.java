
class ParserTest {

public static void main(String... args) {
	new ParserTest().testAll();	
}

//  \\  //  \\  //  \\  //  \\  //  \\  //  \\

void testAll() {
	testSplitDSVString();
	testToIniSection();
}

void testSplitDSVString() {
	String[] values1 = Parser.splitDSVString("");
	assert values1.length == 1;

	String[] values2 = Parser.splitDSVString("11\\:12:6:6:2020");
	assert values2.length == 4;
	assert values2[0].equals("11\\:12");
	assert values2[1].equals("6");
	assert values2[2].equals("6");
	assert values2[3].equals("2020");

	String[] values3 = 
		Parser.splitDSVString("Selangor Darul Ehsan:Shah Alam:Klang");
	assert values3.length == 3;
	assert values3[0].equals("Selangor Darul Ehsan");
	assert values3[1].equals("Shah Alam");
	assert values3[2].equals("Klang");

	String[] values4 = Parser.splitDSVString("nonEmptyValue:");
	assert values4.length == 1;
	assert values4[0].equals("nonEmptyValue");
	// I wrote a comment about this in Parser#splitDSVString.
	// Let's accept this as correct and expected behaviour.
}

void testToIniSection() {
	// Let's go with a schema for Malaysian states.
	String[] keys = 
		new String[] { "Name", "Civil Capital", "Royal Capital" };
	int sectionNameKeyOffset = 0;
	
	String perlisLine = "Perlis Indera Kayangan:Kangar:Arau";
	String sabahLine = "Sabah:Kota Kinabalu:";
	String[] perlisValues = Parser.splitDSVString(perlisLine);
	String[] sabahValues = Parser.splitDSVString(sabahLine);

	// Perlis first, since it's the normal case.
	Models.IniSection perlis = 
		Parser.toIniSection(perlisValues, keys, sectionNameKeyOffset);
	assert perlis.name.equals("Perlis Indera Kayangan");
	assert perlis.properties.size() == 2;
	assert perlis.properties.get(0).key.equals("Civil Capital");
	assert perlis.properties.get(0).value.equals("Kangar");
	assert perlis.properties.get(1).key.equals("Royal Capital");
	assert perlis.properties.get(1).value.equals("Arau");

	Models.IniSection sabah = 
		Parser.toIniSection(sabahValues, keys, sectionNameKeyOffset);
	assert sabah.name.equals("Sabah"); 
	assert sabah.properties.size() == 1;
	assert sabah.properties.get(0).key.equals("Civil Capital");
	assert sabah.properties.get(0).value.equals("Kota Kinabalu");
}

}
