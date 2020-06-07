
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

class IniSection {

String name;
List<Property> properties = new ArrayList<Property>();

boolean equals(IniSection other) {
	if (!(Objects.equals(this.name, other.name))) return false;
	if (this.properties.size() != other.properties.size()) return false;
	for (int o = 0; o < this.properties.size(); ++o) {
		if (this.properties.get(o) != other.properties.get(o)) {
			return false;
		}
	}
	return true;
}

}