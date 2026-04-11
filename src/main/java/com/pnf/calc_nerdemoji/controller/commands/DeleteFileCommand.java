package com.pnf.calc_nerdemoji.controller.commands;

import com.pnf.calc_nerdemoji.controller.Controller;

import java.io.File;

public class DeleteFileCommand implements ICommandRunnable {
    @Override
    public boolean run(Controller controller, String[] args, char[] modifierss) {
        File file = controller.getFileController().fileFromTerminalInput(args[0], false).value();
        return file.delete();
    }

    @Override
    public boolean preChecks(Controller controller, String[] args, char[] modifiers) {
        return CommandHelper.requireArgsLength(args, 1) &&
                !controller.getFileController().fileFromTerminalInput(args[0], false).announcedIsErrored();
    }

    @Override
    public CommandHelp help() {
        return null;
    }
}
