package com.pnf.calc_nerdemoji.controller;

import com.pnf.calc_nerdemoji.controller.commands.ICommandRunnable;
import com.pnf.calc_nerdemoji.model.CalcContext;
import com.pnf.calc_nerdemoji.view.Terminal;
import com.pnf.calc_nerdemoji.view.TerminalView;
import org.jline.reader.EndOfFileException;
import org.jline.reader.UserInterruptException;

import java.io.File;
import java.util.Arrays;
import java.util.function.Function;

public class Controller {
    private CalcContext context;
    private final TerminalView view;
    private final FileController fileController;

    private final CalcMemory memory = new CalcMemory();

    public Controller(TerminalView view) {
        this.view = view;
        this.fileController = new FileController(this);
        this.context = CalcContext.empty();
    }

    public void runCommand(ICommandRunnable runnable, String[] args) {
        if (runnable.preChecks(this, args))
            if (!runnable.run(this, args))
                Terminal.error("Command execution failed");
    }

    public void initApp() {
        var contextResult = fileController.loadContext();
        if (!contextResult.announcedIsErrored()) {
            this.context = contextResult.announcedValue();
            Terminal.info("Loaded session from " + FileController.DATA.getAbsolutePath());
        }
        view.runTerminal(this);
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

    public CalcMemory getMemory() {
        return memory;
    }
}
