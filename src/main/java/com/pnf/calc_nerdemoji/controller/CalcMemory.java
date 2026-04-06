package com.pnf.calc_nerdemoji.controller;

import java.util.HashMap;
import java.util.Map;

public class CalcMemory {
    public static final String LAST_BILL_NAME = "last_bill_name";

    private final Map<String, Object> memory = new HashMap<>();

    public Object get(String key) {
        return memory.get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, Object defaultValue, Class<T> type) {
        Object o = memory.putIfAbsent(key, defaultValue);
        return (T) ((T) o != null ? o : defaultValue);
    }

    public void set(String key, Object o) {
        memory.put(key, o);
    }
}
