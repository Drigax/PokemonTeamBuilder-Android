package com.drigax.teambuilder.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Scanner;

public class TeamBuilder {

    private static Scanner userInput = new Scanner(System.in);
    private static Team Instance;
    private Team currentTeam;
    private Pokedex currentDex;
    private ArrayList<String> statStrings;

    public TeamBuilder() {
        statStrings = new ArrayList<String>(Arrays.asList("HP", "Attack", "Defense", "SpAttack", "SpDefense", "Speed"));
    }

    public static Team getInstance(Pokedex pokedexInstance) {
        if (Instance == null) {
            Instance = new Team(pokedexInstance); //creates a new TeamBuilder instance with CLI disabled
        }
        return Instance;
    }

    public static void main(String[] args) {
        if (args.length > 1) {
            if (args[1].equals("-g")) {
                new TeamBuilder();
            } else {
                errorPrompt();
            }
        } else {
            new TeamBuilder();
        }
    }

    private static void errorPrompt() {
        System.err.println("proper usage: TeamBuilder (-g for GUI mode)");
    }

    public void addPokemonToTeam(Pokemon currentPoke) {
        currentTeam.addPokemon(currentPoke);
    }

    public void addPokemonToTeam(Pokemon currentPoke, int index) {
        currentTeam.addPokemonAt(currentPoke, index);
    }

    public void removePokemonFromTeam(int index) {
        currentTeam.removePokemonAt(index);
    }

    //###################### Privates #######################

    public Pokemon getPokemonAt(int index) {
        return currentTeam.getPokemon(index);
    }


    private void launchCLI() {
        int input = 0;
        boolean complete = false;
        while (!complete) {
            printMainMenu();
            input = getNumericalUserInput();
            switch (input) {
                case (1):
                    promptPokemonAdd();
                    break;
                case (2):
                    promptPokemonRemoval();
                    break;
                case (3):
                    displayStats();
                    break;
                case (4):
                    promptIndividualPokemonStats();
                    break;
                case (5):

                case (-1):
                    complete = true;
                    break;
                default:
                    System.out.println("Invalid selection");
                    break;
            }
        }
    }

    private void launchGUI() {
        System.out.println("Well, this is quite awkward... \n" +
                "Because the current state of this application does not " +
                "support GUI functionality, it will be forced to close. \n" +
                "Try launching in command line mode!");
    }

    private void printMainMenu() {
        System.out.println("What would you like to do? \n" +
                "1) Add a Pokemon \n" +
                "2) Remove a Pokemon \n" +
                "3) View Team Stats \n" +
                "4) View Individual Pokemon Stats \n" +
                "5) Exit");
    }

    private int selectPokemon() {
        Hashtable<Integer, Pokemon> database = currentDex.getDatabase();
        Enumeration<Integer> id = database.keys();
        LinkedList<Integer> idStack = new LinkedList<Integer>();
        while (id.hasMoreElements()) {
            idStack.push(id.nextElement());
        }
        int currentId = -2;
        while (idStack.peek() != null) {
            currentId = idStack.pop();
            System.out.println(Integer.toString(currentId) + ") " + database.get(currentId).getName());
        }
        System.out.println("Select a pokemon (c to exit)");
        return getNumericalUserInput(1, currentId);
    }

    private void promptPokemonAdd() {
        int index = selectPokemon();
        if (index != -1) {
            currentTeam.addPokemon(currentDex.getPokemon(index));
        }
    }

    private void promptPokemonRemoval() {
        int i;
        for (i = 0; i < currentTeam.size; ++i) {
            System.out.println(Integer.toString(i + 1) + ") " + "ID: " + currentTeam.getPokemon(i).getId() + " " + currentTeam.getPokemon(i).getName());
        }
        int index = getNumericalUserInput(1, currentTeam.size);
        if (index != -1) {
            currentTeam.removePokemonAt(index - 1);
        }
    }

    private void displayStats() {
        int i;
        System.out.println("Displaying current team information");
        for (i = 0; i < currentTeam.size; ++i) {
            System.out.println(Integer.toString(i + 1) + ") " + currentTeam.getPokemon(i));
        }
        System.out.println("\nTeam Average Stats: " + teamStats());
        System.out.println("\nTeam Weaknesses: \ntype: damage multiplier: number of pokemon");
        System.out.println(teamWeakness());
        System.out.println("\nEnter \"c\" to exit");
        userInput.next();
    }

    private String teamStats() {
        int i;
        String str = "";
        for (i = 0; i < statStrings.size(); ++i) {
            str += statStrings.get(i) + ": " + currentTeam.teamStats.get(i) + " ";
        }
        return str;
    }

    private String teamWeakness() {
        String str = "";
        ElementalType currentElement;
        //grabs all elements in the weakness table for the team
        Enumeration<ElementalType> elements = currentTeam.getTeamWeakness().weaknessTable.keys();
        //Hashtable that represents the team multiplier damages for an element
        Hashtable<Effectiveness, Integer> elementDamageTable;
        //grabs all multiplier values for a given attack element
        Enumeration<Effectiveness> multipliers;
        //string that stores the current multiplier type
        Effectiveness multiplierType;

        while (elements.hasMoreElements()) {
            currentElement = elements.nextElement();
            elementDamageTable = currentTeam.getTeamWeakness().weaknessTable.get(currentElement);
            multipliers = elementDamageTable.keys();
            str += String.format("%s: ", currentElement);
            while (multipliers.hasMoreElements()) {
                multiplierType = multipliers.nextElement();
                str += String.format("%s: %d ", multiplierType, elementDamageTable.get(multiplierType));
            }
            str += "\n";
        }


        return str;
    }

    private void promptIndividualPokemonStats() {
        int index = selectPokemon();
        if (index != -1) {
            System.out.println(currentDex.getPokemon(index).toString() + "\n");
        }
        //System.out.println();
        System.out.println("Enter \"c\" to exit");
        userInput.next();
    }
    //###################### Main ###########################

    /**
     * Converts user input to useable state.
     *
     * @return int
     */
    int getNumericalUserInput() {
        String input = userInput.next();
        int i = -1; //holds converted input
        boolean success = false;

        while (!success) {
            if (input.toLowerCase().equals("c")) {
                i = -1;
                success = true;
            } else {
                try {
                    i = Integer.parseInt(input);
                    success = true;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid entry.");
                    break;
                }
            }
        }
        return i;
    }

    int getNumericalUserInput(int int1, int int2) {
        boolean complete = false;
        int input = -2;
        while (!complete) {
            input = getNumericalUserInput();
            if (input >= int1 && input <= int2) {
                complete = true;
            } else if (input == -1) {
                complete = true;
            } else {
                System.out.println("Invalid selection");
            }
        }
        return input;
    }
}
