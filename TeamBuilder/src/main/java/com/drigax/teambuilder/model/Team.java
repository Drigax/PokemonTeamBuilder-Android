package com.drigax.teambuilder.model;

import com.drigax.teambuilder.utils.SimpleObservable;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class Team extends SimpleObservable {

    public static Team Instance;
    public ArrayList<Integer> teamStats;
    public Pokedex currentDex;
    public Integer size;
    public int MAX_LENGTH = 6;
    private ArrayList<Pokemon> slots;
    private TeamWeaknessTable teamWeakness;
    private Hashtable<ElementalType, Effectiveness> teamResistance;
    private Hashtable<ElementalType, Effectiveness> teamStrengths;
    private Hashtable<ElementalType, Effectiveness> teamIneffective;
    private int numStats = 6;

    public Team(Pokedex aPokedex) {
        slots = new ArrayList<Pokemon>();
        currentDex = aPokedex;
        teamWeakness = new TeamWeaknessTable();
        teamStrengths = new Hashtable<ElementalType, Effectiveness>();
        teamResistance = new Hashtable<ElementalType, Effectiveness>();
        teamIneffective = new Hashtable<ElementalType, Effectiveness>();
        teamStats = new ArrayList<Integer>();
        size = 0;
        initialize();
    }

    public static Team getInstance(Pokedex aPokedex) {
        if (Instance == null) {
            Instance = new Team(aPokedex);
        }

        return Instance;
    }

    //####################### Main ##################################
    public static void main(String[] args) {
    }

    public Pokemon getPokemon(int index) {
        return slots.get(index);
    }

    public List<Pokemon> getPokemon() {
        return slots;
    }

    public TeamWeaknessTable getTeamWeakness() {
        return teamWeakness;
    }

    /**
     * checks the underlying list for the first empty index, and adds the given pokemon there
     *
     * @param newPoke
     */
    public void addPokemon(Pokemon newPoke) {
        int selectedIndex = -1;
        int i = 0;
        while (i < MAX_LENGTH && selectedIndex == -1) {
            if (slots.get(i) == null) {
                selectedIndex = i;
            }
            ++i;
        }
        if (selectedIndex > -1) {
            addPokemonAt(newPoke, selectedIndex);
        }
    }

    public void addPokemonAt(Pokemon newPoke, int index) {
        slots.remove(index);
        slots.add(index, newPoke);
        ++size;
        recalculateTeam();
    }

    public void removePokemonAt(int index) {
        slots.remove(index);
        --size;
        recalculateTeam();
    }

    public void recalculateTeam() {
        calculateTeamStats();
        notifyObservers(this);
    }

    /**
     * recalculates teamStats to be the average of all pokemon stats currently
     * on the team.
     */
    public void calculateTeamStats() {
        int currentTotal;
        int currentStat;
        for (currentStat = 0; currentStat < teamStats.size(); ++currentStat) {
            //System.out.format("currentStat: %d teamStats.size: %d", currentStat, teamStats.size());
            currentTotal = 0;
            if (this.size > 0) {
                for (Pokemon currentPoke : slots) {
                    if (currentPoke != null)
                        currentTotal += currentPoke.getStats().get(currentStat);
                }
                currentTotal /= this.size;
            }
            teamStats.set(currentStat, currentTotal);
            //System.out.format("Adding %d to slot %d\n", currentStat, currentTotal);
        }
    }

    public void printTeam() {
        for (Pokemon currentPoke : slots) {
            System.out.println(currentPoke.toString());
        }
    }

    public String getAverageStats() {
        return teamStats.toString();
    }

    //###########################Privates##########################

    public void printStats() {
        System.out.println("Average Stats: " + teamStats.toString());
        System.out.println("Weaknesses: " + teamWeakness.toString());
        System.out.println("Strengths: " + teamStrengths.toString());
        System.out.println("Resistances: " + teamResistance.toString());
        System.out.println("Ineffectives: " + teamIneffective.toString());
    }

    /**
     * initializes the team tables with zeroes for all possible elements.
     */
    private void initialize() {
        Enumeration<ElementalType> allTypes = currentDex.getElements().keys();
        ElementalType currentType;
        while (allTypes.hasMoreElements()) {
            currentType = allTypes.nextElement();
            teamStrengths.put(currentType, Effectiveness.NORMAL_DAMAGE);
            teamResistance.put(currentType, Effectiveness.NORMAL_DAMAGE);
            teamIneffective.put(currentType, Effectiveness.NORMAL_DAMAGE);
        }
        int count;
        for (count = 0; count < numStats; ++count) {
            teamStats.add(0);
        }
        for (int i = 0; i < MAX_LENGTH; ++i) {
            slots.add(null);
        }
    }

    //################### Internal Classes ##########################
    public class TeamWeaknessTable {

        // weaknessTable - a Hashtable that maps a type of damage to
        //				   another hashtable that contains all possible damage multipliers,
        //				   and the number of pokemon who receive damage of that multiplier.
        public Hashtable<ElementalType, Hashtable<Effectiveness, Integer>> weaknessTable;

        public TeamWeaknessTable() {
            weaknessTable = new Hashtable<ElementalType, Hashtable<Effectiveness, Integer>>();

            initialize();
        }

        //initializes the hashtable, writing all values of each possible damage/multiplier combination
        // to zero.
        public void initialize() {
            Enumeration<ElementalType> allTypes = currentDex.getElements().keys();
            ArrayList<String> damageTypes = new ArrayList<String>();
            ElementalType currentElement;
            while (allTypes.hasMoreElements()) {
                currentElement = allTypes.nextElement();
                weaknessTable.put(currentElement, new Hashtable<Effectiveness, Integer>());
                for (Effectiveness currentEffectiveness : Effectiveness.values()) {
                    weaknessTable.get(currentElement).put(currentEffectiveness, 0);
                }
            }
        }

        public String toString() {
            String str = "";
            str += weaknessTable.toString();
            return str;
        }


    }


}
