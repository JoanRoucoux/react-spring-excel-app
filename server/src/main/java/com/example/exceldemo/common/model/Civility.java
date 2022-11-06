package com.example.exceldemo.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static java.util.Arrays.stream;

@Getter
@AllArgsConstructor
public enum Civility {

    MR("Mr"),
    MS("Ms");

    private final String value;

    public static Civility findByValue(String value) {
        return stream(values())
                .filter(civility -> civility.getValue().equals(value))
                .findAny().orElse(null);
    }

}
