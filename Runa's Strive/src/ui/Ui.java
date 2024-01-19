package ui;

import game.CharacterClass;
import game.Game;
import game.RunaStrive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The main class of the program. Takes input and manages how the game will be played based on it
 * Also provides the output
 *
 * @author usoia
 * @version 1.0
 */
public final class Ui {
    /**
     * Private constructor
     */
    private Ui() {

    }

    private static final int MINUS_AMOUNT = 40;
    private static final int HERO_MAX_HP = 50;
    private static final int MAX_INT_LENGTH = 10;

    /**
     * Main method, manages the input and sequencing of the game
     *
     * @param args command line arguments, should be empty
     * @throws Exception custom exception in case args is not empty
     */
    public static void main(String[] args) throws Exception {
        if (args.length > 0) {
            throw new Exception("Error, no command line arguments should be given");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome to Runa's Strive");
        System.out.println("Select Runa's character class");
        System.out.println("1) Warrior");
        System.out.println("2) Mage");
        System.out.println("3) Paladin");
        String input;
        CharacterClass characterClass = null;
        boolean isCorrectCommand = true;
        do {
            System.out.println("Enter number [1--3]:");
            input = reader.readLine();
            if (input.equals("quit")) {
                return;
            }
            switch (input) {
                case "1":
                    characterClass = CharacterClass.WARRIOR;
                    break;
                case "2":
                    characterClass = CharacterClass.MAGE;
                    break;
                case "3":
                    characterClass = CharacterClass.PALADIN;
                    break;
                default:
                    continue;
            }
            isCorrectCommand = false;
        } while (isCorrectCommand);
        Game game = new RunaStrive(characterClass);
        while (game.getLevel() != 3) {
            List<Integer> seeds = new LinkedList<>();
            if (getSeeds(reader, seeds)) {
                return;
            }
            game.shuffle(seeds.get(0), seeds.get(1));
            while (game.checkLevelConditions()) {
                System.out.println("Runa enters Stage " + game.getStage() + " of Level " + game.getLevel());
                if (runStage(reader, game)) {
                    return;
                }
                if (game.getStage() != 5 && game.getStage() != 1) {
                    if (getRewards(reader, game)) {
                        return;
                    }
                }
                if (game.getLevel() != 2 || game.getStage() != 5) {
                    if (heal(reader, game)) {
                        return;
                    }
                }
            }
        }
        reader.close();
        System.out.println("Runa won!");
    }

    /**
     * This method takes input calls the game methods that do the healing and discarding when available and delivers output
     *
     * @param reader BufferedReader that takes input
     * @param game   current game
     * @return true if the quit function was called, otherwise only calls methods and prints
     * @throws IOException BufferedReader exception
     */
    private static boolean heal(BufferedReader reader, Game game) throws IOException {
        Pattern pattern1;
        String input;
        String[] cards = game.getCardOptions();
        String[] options = cards[0].split(", ");
        String[] abilityLevels = cards[1].split(", ");
        int[] runaCardIndex;
        boolean correctInput = true;
        if (options.length > 1 && game.getRunaHp() < HERO_MAX_HP) {
            System.out.println("Runa (" + game.getRunaHp() + "/" + game.getRunaMaxHp()
                    + " HP) can discard ability cards for healing (or none)");
            for (int i = 0; i < options.length; i++) {
                System.out.println(i + 1 + ") " + options[i] + "(" + abilityLevels[i] + ")");
            }
            boolean skipped = false;
            if (options.length == 2) {
                do {
                    pattern1 = Pattern.compile("[1-" + options.length + "]");
                    System.out.println("Enter number [1--" + options.length + "]");
                    input = reader.readLine();
                    if (input.equals("quit")) {
                        return true;
                    }
                    if (input.equals("")) {
                        skipped = true;
                        break;
                    }
                    Matcher matcher = pattern1.matcher(input);
                    if (!matcher.matches()) {
                        continue;
                    }
                    correctInput = false;
                } while (correctInput);
            } else {
                do {
                    int possibleHeal = (game.getRunaMaxHp() - game.getRunaHp()) / 10 + 1;
                    if (possibleHeal < options.length - 1) {
                        pattern1 = Pattern.compile("([1-" + options.length + "][,])" + "{1," + possibleHeal + "}");
                    } else {
                        pattern1 = Pattern.compile("([1-" + options.length + "][,])" + "{1," + (options.length - 1) + "}");
                    }
                    System.out.println("Enter numbers [1--" + options.length + "] separated by comma:");
                    input = reader.readLine();
                    if (input.equals("quit")) {
                        return true;
                    }
                    if (input.equals("")) {
                        skipped = true;
                        break;
                    }
                    input = input + ",";
                    Matcher matcher = pattern1.matcher(input);
                    if (!matcher.matches()) {
                        continue;
                    }
                    correctInput = false;
                } while (correctInput);
            }
            if (!skipped) {
                int currentHp = game.getRunaHp();
                String[] indexes = input.split(",");
                runaCardIndex = new int[indexes.length];
                for (int i = 0; i < indexes.length; i++) {
                    runaCardIndex[i] = Integer.parseInt(indexes[i]) - 1;
                }
                game.discard(runaCardIndex);
                System.out.println("Runa gains " + (game.getRunaHp() - currentHp) + " health");
            }
        }
        return false;
    }

    /**
     * This method takes input calls the game methods that do the rewarding after battle and delivers output
     *
     * @param reader BufferedReader that takes input
     * @param game   current game
     * @return true if the quit function was called, otherwise only calls methods and prints
     * @throws IOException BufferedReader exception
     */
    private static boolean getRewards(BufferedReader reader, Game game) throws IOException {
        Pattern pattern1;
        String input;
        boolean choseDice = false;
        if (game.getMaxDice() != game.heroMaxDice()) {
            System.out.println("Choose Runa's reward");
            System.out.println("1) new ability cards");
            System.out.println("2) next player dice");
            boolean continueLoop = true;
            do {
                System.out.println("Enter number [1--2]:");
                String read = reader.readLine();
                if (read.equals("quit")) {
                    return true;
                }
                if (read.equals("1")) {
                    continueLoop = false;
                } else if (read.equals("2")) {
                    int newDice = game.upgradeDice();
                    System.out.println("Runa upgrades her die to a d" + newDice);
                    continueLoop = false;
                    choseDice = true;
                }
            } while (continueLoop);
        }
        if (!choseDice) {
            String[] rewardOptions = game.getRewardCardOptions().split(",");
            System.out.println("Pick " + (rewardOptions.length / 2 + rewardOptions.length % 2)
                    + " card(s) as loot");
            for (int i = 0; i < rewardOptions.length; i++) {
                System.out.println(i + 1 + ") " + rewardOptions[i]);
            }
            int[] cardIndex = null;
            boolean isCorrect = true;
            String message;
            int rewardChoiceLength = rewardOptions.length;
            if (rewardOptions.length > 2) {
                pattern1 = Pattern.compile("[1-" + rewardChoiceLength + "][,][1-" + rewardChoiceLength + "]");
                message = "Enter numbers [1--" + rewardOptions.length + "] separated by comma:";
            } else {
                pattern1 = Pattern.compile("[1-" + rewardChoiceLength + "]");
                message = "Enter number [1--" + rewardOptions.length + "]:";
            }
            do {
                System.out.println(message);
                input = reader.readLine();
                if (input.equals("quit")) {
                    return true;
                }
                Matcher matcher = pattern1.matcher(input);
                if (!matcher.matches()) {
                    continue;
                }
                String[] rewards = input.split(",");
                if (rewardChoiceLength == 2) {
                    cardIndex = new int[1];
                } else {
                    cardIndex = new int[2];
                    cardIndex[1] = Integer.parseInt(rewards[1]) - 1;
                }
                cardIndex[0] = Integer.parseInt(rewards[0]) - 1;
                isCorrect = false;
            } while (isCorrect);
            game.drawCards(cardIndex);
        }
        return false;
    }

    /**
     * This method takes input, calls the game methods that run the battle, also delivers output based on the events
     *
     * @param reader BufferedReader that takes input
     * @param game   current game
     * @return true if the quit function was called, otherwise only calls methods and prints
     * @throws IOException bufferedReader exception
     */
    private static boolean runStage(BufferedReader reader, Game game) throws IOException {
        Pattern pattern1;
        String input;
        do {
            List<String> monsterNames = new ArrayList<>();
            game.beforeTurn();
            printMinusLine();
            System.out.println("Runa (" + game.getRunaHp() + "/" + game.getRunaMaxHp() + " HP, "
                    + game.getRunaFocusPoints() + "/" + game.getRunaMaxFocusPoints() + " FP)");
            System.out.println("vs.");
            String monstersData = game.getCurrentMonstersStats();
            String[] monsters = monstersData.split("/");
            for (String monster : monsters) {
                String[] currentMonster = monster.split(",");
                System.out.println(currentMonster[0] + " (" + currentMonster[1] + " HP, " + currentMonster[2]
                        + " FP): attempts " + currentMonster[3] + "(" + currentMonster[4] + ") " + "next");
                monsterNames.add(currentMonster[0]);
            }
            printMinusLine();
            String[] cards = game.getCardOptions();
            String[] options = cards[0].split(", ");
            String[] abilityLevels = cards[1].split(", ");
            int runaCardIndex = -1;
            int monsterIndex = 0;
            boolean correctInput = true;
            pattern1 = Pattern.compile("[1-" + options.length + "]");
            System.out.println("Select card to play");
            for (int i = 0; i < options.length; i++) {
                System.out.println(i + 1 + ") " + options[i] + "(" + abilityLevels[i] + ")");
            }
            do {
                System.out.println("Enter number [1--" + options.length + "]:");
                input = reader.readLine();
                if (input.equals("quit")) {
                    return true;
                }
                Matcher matcher = pattern1.matcher(input);
                if (!matcher.matches()) {
                    continue;
                }
                correctInput = false;
                runaCardIndex = Integer.parseInt(input) - 1;
            } while (correctInput);
            if (monsters.length > 1 && game.needsTarget(runaCardIndex)) {
                System.out.println("Select Runa's target.");
                for (int i = 0; i < monsterNames.size(); i++) {
                    System.out.println(i + 1 + ") " + monsterNames.get(i));
                }
                pattern1 = Pattern.compile("[1-" + monsters.length + "]");
                correctInput = true;
                do {
                    System.out.println("Enter number [1--" + monsters.length + "]:");
                    input = reader.readLine();
                    if (input.equals("quit")) {
                        return true;
                    }
                    Matcher matcher = pattern1.matcher(input);
                    if (!matcher.matches()) {
                        continue;
                    }
                    correctInput = false;
                    monsterIndex = Integer.parseInt(input) - 1;
                } while (correctInput);
            }
            System.out.println("Runa uses " + options[runaCardIndex] + "(" + abilityLevels[runaCardIndex] + ")");
            List<Integer> dice = new LinkedList<>();
            if (game.mustRollDice(runaCardIndex)) {
                if (rollDice(reader, game, dice)) return true;
            }
            dice.add(0);
            game.takeTurn(runaCardIndex, monsterIndex, dice.get(0));
            if (game.runaHasLost()) {
                return true;
            }
        } while (game.checkStageConditions());
        return false;
    }

    /**
     * This method the runStage method by rolling the dice required for the game to continue
     *
     * @param reader BufferedReader that takes input
     * @param game   current game
     * @return true if the quit function was called, otherwise only calls methods and prints
     * @throws IOException bufferedReader exception
     */
    private static boolean rollDice(BufferedReader reader, Game game, List<Integer> dice) throws IOException {
        Pattern pattern1;
        String input;
        boolean correctInput;
        pattern1 = Pattern.compile("[1-" + game.heroMaxDice() + "]");
        correctInput = true;
        do {
            System.out.println("Enter dice roll [1--" + game.heroMaxDice() + "]:");
            input = reader.readLine();
            if (input.equals("quit")) {
                return true;
            }
            Matcher matcher = pattern1.matcher(input);
            if (!matcher.matches()) {
                continue;
            }
            correctInput = false;
        } while (correctInput);
        dice.add(Integer.parseInt(input));
        return false;
    }

    /**
     * This method takes input, formats it, so it can be interpreted as a seed and delivers output
     *
     * @param reader BufferedReader that takes input
     * @param seed   a list where the seeds of the game are to be saved and returned to the main method
     * @return true if the quit function was called, otherwise only calls methods and prints
     * @throws IOException bufferedReader exception
     */
    private static boolean getSeeds(BufferedReader reader, List<Integer> seed) throws IOException {
        boolean isCorrectCommand;
        String input;
        Pattern pattern1 = Pattern.compile("[1-9]\\d*[,][1-9]\\d*");
        isCorrectCommand = true;
        do {
            System.out.println("To shuffle ability cards and monsters, enter two seeds");
            System.out.println("Enter seeds [1--2147483647] separated by comma:");
            input = reader.readLine();
            if (input.equals("quit")) {
                return true;
            }
            Matcher matcher = pattern1.matcher(input);
            if (!matcher.matches()) {
                continue;
            }
            String[] seeds = input.split(",");
            if (seeds[0].length() > MAX_INT_LENGTH || seeds[1].length() > MAX_INT_LENGTH) {
                continue;
            }
            long test = Long.parseLong(seeds[0]);
            long test2 = Long.parseLong(seeds[1]);
            if (test > Integer.MAX_VALUE || test2 > Integer.MAX_VALUE) {
                continue;
            }
            seed.add(Integer.parseInt(seeds[0]));
            seed.add(Integer.parseInt(seeds[1]));
            isCorrectCommand = false;
        } while (isCorrectCommand);
        return false;
    }

    /**
     * Prints 40 minuses
     */
    private static void printMinusLine() {
        for (int i = 0; i < 40; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    /**
     * Prints the damage dealt to a character
     *
     * @param name       name of the character
     * @param damage     damage taken by the character
     * @param damageType magic or physical
     */
    public static void printDamage(String name, int damage, String damageType) {
        System.out.println(name + " takes " + damage + " " + damageType);
    }

    /**
     * Prints the used card by a game character
     *
     * @param name     name of the character
     * @param cardName name of the card used
     * @param level    level of the card used
     */
    public static void printUsedCard(String name, String cardName, int level) {
        System.out.println(name + " uses " + cardName + "(" + level + ")");
    }

    /**
     * Prints the name of a character who just died
     *
     * @param name name of the character
     */
    public static void characterDied(String name) {
        System.out.println(name + " dies");
    }

    /**
     * Prints the focus points gained by a character at the end of a turn
     *
     * @param name  name of the character
     * @param focus focus points amount
     */
    public static void printFocusGain(String name, int focus) {
        System.out.println(name + " gains " + focus + " focus");
    }

    /**
     * Prints the gained/drawn card by a character (mostly Runa)
     *
     * @param name  name of the drawn card
     * @param level level of the drawn card
     */
    public static void printGainedCard(String name, int level) {
        System.out.println("Runa gets " + name + "(" + level + ")");
    }
}
