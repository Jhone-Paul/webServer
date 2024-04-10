# Define variables
JAVAC = javac
JAVA = java
SRC_DIR = src
BUILD_DIR = build
SERVER_CLASS = simpleHTTPServer

# Define targets
all: compile run

compile:
	@mkdir -p $(BUILD_DIR)
	$(JAVAC) -d $(BUILD_DIR) $(SRC_DIR)/*.java

run:
	$(JAVA) -cp $(BUILD_DIR) $(SERVER_CLASS)

clean:
	@rm -rf $(BUILD_DIR)

.PHONY: all compile run clean
