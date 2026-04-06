package com.pnf.calc_nerdemoji;

import com.pnf.calc_nerdemoji.controller.Controller;
import com.pnf.calc_nerdemoji.model.CalcContext;
import com.pnf.calc_nerdemoji.view.TerminalView;

import java.io.IOException;
import java.util.logging.Logger;

public class Main {
    public static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            var terminal = new TerminalView();
            var controller = new Controller(terminal);

            controller.initApp();
        } catch (IOException e) {
            System.exit(-1);
        }
    }
}
