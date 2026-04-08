package com.pnf.calc_nerdemoji.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CalcBillReferenceField(@JsonProperty String calcBillKey) implements ICalcValueHolder {
    @JsonCreator
    public CalcBillReferenceField(@JsonProperty("calcBillKey") String calcBillKey) {
        this.calcBillKey = calcBillKey;
    }

    @Override
    public float getValue(CalcValueType type, CalcContext context) {
        var bill = context.tryGetBill(calcBillKey);
        if (bill.isEmpty()) return 0f;

        if (bill.get().containsCircularReference())
            throw new IllegalStateException("CalcBillReferenceField contains circular reference to " + calcBillKey);

        return (float) bill.get().sum(type, context);
    }

    @Override
    public String toString() {
        return "%s (bill reference)".formatted(calcBillKey);
    }
}
