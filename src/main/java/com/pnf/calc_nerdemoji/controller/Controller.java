package com.pnf.calc_nerdemoji.controller;

import com.pnf.calc_nerdemoji.controller.commands.ICommandRunnable;
import com.pnf.calc_nerdemoji.model.CalcContext;
import com.pnf.calc_nerdemoji.view.Terminal;
import com.pnf.calc_nerdemoji.view.TerminalView;
import org.jline.reader.EndOfFileException;
import org.jline.reader.UserInterruptException;

import java.io.File;

public class Controller {
    private final TerminalView view;
    private final FileController fileController;

    private CalcContext context;
    private Memory memory;

    public Controller(TerminalView view) {
        this.view = view;
        this.fileController = new FileController(this);
        this.context = CalcContext.empty();
        this.memory = new Memory(this);
    }

    public void runCommand(ICommandRunnable runnable, String[] args) {
        if (runnable.preChecks(this, args))
            if (!runnable.run(this, args))
                Terminal.error("Command execution failed");
    }

    public void initApp() {
        var memoryResult = fileController.loadMemory();
        if (!memoryResult.isErrored()) {
            memory = memoryResult.value();
            memory.setController(this);
        }

        File startFile = new File(memory.get(Memory.LAST_FILE, FileController.DATA.getAbsolutePath()));
        if (!startFile.exists()) {
            memory.set(Memory.LAST_FILE, FileController.DATA.getAbsolutePath());
            startFile = FileController.DATA;
        }

        var contextResult = fileController.loadContext(startFile);
        if (!contextResult.announcedIsErrored()) {
            this.context = contextResult.announcedValue();
            Terminal.info("Loaded session from " + memory.get(Memory.LAST_FILE));
        }


        view.runTerminal(this);
    }

    public void exitApp() {
        fileController.saveMemory();
        System.exit(0);
    }

    public void save() {
        fileController.saveContext(context).logErrored();
    }

    public boolean loadContext(File file) {
        var contextResult = fileController.loadContext(file);
        if (!contextResult.announcedIsErrored()) {
            this.context = contextResult.announcedValue();
            return true;
        }
        return false;
    }

    public String input(String msg) {
        return view.getReader().readLine(msg);
    }

    public String input() throws UserInterruptException, EndOfFileException {
        return input(Terminal.PREFIX + " > ");
    }

    public CalcContext getContext() {
        return context;
    }

    public FileController getFileController() {
        return fileController;
    }

    public Memory getMemory() {
        return memory;
    }
}
