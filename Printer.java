
import java.util.Map;

class Printer {

static String toString(IniSection section) {
	// Please unit test this.

	StringBuilder builder = new StringBuilder();

	// Header..
	builder.append("["); builder.append(section.name); builder.append("]\n");

	// Properties..
	for (Map.Entry<String, String> property: section.properties.entrySet()) {
		builder.append(property.getKey()); 
		builder.append("=");
		builder.append(property.getValue());
		builder.append("\n");
	}

	// Okay, all good.
	return builder.toString();
}

}