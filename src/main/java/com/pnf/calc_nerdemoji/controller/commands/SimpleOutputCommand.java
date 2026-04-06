package com.pnf.calc_nerdemoji.controller.commands;

import com.pnf.calc_nerdemoji.controller.Controller;
import com.pnf.calc_nerdemoji.view.Terminal;

public class SimpleOutputCommand implements ICommandRunnable {
    private final String message;

    public SimpleOutputCommand(String message) { this.message = message; }

    @Override
    public boolean run(Controller controller, String[] args) {
        Terminal.log(Terminal.Level.OK, message);
        return true;
    }

    @Override
    public boolean preChecks(Controller controller, String[] args) {
        return true;
    }


    @Override
    public CommandHelp help() {
        return null;
    }
}
