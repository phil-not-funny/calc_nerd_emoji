package com.pnf.calc_nerdemoji.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pnf.calc_nerdemoji.model.CalcContext;
import com.pnf.calc_nerdemoji.model.OperationResult;
import com.pnf.calc_nerdemoji.view.Terminal;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

public class FileController {
    private static final String SEPARATOR = FileSystems.getDefault().getSeparator();
    public static final File ROOT = new File(System.getProperty("user.home") + SEPARATOR + ".calc_nerd");
    public static final File DATA = new File(ROOT.getAbsolutePath() + SEPARATOR + "data.json");

    static {
        ROOT.mkdir();
    }

    private final Controller controller;
    private final ObjectMapper mapper = new ObjectMapper();

    public FileController(Controller controller) {
        this.controller = controller;
    }

    public OperationResult<CalcContext> loadContext() {
        return loadContext(DATA);
    }

    public OperationResult<CalcContext> loadContext(File file) {
        try {
            if (file.exists())
                return OperationResult.from(mapper.readValue(file, CalcContext.class), OperationResult.Result.SUCCESS, "Previous session restored.");
        } catch (IOException e) {
            return OperationResult.fail("Unexpected error occurred while loading previous session:\n" + e.getMessage());
        }
        return OperationResult.from(CalcContext.empty());
    }

    public OperationResult<Boolean> saveContext(CalcContext context, File file) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, context);
            return OperationResult.from(true);
        } catch (IOException e) {
            return OperationResult.from(false, OperationResult.Result.ERROR, "Unexpected error occurred while saving current session:\n" + e.getMessage());
        }
    }

    public OperationResult<Boolean> saveContext(CalcContext context) {
        return saveContext(context, DATA);
    }

    public OperationResult<File> fileFromTerminalInput(String arg, boolean create) {
        if (arg.isEmpty()) return OperationResult.fail("Operation requires file name");
        String path = ROOT + SEPARATOR + "%s.json".formatted(arg);
        File file = new File(path);
        if (file.exists())
            return OperationResult.from(file);
        else if (create) {
            Terminal.info("Given File not found. Creating new one...");
            try {
                if (file.createNewFile())
                    return OperationResult.from(file, OperationResult.Result.CREATED, "The File has been successfully created.");
                else
                    return OperationResult.fail("Unable to create " + file.getAbsolutePath());
            } catch (IOException e) {
                return OperationResult.fail(e.getMessage());
            }
        } else
            return OperationResult.fail("File " + file.getAbsolutePath() + " could not be found");
    }
}
