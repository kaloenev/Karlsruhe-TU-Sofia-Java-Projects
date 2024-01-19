package game;

import cards.RunaCard;
import cards.RunaMagicCard;
import cards.RunaPhysicalCard1;
import cards.RunaPhysicalCard2;
import cards.RunaSpecialCard;
import characters.Boss;
import characters.Hero;
import characters.Monster;
import characters.Monsters;
import ui.Ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * This class represents the Game with implementations of the main game functions
 *
 * @author usoia
 * @version 1.0
 */
public class RunaStrive implements Game {
    private Queue<Monster> monsters;
    private List<RunaCard> cards;
    private List<Monster> currentMonsters;
    private List<Monster> previousMonsters;
    private final Hero hero;
    private int level;
    private int stage;
    private final int maxDice = 12;
    private boolean hasSwitched = false;
    private boolean stillAlive = true;

    /**
     * Initializes the game with its characters and cards
     *
     * @param characterClass Runa's character class
     */
    public RunaStrive(CharacterClass characterClass) {
        this.hero = new Hero(characterClass);
        this.cards = new ArrayList<>();
        currentMonsters = new LinkedList<>();
        previousMonsters = new LinkedList<>();
        this.level = 1;
        cards.addAll(Arrays.asList(RunaPhysicalCard1.values()));
        cards.addAll(Arrays.asList(RunaPhysicalCard2.values()));
        cards.addAll(Arrays.asList(RunaSpecialCard.values()));
        cards.addAll(Arrays.asList(RunaMagicCard.values()));
        this.stage = 1;
        cards.removeAll(characterClass.getSpecificCards());
    }


    /**
     * Checks if Runa is still alive (has not lost the game)
     *
     * @return true if she lost (is dead) false otherwise
     */
    public boolean runaHasLost() {
        return !stillAlive;
    }

    /**
     * Maximum value of a dice getter
     *
     * @return maximum value a dice can have
     */
    @Override
    public int getMaxDice() {
        return maxDice;
    }

    /**
     * Upgrades the Runa's current dice to the next one
     *
     * @return the value of the dice, so it can be printed
     */
    @Override
    public int upgradeDice() {
        previousMonsters.clear();
        return hero.upgradeDice();
    }

    /**
     * Checks the hp of Runa and the monsters and calls a print function if runa gained focus points
     */
    @Override
    public void beforeTurn() {
        if (checkHp()) {
            int focus = hero.beforeTurn();
            if (focus > 0) {
                Ui.printFocusGain(hero.getName(), focus);
            }
        }
    }

    /**
     * Represents the sequential taking of a turn (first Runa then the Monsters)
     *
     * @param index        index of Runa's card to be used
     * @param monsterIndex index of the target monster
     * @param diceRoll     value of the dice
     */
    @Override
    public void takeTurn(int index, int monsterIndex, int diceRoll) {
        if (checkHp()) {
            hero.takeTurn(index, currentMonsters.get(monsterIndex), diceRoll);
        }
        if (checkHp()) {
            monsterTurn();
        }
    }

    /**
     * Represents the sequential taking of turns of the monsters in the current battle
     */
    private void monsterTurn() {
        for (Monster monster : currentMonsters) {
            int focus = monster.beforeTurn();
            if (focus > 0) {
                Ui.printFocusGain(monster.getName(), focus);
            }
        }
        for (Monster monster : currentMonsters) {
            if (checkHp()) {
                monster.takeTurn(hero);
            } else {
                break;
            }
        }
        checkHp();
    }

    /**
     * Builds a String of the cards runa has to choose from as reward
     *
     * @return String with the possible cards Runa can get as reward
     */
    @Override
    public String getRewardCardOptions() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < previousMonsters.size() * 2 && i < cards.size(); i++) {
            builder.append(cards.get(i).toString()).append("(").append(level).append(")").append(",");
        }
        return builder.toString();
    }

    /**
     * Checks if the current Stage has ended (all monsters have been killed)
     *
     * @return true if the stage continues, false otherwise
     */
    @Override
    public boolean checkStageConditions() {
        if (currentMonsters.isEmpty()) {
            switchStage();
            return false;
        }
        return true;
    }

    /**
     * Checks if the level was switched
     *
     * @return true if the level continues, false otherwise
     */
    @Override
    public boolean checkLevelConditions() {
        return !hasSwitched;
    }

    /**
     * Getter
     *
     * @return hp of Runa
     */
    @Override
    public int getRunaHp() {
        return hero.getHp();
    }

    /**
     * Getter
     *
     * @return maximum hp Runa could have
     */
    @Override
    public int getRunaMaxHp() {
        return hero.getMaxHp();
    }

    /**
     * Getter
     *
     * @return the current focus points of Runa
     */
    @Override
    public int getRunaFocusPoints() {
        return hero.getFocusPoints();
    }

    /**
     * Getter
     *
     * @return the maximum focus points Runa could have
     */
    @Override
    public int getRunaMaxFocusPoints() {
        return hero.getMaxFocusPoints();
    }

    /**
     * Builds a String array of the cards runa has and returns it
     *
     * @return String array with the possible cards Runa could play with their ability level
     */
    @Override
    public String[] getCardOptions() {
        String[] options = new String[2];
        String option = hero.getCards().toString();
        option = option.replace("]", "");
        option = option.replace("[", "");
        options[0] = option;
        option = hero.getAbilityLevels();
        option = option.replace("]", "");
        option = option.replace("[", "");
        options[1] = option;
        return options;
    }

    /**
     * Represents the drawing of the cards form the card possibilities List into Runa's abilities List
     *
     * @param cardNumbers the indexes of the cards to be added to Runa's abilities
     */
    @Override
    public void drawCards(int[] cardNumbers) {
        Arrays.sort(cardNumbers);
        int counter = 0;
        int counter2 = 0;
        List<RunaCard> temp = new ArrayList<>();
        for (int i = 0; i < previousMonsters.size() * 2 && i < cards.size(); i++) {
            temp.add(cards.get(i));
        }

        for (RunaCard runaCard : temp) {
            cards.remove(runaCard);
            if (counter != cardNumbers.length && counter2 == cardNumbers[counter]) {
                hero.addCard(runaCard, level);
                counter++;
            }
            counter2++;
        }
        previousMonsters.clear();
    }

    /**
     * Summons the current level boss
     */
    private void summonBoss() {
        for (Boss boss : Boss.values()) {
            if (boss.getLevel() == level) {
                currentMonsters.add(boss);
                break;
            }
        }
    }

    /**
     * Switches the level to one higher (from 1 to 2 in current state)
     */
    private void switchLevel() {
        this.level++;
        monsters.clear();
        List<Monster> monstersList = new LinkedList<>();
        for (Monsters monster : Monsters.values()) {
            if (monster.getLevel() == level) {
                monstersList.add(monster);
            }
        }
        monsters.addAll(monstersList);
        cards.clear();
        cards.addAll(Arrays.asList(RunaPhysicalCard1.values()));
        cards.addAll(Arrays.asList(RunaPhysicalCard2.values()));
        cards.addAll(Arrays.asList(RunaSpecialCard.values()));
        cards.addAll(Arrays.asList(RunaMagicCard.values()));
        for (RunaCard card : hero.getCharacterClass().getSpecificCards()) {
            hero.addCard(card, level);
            cards.remove(card);
        }
        hasSwitched = true;
        stage = 1;
    }

    /**
     * Switches between the stages of the levels
     */
    private void switchStage() {
        this.stage++;
        if (stage != 5) {
            if (stage == 4) {
                summonBoss();
                return;
            }
            if (stage != 1) {
                currentMonsters.add(monsters.remove());
            }
            currentMonsters.add(monsters.remove());
        } else {
            switchLevel();
        }
    }

    /**
     * Getter
     *
     * @return the current level
     */
    @Override
    public int getLevel() {
        return level;
    }

    /**
     * Getter
     *
     * @return the current stage
     */
    @Override
    public int getStage() {
        return stage;
    }

    /**
     * Builds a String that brings information about the current monsters
     *
     * @return String with the current monster attributes, separated with commas
     */
    @Override
    public String getCurrentMonstersStats() {
        StringBuilder builder = new StringBuilder();
        for (Monster monster : currentMonsters) {
            builder.append(monster.getName()).append(",").append(monster.getHp()).append(",")
                    .append(monster.getFocusPoints()).append(",").append(monster.getNextCard()).append(",")
                    .append(monster.getAbilityLevel()).append("/");
        }
        return builder.toString();
    }

    /**
     * Checks the hp of the current monsters and removes them if dead
     * Also checks if runa is alive
     *
     * @return true if there are still monsters alive and Runa is alive
     */
    private boolean checkHp() {
        if (hero.getHp() <= 0) {
            stillAlive = false;
        }
        for (Iterator<Monster> iterator = currentMonsters.listIterator(); iterator.hasNext(); ) {
            Monster monster = iterator.next();
            if (monster.getHp() <= 0) {
                previousMonsters.add(monster);
                iterator.remove();
            }
        }
        return !currentMonsters.isEmpty() && stillAlive;
    }

    /**
     * Checks if Runa must roll the dice
     *
     * @param index index of the card to be played
     * @return true if she must roll
     */
    public boolean mustRollDice(int index) {
        return hero.mustRollDice(index);
    }

    /**
     * Checks the max value of Runa's dice
     *
     * @return the highest number Runa could roll with the current dice
     */
    public int heroMaxDice() {
        return hero.getDice().getMaxFocusPoints();
    }

    /**
     * Discards the cards with the given indexes from Runa's abilities and heals her for max 10 hp per discard
     *
     * @param index given card indexes
     */
    public void discard(int[] index) {
        hero.discard(index);
        hero.heal(index.length * 10);
    }

    /**
     * Checks if the card with the given index needs a target or is a defensive one
     *
     * @param index given card index
     * @return true if it needs a target
     */
    @Override
    public boolean needsTarget(int index) {
        return hero.needsTarget(index);
    }

    /**
     * Shuffles the cards and monsters lists with the given cardSeed and monsterSeed
     *
     * @param cardSeed    given card seed
     * @param monsterSeed given monster seed
     */
    public void shuffle(int cardSeed, int monsterSeed) {
        List<Monster> monstersList = new LinkedList<>();
        for (Monsters monster : Monsters.values()) {
            if (monster.getLevel() == level) {
                monstersList.add(monster);
            }
        }
        Collections.shuffle(cards, new Random(cardSeed));
        Collections.shuffle(monstersList, new Random(monsterSeed));

        this.monsters = new LinkedList<>(monstersList);
        currentMonsters.add(monsters.remove());
        hasSwitched = false;
    }
}
