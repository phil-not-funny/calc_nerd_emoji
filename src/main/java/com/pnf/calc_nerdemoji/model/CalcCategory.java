package com.pnf.calc_nerdemoji.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pnf.calc_nerdemoji.controller.ITerminalPickable;

import java.util.Objects;

public record CalcCategory(@JsonProperty String name) implements ITerminalPickable {
    public static CalcCategory register(CalcContext context, String name) {
        CalcCategory c = new CalcCategory(name);
        context.addCategory(c);
        return c;
    }

    @JsonCreator
    public CalcCategory(@JsonProperty("name") String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CalcCategory that = (CalcCategory) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public String toPickValue() {
        return name;
    }
}
