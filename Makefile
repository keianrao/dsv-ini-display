
all:
	javac *.java
	javac test/*.java

run:
	java DsvIniDisplay

test:
	java -cp .:test -ea PrinterTest
	java -cp .:test -ea ParserTest
	java -cp .:test -ea DsvIniDisplayTest

clean:
	rm *.class
	rm test/*.class

.PHONY: all run test clean
