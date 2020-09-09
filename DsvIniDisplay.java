
import java.io.PrintStream;
import java.io.IOException;

class DsvIniDisplay {

//  Main    \\  //  \\  //  \\  //  \\  //  \\

public static void main(String... args) {
	if (args.length != 2) {
		printHelp(System.err);
		System.exit(1);
	}

	boolean error = false;
	String[] keys = Parser.splitDSVString(args[0]);
	int sectionNameKeyOffset = -1;
	try {
		sectionNameKeyOffset = Integer.parseInt(args[1]) - 1;
	}
	catch (NumberFormatException eNf) { error = true; }
	
	
	if (error) {
		System.err.println("Sorry, one of the arguments was invalid..");
		// Not exactly a good error message..
		System.exit(1);
	}
	else try {
		Parser parser = new Parser(System.in);

		while (true) {
			Models.IniSection anotherSection = 
				parser.next(keys, sectionNameKeyOffset);
			if (anotherSection == null) break;			
			System.out.println(Printer.toString(anotherSection));
		}
	}
	catch (IOException eIo) {
		eIo.printStackTrace();
		System.exit(2);
	}
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
