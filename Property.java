
import java.util.Objects;

class Property {
	String key;
	String value;

	Property() {

	}

	Property(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public boolean equals(Object objOther) {
		if (!(objOther instanceof Property)) return false;
		Property other = (Property)objOther;
		return
			Objects.equals(this.key, other.key)
			&& Objects.equals(this.value, other.value);
	}
} 
