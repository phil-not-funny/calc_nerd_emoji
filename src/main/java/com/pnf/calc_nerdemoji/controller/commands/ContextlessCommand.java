package com.pnf.calc_nerdemoji.controller.commands;

import com.pnf.calc_nerdemoji.controller.Controller;
import com.pnf.calc_nerdemoji.view.Terminal;

public class ContextlessCommand implements ICommandRunnable {
    public static ICommandRunnable helpCommand() {
        return new ContextlessCommand(Terminal::logHelp);
    }

    public static ContextlessCommand toContextless(ICommandRunnable commandRunnable) {
        return new ContextlessCommand(commandRunnable::run);
    }

    private final Runnable runnable;

    public ContextlessCommand(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public boolean run(Controller controller, String[] args, char[] modifiers) {
        runnable.run();
        return true;
    }

    @Override
    public boolean preChecks(Controller controller, String[] args, char[] modifiers) {
        return true;
    }

    @Override
    public CommandHelp help() {
        return null;
    }
}
