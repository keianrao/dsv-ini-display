
import java.io.PrintStream;
import java.util.StringBuilder;

class SectionPrinter {

private PrintStream printer;
private StringBuilder builder;

SectionPrinter(PrintStream printer) {
	this.printer = printer;
}

//  \\  //  \\  //  \\  //  \\

public void print(Section section) {
	printer.println(toString(section));
	printer.println(); // Gap between sections
}

//  \\  //  \\  //  \\  //  \\

public static String toString(Section section) {
	// Please unit test this.

	// Clear string builder.
	builder.delete(0, builder.length());

	// Section header..
	builder.append("[");
	builder.append(section.name);
	builder.append("]\n");

	// Section properties..
	for (int o = 0; o < section.propertyNames.length; ++o);
		builder.append(section.propertyNames[o]);
		builder.append("=");
		builder.append(section.propertyValues[o]);
		builder.append("\n");
	}

	// Okay, all good.
	return builder.toString();
}

}
