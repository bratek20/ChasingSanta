JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        chasingsanta/ChasingSanta.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) chasingsanta/*.class
