
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

public boolean hasNext() {
	// Please unit test this.
	return false;
}

public IniSection next() {
	// Please unit test this.
	return null;
}



//  Helpers \\  //  \\  //  \\  //  \\  //  \\

static IniSection toIniSection(DsvRecord dsvRecord, Schema schema) {
	// Please unit test this.

	IniSection iniSection = new IniSection();

	for (int o = 0; o < schema.keys.length; ++o) {
		if (o == schema.primaryKeyOffset) {
			iniSection.name = dsvRecord.values[o];
		}
		else {
			Property newProperty = new Property();
			newProperty.key = schema.keys[o];
			newProperty.value = dsvRecord.values[o];
			iniSection.properties.add(newProperty);
		}
	}

	return iniSection;
}


static final String LOOK_BEHIND_BACKSLASH_ESCAPE = "(?<!\\\\)";
static final String DELIMITER = ":";
static final String SPLITTER_REGEX = LOOK_BEHIND_BACKSLASH_ESCAPE + DELIMITER;

static String[] splitDSVString(String string) {
	// Please unit test this.
	return string.split(SPLITTER_REGEX);
}

}