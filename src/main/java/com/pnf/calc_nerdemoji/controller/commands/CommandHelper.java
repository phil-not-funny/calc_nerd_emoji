package com.pnf.calc_nerdemoji.controller.commands;

import com.pnf.calc_nerdemoji.controller.CalcMemory;
import com.pnf.calc_nerdemoji.controller.Controller;
import com.pnf.calc_nerdemoji.controller.questioning.Question;
import com.pnf.calc_nerdemoji.controller.questioning.QuestionResult;
import com.pnf.calc_nerdemoji.controller.questioning.QuestionSet;
import com.pnf.calc_nerdemoji.model.CalcBill;
import com.pnf.calc_nerdemoji.view.Terminal;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CommandHelper {
    protected static CalcBill questionBill(Controller controller) {
        String billName = QuestionSet.promptSingleUnsafe(
                controller,
                Question.ofString(
                        "Bill name",
                        controller.getMemory().get(CalcMemory.LAST_BILL_NAME, "fixkosten", String.class))
        );

        return controller.getContext().getBill(billName).announcedValue();
    }

    protected static boolean requireArgsLength(String[] args, int length) {
        if (args.length >= length) return true;
        Terminal.error("Required an argument length of " + length + " but got " + args.length);
        return false;
    }

    protected static boolean requireArgsLength(String[] args, int... lengths) {
        for (int length : lengths) {
            if (args.length == length) return true;
        }

        Terminal.error("Required an argument length of " + Arrays.stream(lengths).mapToObj(Integer::toString).collect(Collectors.joining(", ")) + " but got " + args.length);
        return false;
    }


}
