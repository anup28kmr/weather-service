package com.ak.common.entity;

import lombok.Getter;

@Getter
public enum PeriodType {
    night("night"),
    day("day");

    private final String value;

    PeriodType(String value) {
        this.value = value;
    }
}