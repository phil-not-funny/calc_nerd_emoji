package com.pnf.calc_nerdemoji.controller.commands;

import com.pnf.calc_nerdemoji.controller.Controller;
import com.pnf.calc_nerdemoji.model.CalcBill;
import com.pnf.calc_nerdemoji.model.CalcContext;
import com.pnf.calc_nerdemoji.model.CalcValueType;
import com.pnf.calc_nerdemoji.model.CalcValueHolder;

import java.util.List;

import static com.pnf.calc_nerdemoji.view.Terminal.logPrimitive;
import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

public class ListFieldsCommand implements ICommandRunnable {
    @Override
    public boolean run(Controller controller, String[] args) {
        CalcContext context = controller.getContext();

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
                    for (CalcValueHolder field : bill.getFields()) {
                        logPrimitive(ansi().fg(GREEN).a("  + ").reset().a(field.toString()).reset().toString());
                    }
                    logPrimitive(ansi().fg(CYAN).a("  Σ ").reset().bold().a("Sum Monthly: " + bill.sum(CalcValueType.MONTHLY, context)).reset().toString());
                }

                System.out.println();
            }
        }

        System.out.println(ansi().fg(CYAN).bold().a("  %sEnd%s".formatted("-".repeat(28), "-".repeat(28))).reset());
        return true;
    }

    @Override
    public boolean preChecks(Controller controller, String[] args) {
        return true;
    }


    @Override
    public CommandHelp help() {
        return CommandHelp.of("Lists all bills and their fields.", List.of("list"));
    }
}
