package com.pnf.calc_nerdemoji.controller.commands;

import com.pnf.calc_nerdemoji.controller.Memory;
import com.pnf.calc_nerdemoji.controller.Controller;
import com.pnf.calc_nerdemoji.controller.questioning.Question;
import com.pnf.calc_nerdemoji.controller.questioning.QuestionSet;
import com.pnf.calc_nerdemoji.model.CalcBill;
import com.pnf.calc_nerdemoji.view.Terminal;

import java.util.*;
import java.util.stream.Collectors;

public class CommandHelper {
    protected static CalcBill questionBill(Controller controller) {
        String billName = QuestionSet.promptSingleUnsafe(
                controller,
                Question.ofString(
                        "Bill name",
                        controller.getDefaultBillName())
        );

        return controller.getContext().getBill(billName).announcedValue();
    }

    protected static boolean requireTrue(boolean b, String message) {
        if (!b) Terminal.error(message);
        return b;
    }

    private static void logArgsLengthErrorMessage(String[] args, int[] lengths) {
        Terminal.error("Required an argument length of " + Arrays.stream(lengths).mapToObj(Integer::toString).collect(Collectors.joining(", ")) + " but got " + args.length);
    }

    protected static boolean requireArgsLength(String[] args, int length) {
        if (args.length >= length) return true;
        Terminal.error("Required an argument length of " + length + " but got " + args.length);
        return false;
    }

    protected static boolean requireArgsLength(String[] args, int... lengths) {
        if(Arrays.stream(lengths).anyMatch(l -> l == args.length)) return true;
        logArgsLengthErrorMessage(args, lengths);
        return false;
    }

    protected static boolean requireArgsLength(String[] args, Map<Character, Integer> modifierAdditions, int... lengths) {
        List<Integer> additions = new ArrayList<>(modifierAdditions.values());
        Set<Integer> validLengths = Arrays.stream(lengths).boxed().collect(Collectors.toCollection(HashSet::new));

        // generate all subsets of modifier additions and add their sums to each base length
        for (int mask = 0; mask < (1 << additions.size()); mask++) {
            int modifierSum = 0;
            for (int i = 0; i < additions.size(); i++)
                if ((mask & (1 << i)) != 0) modifierSum += additions.get(i);

            for (int length : lengths)
                validLengths.add(length + modifierSum);
        }

        if (validLengths.contains(args.length)) return true;
        logArgsLengthErrorMessage(args, lengths);
        return false;
    }

    protected static boolean requireValidModifiers(char[] given, char... actual) {
        if (given.length == 0) return true;

        boolean any = false;
        for (char g : given) {
            boolean valid = false;
            for (char a : actual) {
                if (g == a) {
                    valid = true;
                    any = true;
                    break;
                }
            }

            if (!valid)
                Terminal.warn("Unknown modifier: '%s'".formatted(g));
        }
        if (!any)
            Terminal.error("No modifiers matched! Aborting command... Try \"help\".");
        return any;
    }

    protected static boolean hasModifier(char[] given, char desired) {
        return String.valueOf(given).indexOf(desired) >= 0;
    }
}
