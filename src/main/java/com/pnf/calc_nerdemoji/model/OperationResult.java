package com.pnf.calc_nerdemoji.model;

import com.pnf.calc_nerdemoji.view.Terminal;

public class OperationResult<T> {
    public enum Result { CREATED, REMOVED, OKAY, ERROR, SUCCESS }

    public static <T> OperationResult<T> from(T value) {
        return new OperationResult<>(value, Result.OKAY, "No info.");
    }

    public static <T> OperationResult<T> from(T value, Result result) {
        return new OperationResult<>(value, result, "No info.");
    }

    public static <T> OperationResult<T> from(T value, Result result, String info) {
        return new OperationResult<>(value, result, info);
    }

    public static <T> OperationResult<T> fail(String message) {
        return new OperationResult<>(null, Result.ERROR, message);
    }

    private final T value;
    private final Result result;
    private String info = "No info.";

    private OperationResult(T value, Result result, String info) {
        this.value = value;
        this.result = result;
        this.info = info;
    }

    public void log() {
        switch(result) {
            case CREATED -> Terminal.log(Terminal.Level.INFO, "New instance created.");
            case REMOVED -> Terminal.log(Terminal.Level.INFO, "Instance removed.");
            case OKAY -> { break; }
            case ERROR -> Terminal.log(Terminal.Level.ERROR, info);
            case SUCCESS -> Terminal.log(Terminal.Level.SUCCESS, info);
        }
    }

    public void logErrored() {
        if(result == Result.ERROR) log();
    }

    public T announcedValue() {
        log();
        return value();
    }

    public boolean isErrored() {
        return result == Result.ERROR;
    }

    public boolean announcedIsErrored() {
        boolean errored = result == Result.ERROR;
        if(errored) log();
        return errored;
    }

    public boolean isEmpty() {
        return value == null;
    }

    public T value() { return value; }
    public Result result() { return result; }
}
