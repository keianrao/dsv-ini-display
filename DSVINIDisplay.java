
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class DSVINIDisplay {

//  Helper classes  \\  //  \\  //  \\  //  \\

public static class INISection {
	String name;
	Map<String, String> mappings;
}

public static class DSVtoINIMapping {
	String[] keys;
	String sectionNameKey;
}



//  Interface   //  \\  //  \\  //  \\  //  \\

public static List<INISection> fullyTranslate(
		BufferedReader reader, DSVtoINIMapping keyMapping) throws IOException {
	// We might not want to use BufferedReader#readLine, instead to use a normal reader
	// to handle newline escapes. Though, an application using INI is probably not
	// going to need such sophistication..
	
	List<INISection> sections = new ArrayList<>();
	
	String line;
	while ((line = reader.readLine()) != null) {
		sections.add(translate(line, keyMapping));
	}
	
	return sections;
}

public static List<INISection> fullyTranslate(
		Reader reader, DSVtoINIMapping keyMapping) throws IOException {
	return fullyTranslate(new BufferedReader(reader), keyMapping);
}

public static List<INISection> fullyTranslate(
		InputStream inputStream, DSVtoINIMapping keyMapping) throws IOException {
	return fullyTranslate(new InputStreamReader(inputStream), keyMapping);
}

public static List<INISection> fullyTranslate(
		File file, DSVtoINIMapping keyMapping) throws IOException {
	return fullyTranslate(new FileReader(file), keyMapping);
}

public static List<INISection> fullyTranslate(
		String filepath, DSVtoINIMapping keyMapping) throws IOException {
	return fullyTranslate(new File(filepath), keyMapping);
}



public static INISection translate(String dsv, DSVtoINIMapping keyMapping) {
	if (dsv == null)
		throw new IllegalArgumentException
			("Can't translate null string!");
	
	boolean noKeyMapping =
		keyMapping == null
		|| keyMapping.keys == null
		|| keyMapping.keys.length == 0;
	if (noKeyMapping)
		throw new IllegalArgumentException
			("Cannot translate without mappings!");

	String[] dsvFields = dsv.split(":");
	// Will switch to a more sophisticated tokeniser later.
	
	if (keyMapping.keys.length < dsvFields.length)
		throw new IllegalArgumentException
			("Too many fields, no key corresponding to some!");
			
	if (keyMapping.sectionNameKey == null)
		keyMapping.sectionNameKey = keyMapping.keys[0];
	
	INISection iniSection = new INISection();
	iniSection.mappings = new HashMap<>();
	
	for (int o = 0; o < dsvFields.length; ++o) {
		String key = keyMapping.keys[o];
		String value = dsvFields[o];
		
		if (key.equals(keyMapping.sectionNameKey)) {
			iniSection.name = value;
		}
		else {
			iniSection.mappings.put(key, value);
		}
	}
	
	return iniSection;
}

public static String toString(INISection iniSection) {
	StringBuilder builder = new StringBuilder();
	builder.append("[");
	builder.append(iniSection.name);
	builder.append("]");
	for (Map.Entry<String, String> mapping: iniSection.mappings.entrySet()) {
		builder.append(mapping.getKey());
		builder.append(" = ");
		builder.append(mapping.getValue());
	}
	return builder.toString();
}



//  Main    \\  //  \\  //  \\  //  \\  //  \\

public static void main(String... args) {
	
}

}

//  \\  //  \\  //  \\  //  \\  //  \\  //  \\



//    \ |
