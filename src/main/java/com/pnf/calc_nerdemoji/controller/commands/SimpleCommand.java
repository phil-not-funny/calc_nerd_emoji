package com.pnf.calc_nerdemoji.controller.commands;

import com.pnf.calc_nerdemoji.controller.Controller;

import java.util.function.Consumer;

public class SimpleCommand implements ICommandRunnable {
    private final Consumer<Controller> runner;

    public static SimpleCommand exitCommand() {
        return new SimpleCommand(Controller::exitApp);
    }

    public SimpleCommand(Consumer<Controller> runner) {
        this.runner = runner;
    }

    @Override
    public boolean run(Controller controller, String[] args, char[] modifiers) {
        runner.accept(controller);
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
