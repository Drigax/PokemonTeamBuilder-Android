package com.drigax.teambuilder.model;

import junit.framework.Assert;

import java.util.Arrays;
import java.util.List;

/**
 * Created by drigax on 3/21/14.
 */
public enum ElementalType {
    NORMAL("normal"),
    FIRE("fire"),
    WATER("water"),
    FIGHTING("fighting"),
    FLYING("flying"),
    GRASS("grass"),
    POISON("poison"),
    ELECTRIC("electric"),
    GROUND("ground"),
    PSYCHIC("psychic"),
    ROCK("rock"),
    ICE("ice"),
    BUG("bug"),
    DRAGON("dragon"),
    GHOST("ghost"),
    DARK("dark"),
    STEEL("steel"),
    FAIRY("fairy");

    private String value;

    private ElementalType(String typeName) {
        this.value = typeName;
    }

    public static ElementalType parseString(String elementName) {
        if (elementName.equals("normal")) {
            return ElementalType.NORMAL;
        } else if (elementName.equals("fire")) {
            return ElementalType.FIRE;
        } else if (elementName.equals("water")) {
            return ElementalType.WATER;
        } else if (elementName.equals("fighting")) {
            return ElementalType.FIGHTING;
        } else if (elementName.equals("flying")) {
            return ElementalType.FLYING;
        } else if (elementName.equals("grass")) {
            return ElementalType.GRASS;
        } else if (elementName.equals("poison")) {
            return ElementalType.POISON;
        } else if (elementName.equals("electric")) {
            return ElementalType.ELECTRIC;
        } else if (elementName.equals("ground")) {
            return ElementalType.GROUND;
        } else if (elementName.equals("psychic")) {
            return ElementalType.PSYCHIC;
        } else if (elementName.equals("rock")) {
            return ElementalType.ROCK;
        } else if (elementName.equals("ice")) {
            return ElementalType.ICE;
        } else if (elementName.equals("bug")) {
            return ElementalType.BUG;
        } else if (elementName.equals("dragon")) {
            return ElementalType.DRAGON;
        } else if (elementName.equals("ghost")) {
            return ElementalType.GHOST;
        } else if (elementName.equals("dark")) {
            return ElementalType.DARK;
        } else if (elementName.equals("steel")) {
            return ElementalType.STEEL;
        } else if (elementName.equals("fairy")) {
            return ElementalType.FAIRY;
        } else {
            //do nothing.
        }
        Assert.assertTrue(false); //this shouldnt happen.
        return null;
    }

    public static List<ElementalType> getAllElements() {
        return Arrays.asList(ElementalType.values());
    }

    @Override
    public String toString() {
        return value;
    }
}
