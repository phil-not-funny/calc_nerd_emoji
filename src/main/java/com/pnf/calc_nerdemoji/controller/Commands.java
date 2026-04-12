package com.pnf.calc_nerdemoji.controller;

import com.pnf.calc_nerdemoji.controller.exceptions.CommandNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Commands {
    public record PartedCommand(String command, String[] args, char[] modifiers) {}

    public static boolean isValidCommand(String in) {
        return Arrays.stream(Command.values()).anyMatch(c -> c.getAliases().contains(in));
    }

    public static Command fromInput(String in) {
        return Arrays.stream(Command.values())
                .filter(c -> c.getAliases().contains(in))
                .findFirst()
                .orElseThrow(() -> new CommandNotFoundException(in));
    }

    public static PartedCommand splitInput(String in) {
        String[] parts = in.split(" ");
        String[] additions = Arrays.copyOfRange(parts, 1, parts.length);
        List<String> args = new ArrayList<>();
        char[] modifiers = new char[0];
        for (String addition : additions) {
            if (addition.startsWith("-")) {
                final char[] newModifiers = addition.substring(1).toCharArray();
                if (modifiers.length == 0) {
                    modifiers = newModifiers;
                } else {
                    char[] merged = new char[modifiers.length + newModifiers.length];
                    System.arraycopy(modifiers, 0, merged, 0, modifiers.length);
                    System.arraycopy(newModifiers, 0, merged, modifiers.length, newModifiers.length);
                    modifiers = merged;
                }
            }
            else
                args.add(addition.replaceAll("\"", ""));
        }
        return new PartedCommand(parts[0], (String[]) args.toArray(new String[0]), modifiers);
    }
}
