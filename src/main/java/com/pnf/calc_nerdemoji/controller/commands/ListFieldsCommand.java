package com.pnf.calc_nerdemoji.controller.commands;

import com.pnf.calc_nerdemoji.controller.Controller;
import com.pnf.calc_nerdemoji.model.CalcBill;
import com.pnf.calc_nerdemoji.view.Terminal;

public class ListFieldsCommand implements ICommandRunnable {
    @Override
    public boolean run(Controller controller, String[] args) {
        CalcBill bill = CommandHelper.questionBill(controller);

        Terminal.logList(Terminal.Level.INFO, bill.getFields(), "Current Fields");
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
