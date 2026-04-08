package com.pnf.calc_nerdemoji.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "holderType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CalcDynamicField.class, name = "dynamic_field"),
        @JsonSubTypes.Type(value = CalcBillReferenceField.class, name = "bill_ref")
})
public interface ICalcValueHolder {
    float getValue(CalcValueType type, CalcContext context);
}
