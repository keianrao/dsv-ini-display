
class Printer {

static String toString(IniSection section) {
	// Please unit test this.

	StringBuilder builder = new StringBuilder();

	// Header..
	builder.append("["); builder.append(section.name); builder.append("]\n");

	// Properties..
	for (Property property: section.properties) {
		builder.append(property.key); 
		builder.append("=");
		builder.append(property.value);
		builder.append("\n");
	}

	// Okay, all good.
	return builder.toString();
}

}