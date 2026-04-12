package com.pnf.calc_nerdemoji.controller.commands;

import com.pnf.calc_nerdemoji.controller.Controller;
import com.pnf.calc_nerdemoji.controller.questioning.Question;
import com.pnf.calc_nerdemoji.controller.questioning.QuestionResult;
import com.pnf.calc_nerdemoji.controller.questioning.QuestionSet;
import com.pnf.calc_nerdemoji.model.CalcBill;
import com.pnf.calc_nerdemoji.model.CalcCategory;
import com.pnf.calc_nerdemoji.model.CalcValueHolder;
import com.pnf.calc_nerdemoji.view.Terminal;

public class CategorizeCommand implements ICommandRunnable {
    @Override
    public boolean run(Controller controller, String[] args, char[] modifiers) {
        final boolean r = CommandHelper.hasModifier(modifiers, 'r');
        final boolean s = CommandHelper.hasModifier(modifiers, 's');
        final boolean i = CommandHelper.hasModifier(modifiers, 'i');

        QuestionSet set = QuestionSet.of(
                Question.ofListEntry("Category", controller.getContext().getCategories(), (in) -> CalcCategory.register(controller.getContext(), in), CalcCategory.class),
                Question.ofString(r ? "Regex" : "Search String")
        );

        QuestionResult result = switch (args.length) {
            case 0 -> set.prompt(controller);
            case 2 -> set.fromArgs(args);
            default -> QuestionResult.empty();
        };

        CalcBill bill = CommandHelper.questionBill(controller);

        CalcCategory category = result.get(0, CalcCategory.class);
        String search = result.get(1, String.class);

        Terminal.debug(category.toString());

        long affected = bill.getFields().stream()
                .filter(field -> isFieldValid(field, search, r, s, i))
                .peek(field -> field.addCategory(category))
                .count();

        Terminal.info("%s fields affected".formatted(affected));

        controller.save();
        return true;
    }

    private boolean isFieldValid(CalcValueHolder field, String search, boolean r, boolean s, boolean i) {
        String v1 = i ? field.getName().toLowerCase() : field.getName();
        String v2 = i && !r ? search.toLowerCase() : search;
        if (s && v1.startsWith(v2)) return true;
        if (r && v1.matches(v2)) return true;
        return v1.equals(v2);
    }

    @Override
    public boolean preChecks(Controller controller, String[] args, char[] modifiers) {
        return CommandHelper.requireArgsLength(args, 0, 2) &&
                CommandHelper.requireValidModifiers(modifiers, 'r', 's', 'i');
    }

    @Override
    public CommandHelp help() {
        return null;
    }
}
