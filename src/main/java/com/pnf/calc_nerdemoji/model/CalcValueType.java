package com.pnf.calc_nerdemoji.model;

import com.pnf.calc_nerdemoji.controller.ITerminalPickable;

public enum CalcValueType implements ITerminalPickable {
    MONTHLY(1f),
    QUARTERLY(3f),
    YEARLY(12f),
    PER_SALARY(12f / 14f),
    STATIC(1f);

    public final float relativeMultiplicator;

    CalcValueType(float relativeMultiplicator) {
        this.relativeMultiplicator = relativeMultiplicator;
    }

    public float getMultiplicatorForType(CalcValueType type) {
        if (type == STATIC) return 1f;
        return 1f / this.relativeMultiplicator * type.relativeMultiplicator;
    }

    @Override
    public String toPickValue() {
        return Character.toString(name().charAt(0)).toLowerCase();
    }
}
