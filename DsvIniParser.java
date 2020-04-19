
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;

class DsvIniParser {

private BufferedReader reader;

//  \\  //  \\  //  \\  //  \\

DsvIniParser(InputStream stream) throws IOException {
	reader = new BufferedReader(new InputStreamReader(stream));
}

//  \\  //  \\  //  \\  //  \\

public boolean hasNext() {
	// Please unit test this.
}

public Section next() {
	// Please unit test this.
}

//  \\  //  \\  //  \\  //  \\

static final String LOOK_BEHIND_BACKSLASH_ESCAPE = "(?<!\\\\)"
static final String DELIMITER = ":"
static final String SPLITTER_REGEX = LOOK_BEHIND_BACKSLASH_ESCAPE + DELIMITER;

public static String[] splitDSVString(String string) {
	// Please unit test this.
	return string.split(SPLITTER_REGEX);
}
