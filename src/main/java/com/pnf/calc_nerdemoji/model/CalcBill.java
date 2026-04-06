package com.pnf.calc_nerdemoji.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pnf.calc_nerdemoji.view.Terminal;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CalcBill {
    @JsonProperty
    private final List<CalcDynamicField> fields;

    public CalcBill() {
        fields = new ArrayList<>();
    }

    @JsonCreator
    private CalcBill(@JsonProperty("fields") List<CalcDynamicField> fields) {
        this.fields = fields;
    }

    public OperationResult<Boolean> addField(CalcDynamicField field) {
        return OperationResult.from(fields.add(field), OperationResult.Result.SUCCESS);
    }

    public double sum(CalcValueType type) {
        double sum = 0;
        for(CalcDynamicField f : fields) {
           sum += f.getValue(type);
        }
        return new BigDecimal(sum).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public List<CalcDynamicField> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        return "CalcBill{" +
                "fields=" + fields +
                '}';
    }
}
