package com.project.ecom.utils;

import com.project.ecom.enums.BharatState;
import com.project.ecom.enums.Country;
import com.project.ecom.enums.State;

public class StateConverter {
    public static State getState(Country country, String stateName) {
        switch (country) {
            case BHARAT:
                return BharatState.valueOf(stateName.toUpperCase().trim());
            default:
                throw new IllegalArgumentException("Unsupported country: " + country);
        }
    }
}
