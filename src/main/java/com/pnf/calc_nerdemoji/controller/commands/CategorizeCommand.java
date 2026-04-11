package com.pnf.calc_nerdemoji.controller.commands;

import com.pnf.calc_nerdemoji.controller.Controller;

public class CategorizeCommand implements ICommandRunnable {
    @Override
    public boolean run(Controller controller, String[] args, char[] modifiers) {


        return true;
    }

    @Override
    public boolean preChecks(Controller controller, String[] args, char[] modifiers) {
        CommandHelper.requireValidModifiers(modifiers, 's', 'f');
        return CommandHelper.requireArgsLength(args, 0, 1);
    }

    @Override
    public CommandHelp help() {
        return null;
    }
}
