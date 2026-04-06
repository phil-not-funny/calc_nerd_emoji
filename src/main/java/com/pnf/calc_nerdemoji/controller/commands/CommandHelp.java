package com.pnf.calc_nerdemoji.controller.commands;

import java.util.List;

public class CommandHelp {
    private final String description;
    private final List<String> usage;
    private final List<String> examples;

    private CommandHelp(String description, List<String> usage, List<String> examples) {
        this.description = description;
        this.usage = usage;
        this.examples = examples;
    }

    public static CommandHelp of(String description) {
        return new CommandHelp(description, List.of(), List.of());
    }

    public static CommandHelp of(String description, List<String> usage) {
        return new CommandHelp(description, usage, List.of());
    }

    public static CommandHelp of(String description, List<String> usage, List<String> examples) {
        return new CommandHelp(description, usage, examples);
    }

    public String getDescription() { return description; }
    public List<String> getUsage() { return usage; }
    public List<String> getExamples() { return examples; }
}
