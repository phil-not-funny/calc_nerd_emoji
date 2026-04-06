package com.pnf.calc_nerdemoji.controller.questioning;

import java.util.List;

public class QuestionResult {
    private final List<Object> values;

    public QuestionResult(List<Object> values) {
        this.values = values;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(int index, Class<T> type) {
        return (T) values.get(index);
    }

    @SuppressWarnings("unchecked")
    public <T> T getUnsafe(int index) {
        return (T) values.get(index);
    }

    public int size() { return values.size(); }
}
