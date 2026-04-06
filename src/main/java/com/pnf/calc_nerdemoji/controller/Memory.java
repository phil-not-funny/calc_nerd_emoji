package com.pnf.calc_nerdemoji.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class Memory {
    public static final String LAST_BILL_NAME = "last_bill_name";
    public static final String LAST_FILE = "last_saved_file";

    @JsonProperty
    private final Map<String, Object> memory;

    @JsonCreator
    private Memory(@JsonProperty("memory") Map<String, Object> memory) {
        this.memory = memory;
    }

    public Memory() {
        this.memory = new HashMap<>();
    }

    public Object get(String key) {
        return memory.get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, Object defaultValue) {
        Object o = memory.get(key);
        if(memory.isEmpty()) set(key, defaultValue);
        return (T) ((T) o != null ? o : defaultValue);
    }

    public void set(String key, Object o) {
        memory.put(key, o);

    }
}
