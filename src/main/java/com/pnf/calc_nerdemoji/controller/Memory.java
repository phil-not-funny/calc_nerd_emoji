package com.pnf.calc_nerdemoji.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pnf.calc_nerdemoji.view.Terminal;

import java.util.HashMap;
import java.util.Map;

public class Memory {
    public static final String LAST_BILL_NAME = "last_bill_name";
    public static final String LAST_FILE = "last_saved_file";

    @JsonProperty
    private final Map<String, String> memory;

    @JsonIgnore
    private Controller controller;

    @JsonCreator
    private Memory(@JsonProperty("memory") Map<String, String> memory) {
        this.memory = memory;
    }

    public Memory(Controller controller) {
        this.memory = new HashMap<>();
        this.controller = controller;
    }

    public String get(String key) {
        return memory.get(key);
    }

    public String get(String key, String defaultValue) {
        String value = memory.get(key);
        if(value == null) {
            set(key, defaultValue);
            return defaultValue;
        }
        return value;
    }

    public String writeIfUnexpected(String key, String defaultValue, String expected) {
        String actual = get(key, defaultValue);
        if(actual == null || !actual.equalsIgnoreCase(expected)) {
            set(key, defaultValue);
            return defaultValue;
        }
        return actual;
    }

    public void set(String key, String value) {
        memory.put(key, value);
        controller.getFileController().saveMemory();
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
