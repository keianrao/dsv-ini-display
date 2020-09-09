
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;

class Parser {

//  Members \\  //  \\  //  \\  //  \\  //  \\

private BufferedReader reader;



//  Constructors    \\  //  \\  //  \\  //  \\

Parser(InputStream stream) throws IllegalArgumentException, IOException {
	this.reader = new BufferedReader(new InputStreamReader(stream));
	// Should we just switch to completely static functions?
}



//  Interface   //  \\  //  \\  //  \\  //  \\

public Models.IniSection next(String[] keys, int sectionNameKeyOffset) throws IOException {
	// Please unit test this.	
	if (sectionNameKeyOffset < 0 || sectionNameKeyOffset >= keys.length) {
		throw new IllegalArgumentException(
			"Section name key offset has to be " +
			"a valid offset inside the keys array!"
		);
	}
	
	while (true) {
		String line = reader.readLine();
		
		if (line == null) {
			return null;
		}
		
		if (line.startsWith("//") || line.startsWith("#")) {
			continue;
		}		
		if (line.trim().isEmpty()) {
			continue;
		}
		
		String[] values = splitDSVString(line);		
		if (values.length != keys.length) {
			// "Invalid line" - ignore it
			// Spec actually says we should log this somewhere.
			// Where should we?
			continue;
		}
		
		return toIniSection(values, keys, sectionNameKeyOffset);
	}
}



public static String[] splitDSVString(String string) {
	final String LOOK_BEHIND_BACKSLASH_ESCAPE = "(?<!\\\\)";
	final String DELIMITER = ":";
	final String SPLITTER_REGEX = LOOK_BEHIND_BACKSLASH_ESCAPE + DELIMITER;

	return string.split(SPLITTER_REGEX);
	/*
	* Interesting behaviour about java.lang.String#split here:
	* If the string ends in the delimiter but there are 
	* no characters past it, there won't be a trailing field. 
	* So 'string:' -> length == 1.
	* The Javadoc mentions this, but only very briefly..
	*/
}



//  Helper functions    //  \\  //  \\  //  \\

public static Models.IniSection toIniSection(
		String[] values,
		String[] keys, int sectionNameKeyOffset) 
{
	// This function is public only for testability.
	// Surely that is incorrect..?
	assert values != null; assert keys != null;
	
	Models.IniSection iniSection = new Models.IniSection();

	for (int o = 0; o < Math.min(values.length, keys.length); ++o) {
		if (o == sectionNameKeyOffset) {
			iniSection.name = values[o];
		}
		else {
			Models.Property p = new Models.Property(keys[o], values[o]);
			iniSection.properties.add(p);
		}
	}

	return iniSection;
}

}
