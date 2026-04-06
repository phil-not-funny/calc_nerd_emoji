package com.pnf.calc_nerdemoji.controller.questioning;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.pnf.calc_nerdemoji.controller.ITerminalPickable;
import com.pnf.calc_nerdemoji.view.Terminal;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Question<T> {
    private final String field;
    private final String typeName;
    private final T defaultValue;
    private final Function<String, T> parser;
    private final Runnable beforePrompt;
    private final Class<T> questionClass;

    private Question(String field, String typeName, T defaultValue, Function<String, T> parser, Runnable beforePrompt, Class<T> questionClass) {
        this.field = field;
        this.typeName = typeName;
        this.defaultValue = defaultValue;
        this.parser = parser;
        this.beforePrompt = beforePrompt;
        this.questionClass = questionClass;
    }

    public static Question<String> ofString(String field) {
        return new Question<>(field, "Text", null, s -> s, null, String.class);
    }

    public static Question<String> ofString(String field, String defaultValue) {
        return new Question<>(field, "Text", defaultValue, s -> s, null, String.class);
    }

    private static int validateInt(String s) {
        try { return Integer.parseInt(s); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("Invalid Integer: " + s); }
    }

    public static Question<Integer> ofInt(String field) {
        return new Question<>(field, "Integer", null, Question::validateInt, null, Integer.class);
    }

    public static Question<Integer> ofInt(String field, int defaultValue) {
        return new Question<>(field, "Integer", defaultValue, Question::validateInt, null, Integer.class);
    }

    private static float validateFloat(String s) {
        try { return Float.parseFloat(s); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("Invalid Float: " + s); }
    }

    public static Question<Float> ofFloat(String field) {
        return new Question<>(field, "Number", null, Question::validateFloat, null, Float.class);
    }

    public static Question<Float> ofFloat(String field, float defaultValue) {
        return new Question<>(field, "Number", defaultValue, Question::validateFloat, null, Float.class);
    }

    private static <T extends Enum<?> & ITerminalPickable> T validateEnum(String s, Class<T> clazz) {
        return  Arrays.stream(clazz.getEnumConstants())
                .filter(e -> e.toPickValue().equalsIgnoreCase(s))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid option: " + s));
    }

    public static <T extends Enum<?> & ITerminalPickable> Question<T> ofEnum(String field, Class<T> clazz) {
        return new Question<>(
                field,
                "Option",
                null,
                (s) -> validateEnum(s, clazz),
                () -> Terminal.logEnum(Terminal.Level.QUESTION, clazz, "# %s (Select one of:)".formatted(field)),
                clazz
        );
    }

    public static <T extends Enum<?> & ITerminalPickable> Question<T> ofEnum(String field, Class<T> clazz, T defaultValue) {
        return new Question<>(
                field,
                "Option",
                defaultValue,
                (s) -> validateEnum(s, clazz),
                () -> Terminal.logEnum(Terminal.Level.QUESTION, clazz, "# %s (Select):".formatted(field)),
                clazz
        );
    }

    private static boolean validateBoolean(String s) {
        return switch (s.trim().toLowerCase()) {
            case "yes", "y" -> true;
            case "no", "n" -> false;
            default -> throw new IllegalArgumentException("Please enter (y)es or (n)o.");
        };
    }

    public static Question<Boolean> ofBoolean(String field) {
        return new Question<>(field, "Yes/No", null, Question::validateBoolean, null, Boolean.class);
    }

    public static Question<Boolean> ofBoolean(String field, boolean defaultValue) {
        return new Question<>(field, "Yes/No", defaultValue, Question::validateBoolean, null, Boolean.class);
    }

    public String getField() {
        return field;
    }

    public String getTypeName() {
        return typeName;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public boolean hasDefault() {
        return defaultValue != null;
    }

    public Function<String, T> getParser() {
        return parser;
    }

    public Runnable getBeforePrompt() {
        return beforePrompt;
    }

    public Class<T> getQuestionClass() {
        return questionClass;
    }
}
