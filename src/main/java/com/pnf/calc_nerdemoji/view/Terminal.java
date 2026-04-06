package com.pnf.calc_nerdemoji.view;

import com.pnf.calc_nerdemoji.controller.ITerminalPickable;
import org.fusesource.jansi.Ansi;

import java.util.Arrays;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

public class Terminal {
    public enum Level { INFO, SUCCESS, OK, WARN, ERROR, QUESTION, DEBUG }

    public static void log(Level level, String message) {
        String prefix = switch (level) {
            case INFO    -> ansi().fg(Ansi.Color.CYAN).a("   INFO").reset().toString();
            case OK -> ansi().fg(Ansi.Color.GREEN).a("     OK").reset().toString();
            case SUCCESS -> ansi().fg(Ansi.Color.GREEN).a("SUCCESS").reset().toString();
            case WARN    -> ansi().fg(Ansi.Color.YELLOW).a("   WARN").reset().toString();
            case ERROR   -> ansi().fg(Ansi.Color.RED).bold().a("  ERROR").reset().toString();
            case DEBUG -> ansi().fg(Ansi.Color.BLUE).bold().a("  DEBUG").reset().toString();
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

    public static <T extends Enum<?> & ITerminalPickable> void logEnum(Level level, Class<T> clazz, String message) {
        log(level, message != null ? message : "One of " + clazz.getSimpleName());
        Arrays.stream(clazz.getEnumConstants()).forEach(e -> logPrimitive(e.toString() + "(" + e.toPickValue() + ")"));
    }

    public static void logList(Level level, List<?> list, String message) {
        log(level, message != null ? message : "List:");
        list.forEach(i -> logPrimitive(i.toString()));
    }

    public static final String QUESTION_PREFIX = ansi().fg(Ansi.Color.BLUE).a("      ?").reset().toString();
    public static final String PREFIX = "Calc 🤓";
    public static final String INDENT = "       ";
}
