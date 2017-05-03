package com.cat.dict;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public enum GenderEnum {
    UNKNOWN(0, "未知"),
    MALE(1, "男性"),
    FEMALE(2, "女性");

    private static final Map<String, GenderEnum> MAP = new HashMap<>();

    static {
        for (GenderEnum genderEnum : values()) {
            MAP.put(genderEnum.getDescription(), genderEnum);
        }
    }

    private final int index;
    private final String description;

    public static GenderEnum from(String description) {
        return MAP.get(description);
    }

}
