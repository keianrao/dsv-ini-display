
all:
	javac *.java

run:
	java -ea DsvIniDisplay

clean:
	rm *.class

.PHONY: all clean