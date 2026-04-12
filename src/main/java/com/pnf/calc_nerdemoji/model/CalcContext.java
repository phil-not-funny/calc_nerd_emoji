package com.pnf.calc_nerdemoji.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class CalcContext {
    public static CalcContext empty() {
        return new CalcContext();
    }

    @JsonProperty
    private List<CalcBill> bills = new ArrayList<>();

    @JsonProperty
    private List<CalcCategory> categories = new ArrayList<>();

    public OperationResult<CalcBill> getBill(String name) {
        Optional<CalcBill> bill = tryGetBill(name);
        if(bill.isEmpty()) {
            CalcBill c = new CalcBill(name);
            bills.add(c);
            return OperationResult.from(c, OperationResult.Result.CREATED);
        } else return OperationResult.from(bill.get());
    }

    public Optional<CalcBill> tryGetBill(String name) {
        for (CalcBill b : bills) {
            if(b.getName().equalsIgnoreCase(name)) return Optional.of(b);
        }
        return Optional.empty();
    }

    public List<CalcBill> getBills() {
        return bills;
    }

    @Override
    public String toString() {
        return "CalcContext{" +
                "bills=" + bills +
                '}';
    }

    public Optional<CalcCategory> tryGetCategory(String name) {
        for (CalcCategory c : categories) {
            if(c.name().equalsIgnoreCase(name)) return Optional.of(c);
        }
        return Optional.empty();
    }

    public void resolveAll() {
        for (CalcBill bill : bills) {
            for (CalcValueHolder field : bill.getFields()) {
                field.resolveCategories(this);
            }
        }
    }

    public List<CalcCategory> getCategories() {
        return categories;
    }

    public void addCategory(CalcCategory c) {
        categories.add(c);
    }
}
