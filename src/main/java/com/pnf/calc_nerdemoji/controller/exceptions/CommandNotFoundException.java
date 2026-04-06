package com.pnf.calc_nerdemoji.controller.exceptions;

public class CommandNotFoundException extends RuntimeException {
    private static final String PREFIX = "Unknown Command: ";

    public CommandNotFoundException(String input) {
        super(getMessage(input));
    }

    public static String getMessage(String input) {
        return PREFIX + input;
    }
}
