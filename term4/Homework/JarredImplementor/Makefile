# Makefile for Java project, lol

MANIFEST = $(realpath src/META-INF/MANIFEST.MF)
PROJECT_PATH = ru/ifmo/ctddev/belyy/implementor
GEORGIY_PATH = info/kgeorgiy/java/advanced/implementor
SOURCE_DIR = src
BINARY_DIR = bin
DOCS_DIR = docs

SRC_PATH = $(SOURCE_DIR)/$(PROJECT_PATH)
BIN_PATH = $(BINARY_DIR)/$(PROJECT_PATH)
JAVA_OPTS = -sourcepath $(SOURCE_DIR) -d $(BINARY_DIR)

all: $(BINARY_DIR)/JarredImplementor.jar $(DOCS_DIR)

clean:
	rm -rf $(BINARY_DIR)
	rm -rf $(DOCS_DIR)

$(BINARY_DIR)/JarredImplementor.jar: $(BIN_PATH)/Main.class $(BIN_PATH)/JarredImplementor.class
	cd $(BINARY_DIR); jar cfm $(notdir $@) $(MANIFEST) ru/ info/

$(DOCS_DIR):
	javadoc -d $@ -private $(SRC_PATH)/*.java $(SOURCE_DIR)/$(GEORGIY_PATH)/*.java -linkoffline http://docs.oracle.com/javase/8/docs/api/ http://docs.oracle.com/javase/8/docs/api/ 

$(BIN_PATH)/Main.class: $(SRC_PATH)/Main.java $(BINARY_DIR)
	javac $(JAVA_OPTS) $<

$(BINARY_DIR):
	mkdir -p $@
