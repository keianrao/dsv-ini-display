
import java.io.IOException;

class DsvIniDisplay {

public static void main(String... args) {
	Schema schema = parseSchema(args);
	assert Parser.schemaIsValid(schema);
	try {
		Parser parser = new Parser(schema, System.in);
		while (parser.hasNext()) {
			System.out.println(Printer.toString(parser.next()));
		}
	}
	catch (IOException eIo) {
		eIo.printStackTrace();
		System.exit(1);
	}
}

//  \\  //  \\  //  \\  //  \\  //  \\  //  \\

static Schema parseSchema(String... args) {
	// Please unit test this.
	
	/*
	if (primaryKeyIndex < 0 || primaryKeyIndex > keys.length)
		throw new IndexOutOfBoundsException
			("Index must be that of a key!");
	// Not a very helpful message but..

	this.keys = keys;
	this.primaryKeyIndex = primaryKeyIndex;
	*/
	return null;
}

}