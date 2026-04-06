package com.pnf.calc_nerdemoji.controller.commands;

import com.pnf.calc_nerdemoji.controller.Controller;

public interface ICommandRunnable {
    boolean run(Controller controller, String[] args);
    boolean preChecks(Controller controller, String[] args);
    CommandHelp help();

    /**
     * Executes this.run(null)
     */
    default boolean run() {
        return this.run(null, null);
    }
}
