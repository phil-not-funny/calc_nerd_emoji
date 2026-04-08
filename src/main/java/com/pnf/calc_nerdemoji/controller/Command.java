package com.pnf.calc_nerdemoji.controller;

import com.pnf.calc_nerdemoji.controller.commands.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Command implements ITerminalPickable {
    HELP(ContextlessCommand.helpCommand(), "?", "h"),
    EXIT(SimpleCommand.exitCommand(), "bye"),
    HELLO(new SimpleOutputCommand("Hello Mr. Stark")),
    ADD_FIELD(new AddDynamicFieldCommand(), "ad"),
    SUM_BILL(new SumBillCommand(), "sum"),
    LIST_FIELDS(new ListFieldsCommand(), "list", "ls"),
    LOAD_CONTEXT(new LoadFileCommand(), "load"),
    SAVE_CONTEXT(new SaveFileCommand(), "save"),
    DELETE_CONTEXT(new DeleteFileCommand(), "delete", "del"),
    ADD_REFERENCE(new AddBillReferenceCommand(), "ar");

    private final ICommandRunnable runner;
    private final List<String> aliases;

    Command(ICommandRunnable runner, String... aliases) {
        this.runner = runner;
        this.aliases = List.of(aliases);
    }

    public String getName() {
        return this.name().toLowerCase();
    }

    public int getId() {
        return this.ordinal();
    }

    public List<String> getAliases() {
        return Stream.concat(
                Stream.of(this.getName()),
                aliases.stream()
        ).collect(Collectors.toList());
    }

    public ICommandRunnable getRunner() {
        return runner;
    }

    @Override
    public String toString() {
        return name();
    }

    @Override
    public String toPickValue() {
        return getName();
    }
}
