package com.pnf.calc_nerdemoji.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CalcBillReferenceField extends CalcValueHolder {
    @JsonProperty
    private final String calcBillKey;

    @JsonCreator
    private CalcBillReferenceField(@JsonProperty("name") String name, @JsonProperty("calcBillKey") String calcBillKey, @JsonProperty("categories") List<String> categories) {
        super(name, categories);
        this.calcBillKey = calcBillKey;
    }

    public CalcBillReferenceField(String name, @JsonProperty("calcBillKey") String calcBillKey) {
        super(name);
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

    public String getCalcBillKey() {
        return calcBillKey;
    }
}
