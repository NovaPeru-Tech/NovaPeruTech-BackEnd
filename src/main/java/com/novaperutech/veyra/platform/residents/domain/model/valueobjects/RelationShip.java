package com.novaperutech.veyra.platform.residents.domain.model.valueobjects;

import java.util.Arrays;

public enum RelationShip {
    Son(0),
    Daughter( 1),
    Granddaughter(2),
    Grandson(3),
    Brother(4),
    Sister(5);
    private final int code;

    RelationShip(int  code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
    public static RelationShip fromCode(Integer code) {
       return Arrays.stream(values()).filter(stack->stack.getCode()==code)
               .findFirst().orElseThrow(()->new IllegalArgumentException("Invalid code "+code));
    }
}
