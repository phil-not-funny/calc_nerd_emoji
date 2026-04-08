package com.pnf.calc_nerdemoji.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "holderType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CalcDynamicField.class, name = "dynamic_field"),
        @JsonSubTypes.Type(value = CalcBillReferenceField.class, name = "bill_ref")
})
public abstract class CalcValueHolder {
    @JsonProperty
    private final String name;
    @JsonProperty
    private List<CalcCategory> categories;

    @JsonCreator
    protected CalcValueHolder(@JsonProperty("name") String name, @JsonProperty("categories") List<CalcCategory> categories) {
        this.name = name;
        this.categories = categories;
    }

    protected CalcValueHolder(String name) {
        this.name = name;
        categories = new ArrayList<>();
    }

    abstract float getValue(CalcValueType type, CalcContext context);

    public String getName() {
        return name;
    }

    public void addCategory(CalcCategory category) {
        categories.add(category);
    }

    public List<CalcCategory> getCategories() {
        return categories;
    }
}
