package com.pnf.calc_nerdemoji.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CalcBill {
    @JsonProperty
    private final List<CalcValueHolder> fields;

    @JsonProperty
    private final String name;

    public CalcBill(String name) {
        fields = new ArrayList<>();
        this.name = name;
    }

    @JsonCreator
    private CalcBill(@JsonProperty("fields") List<CalcValueHolder> fields, @JsonProperty("name") String name) {
        this.fields = fields;
        this.name = name;
    }

    public OperationResult<Boolean> addField(CalcValueHolder field) {
        return OperationResult.from(fields.add(field), OperationResult.Result.SUCCESS);
    }

    public double sum(CalcValueType type, CalcContext context) {
        double sum = 0;
        for (CalcValueHolder f : fields) {
            sum += f.getValue(type, context);
        }
        return new BigDecimal(sum).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public boolean containsCircularReference() {
        for (CalcValueHolder field : fields) {
            if(field instanceof CalcBillReferenceField &&
                    ((CalcBillReferenceField) field).getCalcBillKey().equalsIgnoreCase(name))
                return true;
        }
        return false;
    }

    public List<CalcValueHolder> getFields() {
        return fields;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CalcBill{" +
                "fields=" + fields +
                '}';
    }
}
