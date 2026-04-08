package com.pnf.calc_nerdemoji.controller.commands;

import com.pnf.calc_nerdemoji.controller.Controller;

public class CategorizeCommand implements ICommandRunnable {
    @Override
    public boolean run(Controller controller, String[] args) {
        return false;
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
