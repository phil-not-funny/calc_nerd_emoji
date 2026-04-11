package com.pnf.calc_nerdemoji.controller.commands;

import com.pnf.calc_nerdemoji.controller.Controller;
import com.pnf.calc_nerdemoji.controller.FileController;
import com.pnf.calc_nerdemoji.model.OperationResult;

import java.io.File;
import java.util.Arrays;

public class SaveFileCommand implements ICommandRunnable{
    @Override
    public boolean run(Controller controller, String[] args, char[] modifiers) {
        OperationResult<File> fileResult = controller.getFileController().fileFromTerminalInput(args[0], true);
        fileResult.log();
        if(fileResult.isErrored()) return false;

        return controller.getFileController().saveContext(controller.getContext(), fileResult.value()).announcedValue();
    }

    @Override
    public boolean preChecks(Controller controller, String[] args, char[] modifiers) {
        return CommandHelper.requireArgsLength(args, 1) &&
                CommandHelper.requireTrue(
                        Arrays.stream(FileController.illegalFileNames).
                                noneMatch(s -> s.equalsIgnoreCase(args[0])),
                        "%s is an illegal file name!".formatted(args[0])
                );
    }


    @Override
    public CommandHelp help() {
        return null;
    }
}
