package com.drigax.teambuilder.model;

import junit.framework.Assert;

/**
 * Created by drigax on 3/21/14.
 */
public enum GenerationType {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6);

    private int value;

    private GenerationType(int generation) {
        this.value = generation;
    }

    public static GenerationType getGeneration(Pokemon poke) {
        int id = poke.getId();
        if (151 >= id) {
            return GenerationType.ONE;
        } else if (251 >= id) {
            return GenerationType.TWO;
        } else if (386 >= id) {
            return GenerationType.THREE;
        } else if (493 >= id) {
            return GenerationType.FOUR;
        } else if (649 >= id) {
            return GenerationType.FIVE;
        } else if (719 >= id) {
            return GenerationType.SIX;
        } else {
            System.err.format("invalid id %d. Exiting...", id);
            Assert.assertTrue(false);
            return null;
        }
    }
}
