package com.pnf.calc_nerdemoji.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class CalcContext {
    public static CalcContext empty() {
        return new CalcContext();
    }

    @JsonProperty
    private Map<String, CalcBill> bills = new HashMap<>();

    public OperationResult<CalcBill> getBill(String name) {
        Optional<CalcBill> bill = tryGetBill(name);
        if(bill.isEmpty()) {
            CalcBill c = new CalcBill();
            bills.put(name, c);
            return OperationResult.from(c, OperationResult.Result.CREATED);
        } else return OperationResult.from(bill.get());
    }

    private Optional<CalcBill> tryGetBill(String key) {
        return Optional.ofNullable(bills.get(key));
    }

    @Override
    public String toString() {
        return "CalcContext{" +
                "bills=" + bills +
                '}';
    }
}
