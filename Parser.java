
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;

class Parser {

//  Members \\  //  \\  //  \\  //  \\  //  \\

private Schema schema;
private BufferedReader reader;



//  Constructors    \\  //  \\  //  \\  //  \\

Parser(Schema schema, InputStream stream) 
throws IllegalArgumentException, IOException {
	if (!schemaIsValid(schema)) {
		throw new IllegalArgumentException("Schema is invalid!");
	}
	this.schema = schema;
	this.reader = new BufferedReader(new InputStreamReader(stream));
}

static boolean schemaIsValid(Schema schema) {
	if (schema == null) return false;
	if (schema.keys == null) return false;
	if (schema.primaryKeyOffset < 0) return false;
	if (schema.primaryKeyOffset >= schema.keys.length) return false;
	return true;
}



//  Interface   //  \\  //  \\  //  \\  //  \\

public IniSection next() {
	// Please unit test this.	
	String string;
	try {
		string = reader.readLine();
	}
	catch (IOException eIo) {
		return null;
	}
	if (string == null) {
		return null;
	}

	String[] values = splitDSVString(string);
	IniSection section = toIniSection(values, schema);
	return section;
}



//  Helpers \\  //  \\  //  \\  //  \\  //  \\

static IniSection toIniSection(String[] values, Schema schema) {
	IniSection iniSection = new IniSection();

	for (int o = 0; o < values.length; ++o) {
		if (o == schema.primaryKeyOffset) {
			iniSection.name = values[o];
		}
		else {
			iniSection.properties
				.add(new Property(schema.keys[o], values[o]));
		}
	}

	return iniSection;
}


static final String LOOK_BEHIND_BACKSLASH_ESCAPE = "(?<!\\\\)";
static final String DELIMITER = ":";
static final String SPLITTER_REGEX = LOOK_BEHIND_BACKSLASH_ESCAPE + DELIMITER;

static String[] splitDSVString(String string) {
	return string.split(SPLITTER_REGEX);
	/*
	* Interesting behaviour about java.lang.String#split here:
	* If the string ends in the delimiter but there's no characters past it, 
	* there won't be a trailing field. So 'string:' -> length == 1.
	* The Javadoc mentions this, but *very* briefly..
	*/
}

}