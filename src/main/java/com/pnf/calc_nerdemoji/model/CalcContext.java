package com.pnf.calc_nerdemoji.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class CalcContext {
    public static CalcContext empty() {
        return new CalcContext();
    }

    @JsonProperty
    private List<CalcBill> bills = new ArrayList<>();

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
}
