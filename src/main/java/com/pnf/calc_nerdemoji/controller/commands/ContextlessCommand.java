package com.pnf.calc_nerdemoji.controller.commands;

import com.pnf.calc_nerdemoji.controller.Command;
import com.pnf.calc_nerdemoji.controller.Controller;
import com.pnf.calc_nerdemoji.view.Terminal;

public class ContextlessCommand implements ICommandRunnable {
    public static ICommandRunnable exitCommand() {
        return new ContextlessCommand(() -> System.exit(0));
    }

    public static ICommandRunnable helpCommand() {
        return new ContextlessCommand(() -> {
            Terminal.logEnum(Terminal.Level.INFO, Command.class, "Available Commands are:");
        });
    }

    public static ContextlessCommand toContextless(ICommandRunnable commandRunnable) {
        return new ContextlessCommand(commandRunnable::run);
    }

    private final Runnable runnable;

    public ContextlessCommand(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public boolean run(Controller controller, String[] args) {
        runnable.run();
        return true;
    }

    @Override
    public boolean preChecks(Controller controller, String[] args) {
        return true;
    }

    @Override
    public String help() {
        return null;
    }
}
