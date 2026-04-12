package com.pnf.calc_nerdemoji.controller.questioning;

import com.pnf.calc_nerdemoji.controller.Controller;
import com.pnf.calc_nerdemoji.controller.exceptions.UnknownOptionException;
import com.pnf.calc_nerdemoji.view.Terminal;
import com.pnf.calc_nerdemoji.view.TerminalView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionSet {
    private final List<Question<?>> questions;

    private QuestionSet(List<Question<?>> questions) {
        this.questions = questions;
    }

    public static QuestionSet of(Question<?>... questions) {
        return new QuestionSet(Arrays.asList(questions));
    }

    public static QuestionResult prompt(Controller controller, Question<?>... questions) {
        return new QuestionSet(Arrays.asList(questions)).prompt(controller);
    }

    public static <T> T promptSingleUnsafe(Controller controller, Question<T> question) {
        QuestionResult res = prompt(controller, question);
        return res.getUnsafe(0);
    }

    // Interactive mode — prompts the user for each question
    public QuestionResult prompt(Controller controller) {
        List<Object> values = new ArrayList<>();
        for (Question<?> question : questions) {
            values.add(promptSingle(controller, question));
        }
        return new QuestionResult(values);
    }

    // Inline mode — reads values from args array starting at offset
    public QuestionResult fromArgs(String[] args, int offset) {
        if (args.length - offset < questions.size())
            throw new IllegalArgumentException(
                    "Expected " + questions.size() + " arguments, got " + (args.length - offset)
            );

        List<Object> values = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            values.add(parseSingle(questions.get(i), args[offset + i]));
        }
        return new QuestionResult(values);
    }

    public QuestionResult fromArgs(String[] args) {
        return fromArgs(args, 0);
    }

    private <T> T promptSingle(Controller controller, Question<T> question) {
        if (question.getBeforePrompt() != null)
            question.getBeforePrompt().run();
        while (true) {
            String prompt = formatPrompt(question);
            String input = controller.input(prompt);

            if (input.isBlank() && question.hasDefault())
                return question.getDefaultValue();
            else if (input.isBlank()) {
                Terminal.log(Terminal.Level.ERROR, "Value cannot be empty.");
                continue;
            }

            try {
                return parse(question, input);
            } catch (IllegalArgumentException e) {
                Terminal.log(Terminal.Level.ERROR, e.getMessage());
            }
        }
    }

    private <T> T parseSingle(Question<T> question, String arg) {
        if (arg.isEmpty() && question.hasDefault())
            return question.getDefaultValue();
        return parse(question, arg); // throws if invalid, let command handle it
    }

    private String formatPrompt(Question<?> question) {
        String defaultPart = question.hasDefault() ? "(%s)".formatted(question.getDefaultValue()) : "";
        if (question.getQuestionClass().isEnum())
            return "%s > ".formatted(Terminal.QUESTION_PREFIX);
        else
            return "%s > # %s (%s) > %s".formatted(
                    Terminal.QUESTION_PREFIX,
                    question.getField(),
                    question.getTypeName(),
                    defaultPart
            );
    }

    private <T> T parse(Question<T> question, String input) {
        try {
            return question.getParser().apply(input);
        } catch (UnknownOptionException e) {
            Terminal.debug("Fallback applied");
            return question.getFallback().apply(input);
        }
    }
}
