
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

class Models {

//  Structs \\  //  \\  //  \\  //  \\

public static class Property {
	public String key;
	public String value;

	public Property() {
		this.key = null;
		this.value = null;
	}

	public Property(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public boolean equals(Object otherObject) {
		if (!(otherObject instanceof Property)) return false;
		Property other = (Property)otherObject;
		
		return
			Objects.equals(this.key, other.key)
			&& Objects.equals(this.value, other.value);
	}
}
/*
* Simple K-V struct. Differs from AbstractMap.SimpleEntry in that
* it is a struct, so you can change the key as well, and there is
* a no argument constructor that makes the key and value null.
*/



public static class IniSection {
	public String name;
	public final List<Property> properties = new ArrayList<Property>();
	/*
	* It was decided that we should have properties be ordered.
	* Therefore two INI sections are actually unequal if
	* they contain the same properties but in different order.
	*/
	
	
	public boolean equals(Object otherObject) {
		// Please unit test this.
		if (!(otherObject instanceof IniSection)) return false;
		IniSection other = (IniSection)otherObject;
	
		if (!(Objects.equals(this.name, other.name))) 
			return false;
		
		if (this.properties.size() != other.properties.size()) 
			return false;
		
		for (int o = 0; o < this.properties.size(); ++o) {
			Property tP = this.properties.get(o);
			Property oP = this.properties.get(o);
			if (!Objects.equals(tP, oP)) return false;
		}
		
		return true;
	}

}

}
