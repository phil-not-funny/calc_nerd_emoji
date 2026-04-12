package com.pnf.calc_nerdemoji.model;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "holderType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CalcDynamicField.class, name = "dynamic_field"),
        @JsonSubTypes.Type(value = CalcBillReferenceField.class, name = "bill_ref")
})
public abstract class CalcValueHolder {
    @JsonProperty
    private final String name;
    @JsonProperty
    private final List<String> categories;
    @JsonIgnore
    private List<CalcCategory> resolvedCategories = new ArrayList<>();

    @JsonCreator
    protected CalcValueHolder(@JsonProperty("name") String name, @JsonProperty("categories") List<String> categories) {
        this.name = name;
        this.categories = categories;
    }

    protected CalcValueHolder(String name) {
        this.name = name;
        categories = new ArrayList<>();
    }

    protected CalcValueHolder(String name, List<String> categories, List<CalcCategory> resolvedCategories) {
        this.name = name;
        this.categories = categories;
        this.resolvedCategories = resolvedCategories;
    }

    public List<CalcCategory> resolveCategories(CalcContext context) {
        return resolvedCategories = new ArrayList<>(categories.stream()
                .map(context::tryGetCategory)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList());
    }

    abstract float getValue(CalcValueType type, CalcContext context);

    public String getName() {
        return name;
    }

    public void addCategory(CalcCategory category) {
        categories.add(category.name());
        resolvedCategories.add(category);
    }

    @JsonIgnore
    public List<CalcCategory> getCategories() {
        return resolvedCategories;
    }

    public List<CalcCategory> getFilteredCategories(CalcCategory category) {
        return resolvedCategories.stream().filter(c -> c.equals(category)).toList();
    }

    public boolean hasCategory(CalcCategory category) {
        return resolvedCategories.stream().anyMatch(c -> c.equals(category));
    }
}
