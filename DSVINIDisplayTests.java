
import java.io.Reader;
import java.io.BufferedReader;
import java.io.StringReader;
import java.io.InputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.LinkedHashMap;


class DSVINIDisplayTests {

//  Interface   //  \\  //  \\  //  \\  //  \\

public void testAll() {
	nullInputs();
	noMapping();
	emptyInput();
	usualInput();
	usualPrint();
}

public void nullInputs() {
	String validRecord = new String("1:blue:carpentry");
	StringReader validReader = new StringReader(validRecord);
	DSVINIDisplay.DSVtoINIMapping validMapping =
		new DSVINIDisplay.DSVtoINIMapping();
	validMapping.keys = new String[] { "ID", "colour", "vocation" };

	try {
		try {
			DSVINIDisplay.fullyTranslate(validReader, null);
			assert false;
		}
		catch (NullPointerException eNp) { assert false; }
		catch (IllegalArgumentException eIa) { }
	
		try {
			DSVINIDisplay.fullyTranslate((BufferedReader)null, validMapping);
			assert false;
		}
		catch (NullPointerException eNp) { assert false; }
		catch (IllegalArgumentException eIa) { }
	
		try {
			DSVINIDisplay.fullyTranslate((Reader)null, validMapping);
			assert false;
		}
		catch (NullPointerException eNp) { assert false; }
		catch (IllegalArgumentException eIa) { }
	
		try {
			DSVINIDisplay.fullyTranslate((InputStream)null, validMapping);
			assert false;
		}
		catch (NullPointerException eNp) { assert false; }
		catch (IllegalArgumentException eIa) { }
	
		try {
			DSVINIDisplay.fullyTranslate((File)null, validMapping);
			assert false;
		}
		catch (NullPointerException eNp) { assert false; }
		catch (IllegalArgumentException eIa) { }
	
		try {
			DSVINIDisplay.fullyTranslate((String)null, validMapping);
			assert false;
		}
		catch (NullPointerException eNp) { assert false; }
		catch (IllegalArgumentException eIa) { }
	
		try {
			DSVINIDisplay.translate(null, validMapping);
			assert false;
		}
		catch (NullPointerException eNp) { assert false; }
		catch (IllegalArgumentException eIa) { }
	
		try {
			DSVINIDisplay.translate(validRecord, null);
			assert false;
		}
		catch (NullPointerException eNp) { assert false; }
		catch (IllegalArgumentException eIa) { }
	
		try {
			DSVINIDisplay.toString(null);
			assert false;
		}
		catch (NullPointerException eNp) { assert false; }
		catch (IllegalArgumentException eIa) { }
	}
	catch (IOException eIo) { eIo.printStackTrace(); assert false; }
}
	
public void noMapping() {
	DSVINIDisplay.DSVtoINIMapping mapping = new DSVINIDisplay.DSVtoINIMapping();
	
	mapping.keys = null;
	try { DSVINIDisplay.translate("longcoat", mapping); assert false; }
	catch (IllegalArgumentException eIa) { }
	
	mapping.keys = new String[0];
	try { DSVINIDisplay.translate("longcoat", mapping); assert false; }
	catch (IllegalArgumentException eIa) { }
}

public void emptyInput() {
	try {
		DSVINIDisplay.DSVtoINIMapping mapping = new
			DSVINIDisplay.DSVtoINIMapping();
		mapping.keys = new String[] { "fruit", "colour", "taste" };
		
		DSVINIDisplay.INISection s1 = DSVINIDisplay.translate("", mapping);
		assert s1.name != null;
		assert s1.name.equals("");
		assert !s1.pairs.containsKey("fruit");
		assert !s1.pairs.containsKey("colour");
		assert s1.pairs.size() == 0;
		
		StringReader r = new StringReader("");
		// Note: StringReader on empty string is not the same as
		// providing empty string to something. StringReader is
		// going to send end-of-stream immediately.
		
		List<DSVINIDisplay.INISection> ls2 = 
			DSVINIDisplay.fullyTranslate(r, mapping);
		assert ls2.isEmpty();
	}
	catch (IOException eIo) { eIo.printStackTrace(); assert false; }
}

public void usualInput() {
	try {
		DSVINIDisplay.DSVtoINIMapping mapping = new
			DSVINIDisplay.DSVtoINIMapping();
		mapping.keys = new String[] { "name", "class", "material" };
		
		DSVINIDisplay.INISection s1 = DSVINIDisplay.translate(
			"tray:cutlery:silver",
			mapping
		);
		
		assert s1 != null;
		assert s1.name != null;
		assert s1.name.equals("tray");
		assert s1.pairs.containsKey("class");
		assert s1.pairs.containsKey("material");
		assert s1.pairs.get("class").equals("cutlery");
		assert s1.pairs.get("material").equals("silver");
		
		String document = 
			"plate:cutlery:ceramic\n"
			+ "kettle:pottery:metal\n"
			+ "candle:decor:wax";
		List<DSVINIDisplay.INISection> ls2 = DSVINIDisplay.fullyTranslate(
			new StringReader(document),
			mapping
		);
		assert ls2 != null;
		assert ls2.size() == 3;
		assert ls2.get(0).name.equals("plate");
		assert ls2.get(1).name.equals("kettle");
		assert ls2.get(2).name.equals("candle");
		assert ls2.get(1).pairs.get("class").equals("pottery");
		assert ls2.get(1).pairs.get("material").equals("metal");
	}
	catch (IOException eIo) { eIo.printStackTrace(); assert false; }
}

public void usualPrint() {
	String target1 =
		"[user]\n"
		+ "name = Kangaroo\n"
		+ "email = kangaroo@ayers.org\n\n";
	String target2 =
		"[credentials]\n"
		+ "username = ReliablySecure\n"
		+ "password = Plaintext?11\n\n";
		
	DSVINIDisplay.INISection manual = new DSVINIDisplay.INISection();
	manual.pairs = new LinkedHashMap<>();
	manual.name = "user";
	manual.pairs.put("name", "Kangaroo");
	manual.pairs.put("email", "kangaroo@ayers.org");
	String result1 = DSVINIDisplay.toString(manual);
	assert result1.equals(target1);
	
	DSVINIDisplay.DSVtoINIMapping mapping = new DSVINIDisplay.DSVtoINIMapping();
	mapping.keys = new String[] { "configGroup", "username", "password" };
	// This mapping makes no sense, but.
	String document = "credentials:ReliablySecure:Plaintext?11";
	DSVINIDisplay.INISection auto = DSVINIDisplay.translate(document, mapping);
	String result2 = DSVINIDisplay.toString(auto);
	assert result2.equals(target2);
}



//  Main    \\  //  \\  //  \\  //  \\  //  \\

public static void main(String... args) {
	DSVINIDisplayTests tests = new DSVINIDisplayTests();
	tests.testAll();
}

}
