package com.drigax.teambuilder.model;

import android.content.Context;

import com.drigax.teambuilder.R;

import junit.framework.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Pokedex {

    public static InputStream pokemonList;
    public static InputStream elementList;
    private static Pokedex currentInstance;
    private static Scanner inputStream;
    private Hashtable<Integer, Pokemon> database;
    private Hashtable<ElementalType, Hashtable<ElementalType, Effectiveness>> elements;

    /**
     * Constructor for Pokedex object.
     * Reads pokemon values and stats from local CSV file and stores into hash table.
     * file is not to be accessed by user, so all input is of correct format.
     */
    public Pokedex() {
        database = new Hashtable<Integer, Pokemon>();

        elements = new Hashtable<ElementalType, Hashtable<ElementalType, Effectiveness>>();

    }

    public static Pokedex getInstance(Context context) {
        if (currentInstance == null) {
            currentInstance = new Pokedex();
            try {
                currentInstance.pokemonList = context.getAssets().open(context.getString(R.string.pokemonListPath));
                currentInstance.elementList = context.getAssets().open(context.getString(R.string.elementListPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            currentInstance.initializePokemon();
            currentInstance.initializeElements();
        }
        return currentInstance;
    }

    public static void main(String[] args) {

    }

    /**
     * initializePokemon()
     * <p/>
     * initializes a Scanner object to read in all the pokemon information from
     */
    public void initializePokemon() {
        int currentId;
        String currentName;
        List<ElementalType> currentTypes;
        List<Integer> currentStats;
        try {
            inputStream = new Scanner(pokemonList);
            inputStream.nextLine(); //skips first, non formatted line.
            while (inputStream.hasNextLine()) {
                String rawData = inputStream.nextLine();
                String[] rawDataArray = rawData.split("	");
                currentId = Integer.parseInt(rawDataArray[0]);
                currentName = rawDataArray[1];
                //WARNING: the next line of code is awesome.
                currentTypes = parseType(rawDataArray[2]);
                currentStats = parseStats(Arrays.asList(rawDataArray).subList(3, 9));
                //System.out.println(currentStats.toString());

                database.put(currentId, new Pokemon(currentId, currentName, currentTypes, currentStats));
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //printDatabase();
    }

    public void initializeElements() {
        try {
            inputStream = new Scanner(elementList);
            ArrayList<String> rawDefenseType = new ArrayList<String>(Arrays.asList(inputStream.nextLine().split("	")));
            rawDefenseType.remove(0);
            ArrayList<ElementalType> defenseType = (ArrayList<ElementalType>) createElementalTypeEnumList((List<String>) rawDefenseType);
            //System.out.println(defenseType.toString());
            ArrayList<String> rawData;
            String rawAttackType;
            ElementalType currentAttackType;

            int index;
            while (inputStream.hasNextLine()) {
                String[] rawDataArray = inputStream.nextLine().split("	");
                //System.out.println(rawDataArray[0]);
                rawData = new ArrayList<String>(Arrays.asList(rawDataArray));
                rawAttackType = rawData.remove(0); //The first element is the attacking type
                Hashtable<ElementalType, Effectiveness> currentAttackTypeTable = new Hashtable<ElementalType, Effectiveness>();
                index = 0;
                for (String effectiveness : rawData) {
                    currentAttackTypeTable.put(defenseType.get(index), Effectiveness.parseString(effectiveness));
                    index++;
                }
                currentAttackType = ElementalType.parseString(rawAttackType);
                //System.out.println(currentAttackType);
                //System.out.println(currentAttackType.toString());
                elements.put(currentAttackType, currentAttackTypeTable);
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<ElementalType> createElementalTypeEnumList(List<String> rawTypeList) {
        List<ElementalType> eleList = new ArrayList<ElementalType>();
        for (String rawType : rawTypeList) {
            for (ElementalType eleType : ElementalType.values()) {
                if (eleType.equals(ElementalType.parseString(rawType))) {
                    eleList.add(eleType);
                }
            }
        }
        Assert.assertEquals(rawTypeList.size(), eleList.size());
        return eleList;
    }

    public Pokemon getPokemon(int id) {
        return database.get(id);
    }

    public List<Pokemon> getAllPokemon() {
        ArrayList<Pokemon> allPokemon = new ArrayList(getDatabase().values());
        return (List<Pokemon>) allPokemon;
    }

    public Hashtable<ElementalType, Hashtable<ElementalType, Effectiveness>> getElements() {
        return elements;
    }

    public Hashtable<Integer, Pokemon> getDatabase() {
        return database;
    }

    public void printDatabase() {
        System.out.println(database.toString());
    }

    public void printElements() {
        //System.out.println(elements.toString());
        Enumeration<ElementalType> keys = elements.keys();
        Enumeration<Hashtable<ElementalType, Effectiveness>> values = elements.elements();
        while (keys.hasMoreElements() && values.hasMoreElements()) {
            System.out.print(keys.nextElement() + "= ");
            System.out.println(values.nextElement().toString());
        }
    }
//##########################Privates#############################

    public int weaknessCalculation(int value1, int value2) {
        int result;
        if (value1 == 0 || value2 == 0) {
            result = 0;
        } else if (value1 == 1 || value2 == 1) {
            result = value1 * value2;
        } else if (value1 == value2) {
            if (value1 == -1) {
                result = -2;
            } else {
                result = 4;
            }
        } else {
            result = 1;
        }
        return result;
    }

    private void prettyPrint(String[] anArray) {
        int i = 0;
        System.out.print("[");
        for (i = 0; i < anArray.length; ++i) {
            if (i != 0) {
                System.out.print(",");
            }
            System.out.print(anArray[i]);
        }
        System.out.println("]");
    }

    private List<Integer> parseStats(List<String> rawPokemonStats) {
        List<Integer> stats = new ArrayList<Integer>(rawPokemonStats.size());
        for (String anInt : rawPokemonStats) {
            stats.add(Integer.valueOf(anInt));
        }
        return stats;
    }

    private List<ElementalType> parseType(String type) {
        List<String> typeList = Arrays.asList(type.split("/"));
        List<ElementalType> eleTypeList = new LinkedList<ElementalType>();
        for (String typeString : typeList) {
            for (ElementalType currentType : ElementalType.values()) {
                if (currentType.toString().equals(typeString)) {
                    eleTypeList.add(currentType);
                }
            }

        }
        return eleTypeList;
    }
}
