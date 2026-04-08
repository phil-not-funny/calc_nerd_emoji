package com.pnf.calc_nerdemoji.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CalcDynamicField extends CalcValueHolder {
    @JsonProperty
    private float value;
    @JsonProperty
    private CalcValueType type;

    public static OperationResult<CalcDynamicField> createField(float value, String name, CalcValueType type) {
        return OperationResult.from(new CalcDynamicField(name, value, type), OperationResult.Result.CREATED);
    }

    public CalcDynamicField(String name, float value, CalcValueType type) {
        super(name);
        this.value = value;
        this.type = type;
    }

    @JsonCreator
    private CalcDynamicField(
            @JsonProperty("name") String name,
            @JsonProperty("value") float value,
            @JsonProperty("type") CalcValueType type,
            @JsonProperty("categories") List<CalcCategory> categories
    ) {
        super(name, categories);
        this.value = value;
        this.type = type;
    }

    protected void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "%s = %s (%s)".formatted(getName(), value, type.toPickValue());
    }

    @Override
    public float getValue(CalcValueType givenType, CalcContext context) {
        return value * this.type.getMultiplicatorForType(givenType);
    }
}
