package com.pnf.calc_nerdemoji.controller;

import com.pnf.calc_nerdemoji.controller.exceptions.CommandNotFoundException;
import com.pnf.calc_nerdemoji.model.CalcBill;

import java.util.Arrays;

public class Commands {
    public static boolean isValidCommand(String in) {
        return Arrays.stream(Command.values()).anyMatch(c -> c.getAliases().contains(in));
    }

    public static Command fromInput(String in) {
        return Arrays.stream(Command.values())
                .filter(c -> c.getAliases().contains(in))
                .findFirst()
                .orElseThrow(() -> new CommandNotFoundException(in));
    }
}
