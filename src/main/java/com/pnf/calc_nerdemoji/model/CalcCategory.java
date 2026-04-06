package com.pnf.calc_nerdemoji.model;

import com.pnf.calc_nerdemoji.controller.ITerminalPickable;

public enum CalcCategory implements ITerminalPickable {
    HOME, INSURANCE, SUBSCRIPTIONS, OTHER;

    @Override
    public String toPickValue() {
        return Character.toString(name().charAt(0)).toLowerCase();
    }
}
