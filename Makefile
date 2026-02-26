APP_NAME := trust-engine

SRC_DIR := src
BUILD_DIR := build
CLASSES_DIR := $(BUILD_DIR)/classes
JAR_DIR := $(BUILD_DIR)
JAR := $(JAR_DIR)/$(APP_NAME).jar

MAIN ?= main.Main

JAVA ?= java
JAVAC ?= javac
JAR_TOOL ?= jar

# Override if you need an older/newer JDK target.
JAVA_RELEASE ?= 21

JAVA_SOURCES := $(shell find $(SRC_DIR) -name '*.java')

.PHONY: all compile jar run run-jar clean help

all: jar

$(CLASSES_DIR):
	@mkdir -p "$@"

compile: $(CLASSES_DIR)
	$(JAVAC) --release $(JAVA_RELEASE) -d "$(CLASSES_DIR)" $(JAVA_SOURCES)
	@if [ -d "$(SRC_DIR)/resources" ]; then \
		mkdir -p "$(CLASSES_DIR)/resources"; \
		cp -R "$(SRC_DIR)/resources/." "$(CLASSES_DIR)/resources/"; \
	fi

jar: compile
	@mkdir -p "$(JAR_DIR)"
	$(JAR_TOOL) --create --file "$(JAR)" --main-class "$(MAIN)" -C "$(CLASSES_DIR)" .
	@echo "Built $(JAR)"

run: compile
	$(JAVA) -cp "$(CLASSES_DIR)" "$(MAIN)"

run-jar: jar
	$(JAVA) -jar "$(JAR)"

clean:
	rm -rf "$(BUILD_DIR)"

help:
	@printf "%s\n" \
		"Targets:" \
		"  make            Build jar (default)" \
		"  make compile    Compile to $(CLASSES_DIR)" \
		"  make jar        Build runnable jar at $(JAR)" \
		"  make run        Run from class files" \
		"  make run-jar    Run the jar" \
		"  make clean      Remove build output" \
		"" \
		"Vars (override like: make jar JAVA_RELEASE=17):" \
		"  MAIN, JAVA, JAVAC, JAR_TOOL, JAVA_RELEASE"
