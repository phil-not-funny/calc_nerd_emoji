package com.pnf.calc_nerdemoji.controller.commands;

import com.pnf.calc_nerdemoji.controller.Controller;
import com.pnf.calc_nerdemoji.controller.questioning.Question;
import com.pnf.calc_nerdemoji.controller.questioning.QuestionSet;
import com.pnf.calc_nerdemoji.model.CalcBill;
import com.pnf.calc_nerdemoji.model.CalcValueType;
import com.pnf.calc_nerdemoji.view.Terminal;

public class SumBillCommand implements ICommandRunnable{
    @Override
    public boolean run(Controller controller, String[] args) {
        CalcBill bill = CommandHelper.questionBill(controller);

        var result = QuestionSet.of(Question.ofEnum("Calculation Type", CalcValueType.class)).prompt(controller);

        Terminal.log(Terminal.Level.INFO, Double.toString(bill.sum(result.get(0, CalcValueType.class), controller.getContext())));
        return true;
    }

    @Override
    public boolean preChecks(Controller controller, String[] args) {
        return true;
    }


    @Override
    public CommandHelp help() {
        return null;
    }
}
