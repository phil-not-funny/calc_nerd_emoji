package com.pnf.calc_nerdemoji.controller.commands;

import com.pnf.calc_nerdemoji.controller.Controller;
import com.pnf.calc_nerdemoji.controller.questioning.Question;
import com.pnf.calc_nerdemoji.controller.questioning.QuestionResult;
import com.pnf.calc_nerdemoji.controller.questioning.QuestionSet;
import com.pnf.calc_nerdemoji.model.*;

import java.util.List;
import java.util.Map;

import static com.pnf.calc_nerdemoji.view.Terminal.logPrimitive;
import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

public class ListFieldsCommand implements ICommandRunnable {
    @Override
    public boolean run(Controller controller, String[] args, char[] modifiers) {
        boolean c = CommandHelper.hasModifier(modifiers, 'c');
        boolean s = CommandHelper.hasModifier(modifiers, 's');
        CalcContext context = controller.getContext();

        CalcCategory category;
        if(c) {
            QuestionSet set = QuestionSet.of(
                    Question.ofListEntry("Category", context.getCategories(), null, CalcCategory.class)
            );

            QuestionResult result = switch (args.length) {
                case 0 -> set.prompt(controller);
                case 1 -> set.fromArgs(args);
                default -> QuestionResult.empty();
            };

            category = result.get(0, CalcCategory.class);
        } else category = null;

        System.out.println(ansi().fg(CYAN).bold().a("  Bills & Fields").reset());
        System.out.println(ansi().fg(CYAN).bold().a("  " + "-".repeat(60)).reset());

        if (context.getBills().isEmpty()) {
            logPrimitive(ansi().fg(YELLOW).a("No bills found.").reset().toString());
        } else {
            for (CalcBill bill : context.getBills()) {

                // Bill name + field count
                System.out.println(ansi().a("     ").fg(YELLOW).bold().a(bill.getName()).reset().a("  [" + bill.getFields().size() + " field(s)]").reset());

                if (bill.getFields().isEmpty()) {
                    logPrimitive(ansi().a("No fields.").reset().toString());
                } else {
                    for (CalcValueHolder field : bill.getFields().stream().filter(cat -> category == null || cat.hasCategory(category)).toList()) {
                        logPrimitive(ansi().fg(GREEN).a("  + ").reset().a(field.toString()).reset().toString());
                    }
                    if(s)
                        logPrimitive(ansi().fg(CYAN).a("  Σ ").reset().bold().a("Sum Monthly: " + bill.sum(CalcValueType.MONTHLY, context, category)).reset().toString());
                }

                System.out.println();
            }
        }

        System.out.println(ansi().fg(CYAN).bold().a("  %sEnd%s".formatted("-".repeat(28), "-".repeat(28))).reset());
        return true;
    }

    @Override
    public boolean preChecks(Controller controller, String[] args, char[] modifiers) {
        return CommandHelper.requireValidModifiers(modifiers, 'c', 's')
                && CommandHelper.requireArgsLength(args, Map.of('c', 1), 0);
    }

    @Override
    public CommandHelp help() {
        return CommandHelp.of("Lists all bills and their fields.", List.of("list"));
    }
}
