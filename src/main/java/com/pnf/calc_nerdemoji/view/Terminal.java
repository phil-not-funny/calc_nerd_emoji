package com.pnf.calc_nerdemoji.view;

import com.pnf.calc_nerdemoji.controller.Command;
import com.pnf.calc_nerdemoji.controller.ITerminalPickable;
import com.pnf.calc_nerdemoji.controller.commands.CommandHelp;
import org.fusesource.jansi.AnsiConsole;

import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

import java.util.Arrays;
import java.util.List;

public class Terminal {
    public enum Level { INFO, SUCCESS, OK, WARN, ERROR, QUESTION, DEBUG }

    public static void log(Level level, String message) {
        String prefix = switch (level) {
            case INFO    -> ansi().fg(CYAN).a("   INFO").reset().toString();
            case OK -> ansi().fg(GREEN).a("     OK").reset().toString();
            case SUCCESS -> ansi().fg(GREEN).a("SUCCESS").reset().toString();
            case WARN    -> ansi().fg(YELLOW).a("   WARN").reset().toString();
            case ERROR   -> ansi().fg(RED).bold().a("  ERROR").reset().toString();
            case DEBUG -> ansi().fg(BLUE).bold().a("  DEBUG").reset().toString();
            case QUESTION -> QUESTION_PREFIX;
        };
        System.out.println(prefix + " | " + message);
    }

    public static void debug(String message) { log(Level.DEBUG, message); }
    public static void error(String message) { log(Level.ERROR, message); }
    public static void info(String message) { log(Level.INFO, message); }

    public static void logPrimitive(String message) {
        System.out.println(INDENT + " | " + message);
    }

    public static void logIndented(String message) {
        System.out.println(INDENT + message);
    }

    public static <T extends Enum<?> & ITerminalPickable> void logEnum(Level level, Class<T> clazz, String message) {
        log(level, message != null ? message : "One of " + clazz.getSimpleName());
        Arrays.stream(clazz.getEnumConstants()).forEach(e -> logPrimitive(e.toString() + "(" + e.toPickValue() + ")"));
    }

    public static void logList(Level level, List<?> list, String message) {
        log(level, message != null ? message : "List:");
        list.forEach(i -> logPrimitive(i.toString()));
    }

    public static void logHelp() {
        System.out.println(ansi().fg(CYAN).bold().a("  Available Commands").reset());
        System.out.println(ansi().fg(CYAN).bold().a("  " + "-".repeat(60)).reset());

        for (Command cmd : Command.values()) {
            CommandHelp help = cmd.getRunner().help();

            // Command name
            System.out.println(ansi()
                    .a("     ")
                    .fg(YELLOW).bold().a(cmd.getName()).reset()
                    .a(cmd.getAliases().size() > 1
                            ? ansi().fg(YELLOW).a("  [" + String.join(", ", cmd.getAliases().subList(1, cmd.getAliases().size())) + "]").reset().toString()
                            : "")
            );

            if (help == null) {
                logIndented(ansi().a("No description available.").reset().toString());
            } else {
                // Description
                logPrimitive(ansi().a(help.getDescription()).reset().toString());

                // Usage
                if (help.getUsage() != null && !help.getUsage().isEmpty()) {
                    logPrimitive(ansi().fg(CYAN).a("Usage:").reset().toString());
                    for (String u : help.getUsage())
                        logPrimitive(ansi().a("      " + u).reset().toString());
                }

                // Examples
                if (help.getExamples() != null && !help.getExamples().isEmpty()) {
                    logPrimitive(ansi().fg(CYAN).a("Examples:").reset().toString());
                    for (String e : help.getExamples())
                        logPrimitive(ansi().fg(GREEN).a("      > " + e).reset().toString());
                }
            }
        }
        System.out.println(ansi().fg(CYAN).bold().a("  %sEnd%s".formatted("-".repeat(28),"-".repeat(28))).reset());
    }
    
    public static final String QUESTION_PREFIX = ansi().fg(BLUE).a("      ?").reset().toString();
    public static final String PREFIX = "Calc 🤓";
    public static final String INDENT = "       ";
}
