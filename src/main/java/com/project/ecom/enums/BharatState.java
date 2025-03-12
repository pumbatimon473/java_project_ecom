package com.project.ecom.enums;

public enum BharatState implements State {
    JHARKHAND("Jharkhand"),
    UTTAR_PRADESH("Uttar Pradesh"),
    ASSAM("Assam"),
    NAGALAND("Nagaland");

    // field
    private final String stateName;

    // ctor
    BharatState(String stateName) {
        this.stateName = stateName;
    }

    @Override
    public String getStateName() {
        return this.stateName;
    }
}
