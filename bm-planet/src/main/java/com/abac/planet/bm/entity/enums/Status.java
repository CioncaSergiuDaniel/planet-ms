package com.abac.planet.bm.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Status {

    OK("OK"),
    NOT_OK("!OK"),
    TODO("TODO"),
    EN_ROUTE("En route");

    @Getter
    private String value;
}
