
import java.io.PrintStream;
import java.io.IOException;

class DsvIniDisplay {

//  Main    \\  //  \\  //  \\  //  \\  //  \\

public static void main(String... args) {
	if (args.length != 2) {
		printHelp(System.err);
		System.exit(1);
	}

	Schema schema = parseSchema(args);
	if (!Parser.schemaIsValid(schema)) {
		System.err.println("Sorry, one of the arguments given was invalid..");
		System.exit(1);
	}

	try {
		Parser parser = new Parser(schema, System.in);

		while (true) {
			IniSection next = parser.next();
			if (next != null) {
				System.out.println(Printer.toString(next));
			}
			else {
				break;
			}
		}
	}
	catch (IOException eIo) {
		eIo.printStackTrace();
		System.exit(1);
	}
}

static Schema parseSchema(String... args) {
	// Please unit test this.

	Schema schema = new Schema();
	schema.keys = Parser.splitDSVString(args[0]);

	schema.primaryKeyOffset = -1; 
	try {
		schema.primaryKeyOffset = Integer.parseInt(args[1]) - 1;
	}
	catch (NumberFormatException eNf) { }

	return schema;
}

static void printHelp(PrintStream out) {
	out.println(
		"Usage: dsv-ini-display columnNames primaryKeyIndex"
	);
	out.println();
	out.println(
		"Where 'columnNames' is a DSV string of column names (like so: "
	);
	out.println(
		"'column1Name:column2Name:column3Name'), and primaryKeyIndex is"
	);
	out.println(
		"(one-indexed, integer) number saying which of the columns will be"
	);
	out.println(
		"rendered as the INI section's name, rather than as a property."
	);
	out.println();
	out.flush();
}

}