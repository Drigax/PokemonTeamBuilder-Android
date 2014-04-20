package com.drigax.teambuilder.model;

import java.util.Locale;

/**
 * Created by drigax on 3/22/14.
 */
public enum Effectiveness {
    QUARTER_DAMAGE(-2),
    HALF_DAMAGE(-1),
    NO_DAMAGE(0),
    NORMAL_DAMAGE(1),
    DOUBLE_DAMAGE(2),
    QUAD_DAMAGE(4);

    private int value;

    private Effectiveness(int effectiveValue) {
        this.value = effectiveValue;
    }

    public static Effectiveness parseString(String effectivenessString) {
        int effectiveValue = Integer.parseInt(effectivenessString);

        switch (effectiveValue) {
            case (-2):
                return QUARTER_DAMAGE;
            case (-1):
                return HALF_DAMAGE;
            case (0):
                return NO_DAMAGE;
            case (1):
                return NORMAL_DAMAGE;
            case (2):
                return DOUBLE_DAMAGE;
            case (4):
                return QUAD_DAMAGE;
            default:
                System.err.format(Locale.ENGLISH, "invalid value %d", effectiveValue);
                return null;
        }
    }
}
