package com.project.ecom.enums;

import lombok.Getter;

@Getter
public enum Country {
    BHARAT(BharatState.class);

    // field
    private final Class<? extends State> stateClass;

    // ctor
    Country(Class<? extends State> stateClass) {
        this.stateClass = stateClass;
    }
}
