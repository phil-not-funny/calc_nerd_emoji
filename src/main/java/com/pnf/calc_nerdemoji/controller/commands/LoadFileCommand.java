package com.pnf.calc_nerdemoji.controller.commands;

import com.pnf.calc_nerdemoji.controller.Controller;
import com.pnf.calc_nerdemoji.controller.Memory;
import com.pnf.calc_nerdemoji.model.OperationResult;

import java.io.File;

public class LoadFileCommand implements ICommandRunnable {
    @Override
    public boolean run(Controller controller, String[] args) {
        OperationResult<File> fileResult = controller.getFileController().fileFromTerminalInput(args[0], false);
        fileResult.log();
        if(fileResult.isErrored()) return false;

        boolean result = controller.loadContext(fileResult.announcedValue());
        controller.getMemory().set(Memory.LAST_FILE, fileResult.value().getAbsolutePath());
        return result;
    }

    @Override
    public boolean preChecks(Controller controller, String[] args) {
        return CommandHelper.requireArgsLength(args, 1);
    }


    @Override
    public CommandHelp help() {
        return null;
    }
}
