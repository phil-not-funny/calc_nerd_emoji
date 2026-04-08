package com.pnf.calc_nerdemoji.controller.commands;

import com.pnf.calc_nerdemoji.controller.Controller;
import com.pnf.calc_nerdemoji.controller.questioning.Question;
import com.pnf.calc_nerdemoji.controller.questioning.QuestionResult;
import com.pnf.calc_nerdemoji.controller.questioning.QuestionSet;
import com.pnf.calc_nerdemoji.model.CalcBill;
import com.pnf.calc_nerdemoji.model.CalcBillReferenceField;
import com.pnf.calc_nerdemoji.view.Terminal;

import java.util.List;

public class AddBillReferenceCommand implements ICommandRunnable {
    @Override
    public boolean run(Controller controller, String[] args) {
        QuestionSet questions = QuestionSet.of(
                Question.ofString("billReference", controller.getDefaultBillName())
        );

        QuestionResult result = switch (args.length) {
            case 0 -> questions.prompt(controller);
            case 1 -> questions.fromArgs(args);
            default -> QuestionResult.empty();
        };

        String billReference = result.get(0, String.class);
        CalcBill actual = CommandHelper.questionBill(controller);
        if (actual.getName().equalsIgnoreCase(billReference)) {
            Terminal.error("Circular Reference Error: A \"Bill-Reference Field\" may not reference the bill it is in.");
            return false;
        }

        CalcBillReferenceField billRefField = new CalcBillReferenceField("relation", billReference);
        actual.addField(billRefField).announcedValue();
        controller.save();
        return true;
    }

    @Override
    public boolean preChecks(Controller controller, String[] args) {
        return CommandHelper.requireArgsLength(args, 0, 1);
    }

    @Override
    public CommandHelp help() {
        return CommandHelp.of("Adds a new Field referencing another Bill", List.of("add_reference", "add_reference <bill-name>"), List.of("add_reference anotherBill"));
    }
}
