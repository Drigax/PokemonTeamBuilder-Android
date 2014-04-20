package com.drigax.teambuilder.model;

import java.util.List;

public class Pokemon {
    private int id;
    private String name;
    private List<Integer> stats;
    private List<ElementalType> types;

    Pokemon(int givenId, String givenName, List<ElementalType> givenTypes, List<Integer> givenStats) {
        id = givenId;
        name = givenName;
        stats = givenStats;
        types = givenTypes;
    }

    public List<ElementalType> getTypes() {
        return types;
    }

    public List<Integer> getStats() {
        return stats;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /*
        returns a pretty print of the pokemon's types
        in format "type1 / type2"
    */
    public String getTypeName() {
        String typeName = "";
        List<ElementalType> typeList = getTypes();
        for (ElementalType type : typeList) {
            if (type.equals(typeList.get(0))) {
                typeName += " / ";
            }
            typeName += type;
        }
        return typeName;
    }

    public String toString() {
        String result = "";
        result += String.format("%s ID#: %d. %s", name, id, stats.toString());
        //result += stat.toString();
        return result;
    }

}
