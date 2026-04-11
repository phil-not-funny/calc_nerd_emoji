package com.pnf.calc_nerdemoji.controller.commands;

import com.pnf.calc_nerdemoji.controller.Controller;

public interface ICommandRunnable {
    boolean run(Controller controller, String[] args, char[] modifiers);
    boolean preChecks(Controller controller, String[] args, char[] modifiers);
    CommandHelp help();

    /**
     * Executes this.run(null)
     */
    default boolean run() {
        return this.run(null, null, null);
    }
}
