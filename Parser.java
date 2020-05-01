
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;

class Parser {

private Schema schema;
private BufferedReader reader;

//  \\  //  \\  //  \\  //  \\  //  \\  //  \\

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
	if (schema.primaryKeyIndex < 0) return false;
	if (schema.primaryKeyIndex < schema.keys.length) return false;
	return true;
}

//  \\  //  \\  //  \\  //  \\  //  \\  //  \\

public boolean hasNext() {
	// Please unit test this.
	return false;
}

public IniSection next() {
	// Please unit test this.
	return null;
}

//  \\  //  \\  //  \\  //  \\  //  \\  //  \\

static IniSection toIniSection(DsvRecord dsvRecord, Schema schema) {
	// Please unit test this.

	IniSection iniSection = new IniSection();

	for (int i = 0; i < schema.keys.length; ++i) {
		if (i == schema.primaryKeyIndex) {
			iniSection.name = dsvRecord.values[i];
		}
		else {
			iniSection.properties.put(schema.keys[i], dsvRecord.values[i]);
		}
	}
	/*
	Normally I would use 'o' for 'offset' here, but because I 
	called the variable "primaryKeyIndex", I'll use zero-indexing.
	*/

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