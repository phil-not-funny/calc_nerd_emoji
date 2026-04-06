package com.pnf.calc_nerdemoji.view;

import com.pnf.calc_nerdemoji.controller.Controller;
import com.pnf.calc_nerdemoji.controller.Command;
import com.pnf.calc_nerdemoji.controller.Commands;
import com.pnf.calc_nerdemoji.controller.exceptions.CommandNotFoundException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class TerminalView {
    public static final StringsCompleter COMPLETER = new StringsCompleter(
            Arrays.stream(Command.values()).map(Command::getName).toArray(String[]::new)
    );

    private final LineReader reader;
    private final Terminal terminal;

    public TerminalView() throws IOException {
        terminal = TerminalBuilder.builder().system(true).jansi(true).build();
        Path historyFile = Paths.get(System.getProperty("user.home"), ".calc_history");
        historyFile.toFile().createNewFile();

        reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .completer(COMPLETER)
                .variable(LineReader.HISTORY_FILE, historyFile)
                .build();
    }

    public void runTerminal(Controller controller) {
        System.out.println("Good morning, Mr. Stark");
        while (true) {
            String line;
            try {
                line = controller.input();
            } catch (org.jline.reader.UserInterruptException e) {
                // Ctrl+C
                continue;
            } catch (org.jline.reader.EndOfFileException e) {
                // Ctrl+D
                break;
            }

            line = line.trim();
            if (line.isBlank()) continue;
            String[] parts = line.split(" ");

            try {
                Command command = Commands.fromInput(parts[0]);
                String[] args = Arrays.copyOfRange(parts, 1, parts.length);
                controller.runCommand(command.getRunner(), args);
            } catch(Exception e) {
                com.pnf.calc_nerdemoji.view.Terminal.error(e.getMessage());
            }
        }
    }

    public LineReader getReader() {
        return reader;
    }
}
