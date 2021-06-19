/*
* DSVINIDisplay - inane data structure transformation
* (C) 2021 Keian Rao <keian.rao@gmail.com>
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/


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
import java.io.PrintStream;


public class DSVINIDisplay {

//  Helper classes  \\  //  \\  //  \\  //  \\

public static class INISection {
	String name;
	Map<String, String> pairs;
}

public static class DSVtoINIMapping {
	String[] keys;
	String sectionNameKey;
}



//  Interface   //  \\  //  \\  //  \\  //  \\

public static List<INISection> fullyTranslate(
		BufferedReader reader, DSVtoINIMapping mapping) throws IOException {
	// We might not want to use BufferedReader#readLine, instead to use
	// a normal Reader to handle newline escapes. Though, an application
	// using INI is probably not going to need such sophistication..
	
	List<INISection> sections = new ArrayList<>();
	
	String line;
	while ((line = reader.readLine()) != null) {
		sections.add(translate(line, mapping));
	}
	
	return sections;
}

public static List<INISection> fullyTranslate(
		Reader reader, DSVtoINIMapping mapping) throws IOException {
	return fullyTranslate(new BufferedReader(reader), mapping);
}

public static List<INISection> fullyTranslate(
		InputStream inputStream, DSVtoINIMapping mapping) throws IOException {
	return fullyTranslate(new InputStreamReader(inputStream), mapping);
}

public static List<INISection> fullyTranslate(
		File file, DSVtoINIMapping mapping) throws IOException {
	return fullyTranslate(new FileReader(file), mapping);
}

public static List<INISection> fullyTranslate(
		String filepath, DSVtoINIMapping mapping) throws IOException {
	return fullyTranslate(new File(filepath), mapping);
}



public static INISection translate(String dsv, DSVtoINIMapping mapping) {
	if (dsv == null)
		throw new IllegalArgumentException
			("Can't translate null string!");
	
	boolean nomapping =
		mapping == null
		|| mapping.keys == null
		|| mapping.keys.length == 0;
	if (nomapping)
		throw new IllegalArgumentException
			("Cannot translate without mappings!");

	String[] dsvFields = dsv.split(":");
	// Will switch to a more sophisticated tokeniser later.
	
	if (mapping.keys.length < dsvFields.length)
		throw new IllegalArgumentException
			("Too many fields, no key corresponding to some!");
			
	if (mapping.sectionNameKey == null)
		mapping.sectionNameKey = mapping.keys[0];
	
	INISection iniSection = new INISection();
	iniSection.pairs = new HashMap<>();
	
	for (int o = 0; o < dsvFields.length; ++o) {
		String key = mapping.keys[o];
		String value = dsvFields[o];
		
		if (key.equals(mapping.sectionNameKey)) {
			iniSection.name = value;
		}
		else {
			iniSection.pairs.put(key, value);
		}
	}
	
	return iniSection;
}



public static String toString(INISection iniSection) {
	StringBuilder builder = new StringBuilder();
	builder.append("[");
	builder.append(iniSection.name);
	builder.append("]\n");
	for (Map.Entry<String, String> pair: iniSection.pairs.entrySet()) {
		builder.append(pair.getKey());
		builder.append(" = ");
		builder.append(pair.getValue());
		builder.append("\n");
	}
	builder.append("\n");
	return builder.toString();
}



//  Main    \\  //  \\  //  \\  //  \\  //  \\

public static void main(String... args) throws IOException{
	// We'll manually parse.
	
	DSVtoINIMapping mapping = new DSVtoINIMapping();
	List<String> filepathsToRead = new ArrayList<>();
	
	for (int o = 0; o < args.length; ++o) {
		String arg = args[o];
		
		if (matchesOption(arg, "help")) {
			// Explicit help.
			printHelp(System.out);
			System.exit(0);
		}
		else if (matchesOption(arg, "map")) {
			// Accepting key mappings.
			if (o == args.length - 1) lackingArgumentCrash();
			String value = args[++o];
			
			String[] fields = value.split(":");
			// Again, will use a more sophisticated parser later.
			// Although, truly excessive in this context.			
			mapping.keys = fields;
		}
		else if (matchesOption(arg, "primary")) {
			// Accepting specification of primary key.
			if (o == args.length - 1) lackingArgumentCrash();
			String value = args[++o];
			
			mapping.sectionNameKey = value;
		}
		else if (matchesOption(arg, null)) {
			// Testing for unrecognised option.
			printHelp(System.err);
			System.exit(1);
		}
		else {
			// Not an option? A filepath, then.
			filepathsToRead.add(arg);
		}
	}
	
	if (mapping.keys == null) {
		lackingMappingCrash();
	}
	
	List<INISection> totalSections = new ArrayList<>();
	if (filepathsToRead.isEmpty()) {
		totalSections.addAll(fullyTranslate(System.in, mapping));
	}
	else for (String filepath: filepathsToRead) {
		if (filepath.equals("-"))
			totalSections.addAll(fullyTranslate(System.in, mapping));
		else		
			totalSections.addAll(fullyTranslate(filepath, mapping));
	}
	
	for (INISection iniSection: totalSections)
		System.out.print(toString(iniSection));
}

private static boolean matchesOption(String arg, String option) {
	assert arg != null;
	
	if (option == null) {
		// We have to test if the argument is.. *some* option.
		return !arg.isEmpty() && arg.charAt(0) == '-';
	}
	else {
		assert option.length() > 0;
		
		String longOption = "--" + option;
		String shortOption = "-" + option.charAt(0);
		return arg.equals(longOption) || arg.equals(shortOption);
	}
}



private static void lackingArgumentCrash() {
	System.err.println("Option value must follow after, but is missing!");
	System.exit(1);
}

private static void lackingMappingCrash() {
	System.err.println(
		"DSV records provide only values, so keys must be provided,"
		+ " with --map, to translate them to INI sections!"
	);
	System.exit(1);
}



private static void printHelp(PrintStream out) {
	out.println("Usage: java DSVINIDisplay --map <mappings> [filepaths]");
	out.println("");
	out.println("This program translates DSV records to INI sections.");
	out.println("DSV records provide values, while <mappings> provides");
	out.println("the keys corresponding to the values. (Together they");
	out.println("form the key-value pairs of an INI section.)");
	out.println("");
	out.println("<mappings> is also in the form of a DSV record.");
	out.println("Its first field is the key for the first value of an");
	out.println("input DSV record, and so on. DSV records are assumed");
	out.println("to use ':' as the field separator.");
	out.println("");
	out.println("If no [filepaths] are provided, standard input is read.");
	out.println("");
	out.println("Options:");
	//           ---+---+---+---+---+---+---+---+---+---+---+---
	out.println("  -h, --help       Prints this help.");
	out.println("  -m, --map        Specify key mappings.");
	out.println("  -p, --primary    Specify the key that will be the INI");
	out.println("                   section's name. Otherwise, the first");
	out.println("                   key is assumed to play that role.");
	out.println("");
}

}
