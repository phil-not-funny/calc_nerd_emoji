package com.pnf.calc_nerdemoji.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CalcDynamicField implements ICalcValueHolder {
    @JsonProperty
    private float value;
    @JsonProperty
    private final String name;
    @JsonProperty
    private CalcValueType type;
    @JsonProperty
    private List<CalcCategory> category;

    public static OperationResult<CalcDynamicField> createField(float value, String name, CalcValueType type, CalcCategory... categories) {
        return OperationResult.from(new CalcDynamicField(value, name, type, categories), OperationResult.Result.CREATED);
    }

    public CalcDynamicField(float value, String name, CalcValueType type, CalcCategory... categories) {
        this.value = value;
        this.name = name;
        this.type = type;
        this.category = List.of(categories);
    }

    @JsonCreator
    private CalcDynamicField(
            @JsonProperty("name") String name,
            @JsonProperty("value") float value,
            @JsonProperty("type") CalcValueType type,
            @JsonProperty("category") List<CalcCategory> category
    ) {
        this.name = name;
        this.value = value;
        this.type = type;
        this.category = category;
    }

    protected void setValue(int value) {
        this.value = value;
    }

    public List<CalcCategory> getCategory() {
        return category;
    }

    public void setCategory(List<CalcCategory> category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "%s = %s (%s)".formatted(name, value, type.toPickValue());
    }

    @Override
    public float getValue(CalcValueType givenType, CalcContext context) {
        return value * this.type.getMultiplicatorForType(givenType);
    }
}
