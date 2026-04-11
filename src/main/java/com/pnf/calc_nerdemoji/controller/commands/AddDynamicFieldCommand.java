package com.pnf.calc_nerdemoji.controller.commands;

import com.pnf.calc_nerdemoji.controller.Controller;
import com.pnf.calc_nerdemoji.controller.questioning.Question;
import com.pnf.calc_nerdemoji.controller.questioning.QuestionResult;
import com.pnf.calc_nerdemoji.controller.questioning.QuestionSet;
import com.pnf.calc_nerdemoji.model.CalcBill;
import com.pnf.calc_nerdemoji.model.CalcDynamicField;
import com.pnf.calc_nerdemoji.model.CalcValueType;

import java.util.List;

public class AddDynamicFieldCommand implements ICommandRunnable {

    @Override
    public boolean run(Controller controller, String[] args, char[] modifiers) {
        QuestionSet questions = QuestionSet.of(
                Question.ofString("Field Name"),
                Question.ofFloat("Field Value"),
                Question.ofEnum("Field Type", CalcValueType.class)
        );

        QuestionResult result = switch (args.length) {
            case 0 -> questions.prompt(controller);
            case 3 -> questions.fromArgs(args);
            default -> QuestionResult.empty();
        };

        String name = result.get(0, String.class);
        float amount = result.get(1, Float.class);
        CalcValueType type = result.get(2, CalcValueType.class);

        CalcDynamicField field = CalcDynamicField.createField(amount, name, type).announcedValue();

        CalcBill bill = CommandHelper.questionBill(controller);
        bill.addField(field);

        controller.save();
        return true;
    }

    @Override
    public boolean preChecks(Controller controller, String[] args, char[] modifiers) {
        return CommandHelper.requireArgsLength(args, 0, 3);
    }

    @Override
    public CommandHelp help() {
        return CommandHelp.of("Adds a new field to a bill", List.of("add_field", "add_field <field-name> <field-value> <field-type>"), List.of("add_field Rent 750.43 m"));
    }
}
