package characters;

import cards.Card;
import game.CharacterClass;
import game.Dice;
import cards.RunaCard;
import ui.Ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Class representing the hero with their attributes
 *
 * @author usoia
 * @version 1.0
 */
public class Hero implements Character {
    private final String name = "Runa";
    private final CharacterClass characterClass;
    private List<RunaCard> cards;
    private int hp;
    private final int maxHp = 50;
    private int magicDamageReduction = 0;
    private int physicalDamageReduction = 0;
    private final List<Integer> abilityLevel;
    private Dice dice;
    private int focusPoints;
    private int maxFocusPoints;
    private int focusPointsPromotion;

    /**
     * Initializes the hero with a die d4, class specific cards and their ability levels
     *
     * @param characterClass class of the character
     */
    public Hero(CharacterClass characterClass) {
        this.characterClass = characterClass;
        this.cards = new LinkedList<>();
        cards.addAll(characterClass.getSpecificCards());
        abilityLevel = new ArrayList<>();
        abilityLevel.add(1);
        abilityLevel.add(1);

        this.hp = maxHp;
        this.dice = Dice.D4;
        this.maxFocusPoints = dice.getMaxFocusPoints();
        this.focusPoints = 1;
    }

    /**
     * Getter for the class of the character
     *
     * @return class
     */
    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    /**
     * Getter for the character's name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the cards of the character
     *
     * @return a copy of the cards list
     */
    public List<Card> getCards() {
        return List.copyOf(cards);
    }

    /**
     * Gets a string representation of the ability levels array
     *
     * @return string with ability levels
     */
    public String getAbilityLevels() {
        return abilityLevel.toString();
    }

    /**
     * Getter for hero's current dice
     *
     * @return current dice
     */
    public Dice getDice() {
        return dice;
    }

    /**
     * Upgrades hero's dice
     *
     * @return new dice max value
     */
    public int upgradeDice() {
        int counter = 0;
        Dice[] values = Dice.values();
        for (Dice dice : values) {
            if (this.dice.equals(dice)) {
                this.dice = values[counter + 1];
                this.maxFocusPoints = this.dice.getMaxFocusPoints();
                return this.dice.getMaxFocusPoints();
            }
            counter++;
        }
        return 0;
    }

    /**
     * Getter for hp
     *
     * @return current hp
     */
    @Override
    public int getHp() {
        return hp;
    }

    /**
     * Getter for character max hp
     *
     * @return max hp of a character
     */
    @Override
    public int getMaxHp() {
        return maxHp;
    }

    /**
     * Heals the character by the amount without exceeding the max hp
     *
     * @param hp amount to be healed
     */
    public void heal(int hp) {
        this.hp += hp;
        if (this.hp > maxHp) {
            this.hp = maxHp;
        }
    }

    /**
     * Setter for the magic defense of a character
     *
     * @param magicDamageReduction damage by which next magic damage will be reduced
     */
    @Override
    public void setMagicDamageReduction(int magicDamageReduction) {
        this.magicDamageReduction = magicDamageReduction;
    }

    /**
     * Setter for the physical defense of a character
     *
     * @param physicalDamageReduction damage by which next physical damage will be reduced
     */
    @Override
    public void setPhysicalDamageReduction(int physicalDamageReduction) {
        this.physicalDamageReduction = physicalDamageReduction;
    }

    /**
     * Getter for focus points
     *
     * @return current focus points
     */
    @Override
    public int getFocusPoints() {
        return focusPoints;
    }

    /**
     * Setter for when the focus card is played
     *
     * @param focusPoints amount by which focus points will be promoted
     */
    @Override
    public void setFocusPointsPromotion(int focusPoints) {
        this.focusPointsPromotion = focusPoints;
    }

    /**
     * toString method of the character
     *
     * @return string representation of Runa's cards
     */
    @Override
    public String toString() {
        return cards.toString();
    }

    /**
     * Character takes physical damage
     *
     * @param damage damage taken
     */
    @Override
    public void takePhysicalDamage(int damage) {
        int finalDamage = damage - physicalDamageReduction;
        if (finalDamage > 0) {
            hp -= finalDamage;
            Ui.printDamage(name, finalDamage, "phy. damage");
        }

        if (hp <= 0) {
            Ui.characterDied(name);
        }
    }

    /**
     * Character takes magic damage
     *
     * @param damage damage taken
     * @return reflected damage by character
     */
    @Override
    public int takeMagicDamage(int damage) {
        int finalDamage = damage - magicDamageReduction;
        if (finalDamage > 0) {
            hp -= finalDamage;
            Ui.printDamage(name, finalDamage, "mag. damage");
        }

        int reflectedDamage = magicDamageReduction;
        magicDamageReduction = 0;

        if (hp <= 0) {
            Ui.characterDied(name);
        }

        return reflectedDamage;
    }

    /**
     * Represents the resets of magic and physical damage reduction as well as focus points promotion
     * at the beginning of a new turn
     *
     * @return focusPoints gained from previous turn played focus
     */
    @Override
    public int beforeTurn() {
        magicDamageReduction = 0;
        physicalDamageReduction = 0;
        int currentFocusPoints = focusPoints;
        increaseFocusPoints();
        focusPointsPromotion = 0;
        return focusPoints - currentFocusPoints;
    }

    /**
     * The actual turn of the hero, causes the effect of their card and sets focusPoints if needed
     *
     * @param index    index of the card to be played
     * @param monster  the to be damaged the character
     * @param diceRoll in case the card is physical
     */
    public void takeTurn(int index, Monster monster, int diceRoll) {
        RunaCard card = cards.get(index);
        card.setDice(diceRoll);
        if (card.isMagic()) {
            card.setDice(focusPoints);
            reduceFocusPoints(card.getFocusPointCost(abilityLevel.get(index)));
        }
        if (card.isSpecial()) {
            card.causeEffect(this, abilityLevel.get(index));
        } else {
            card.causeEffect(monster, abilityLevel.get(index));
        }
    }

    /**
     * Reduces the focus points by the cost of a magical card
     *
     * @param focusPoints amount to be reduced with
     */
    @Override
    public void reduceFocusPoints(int focusPoints) {
        if (this.focusPoints != 1) {
            this.focusPoints -= focusPoints;
        }
    }

    /**
     * Getter for the max focus points
     *
     * @return maximum focus points Runa can have
     */
    public int getMaxFocusPoints() {
        return maxFocusPoints;
    }

    /**
     * Increases the focus points of the character (called before the actual turn)
     */
    public void increaseFocusPoints() {
        focusPoints += focusPointsPromotion;
        if (focusPoints > maxFocusPoints) {
            focusPoints = maxFocusPoints;
        }
    }

    /**
     * Adds a new card to the hero's abilities
     *
     * @param card  new card
     * @param level ability level of the card
     */
    public void addCard(RunaCard card, int level) {
        cards.add(card);
        abilityLevel.add(level);
        Ui.printGainedCard(card.toString(), level);
    }

    /**
     * Checks if the hero must roll the dice if they want to play the card with the given index
     *
     * @param index given index
     * @return true if they must roll
     */
    public boolean mustRollDice(int index) {
        return cards.get(index).isPhysical();
    }

    /**
     * Removes an ability of the hero
     *
     * @param index index at cards list of the card to be removed
     */
    public void discard(int[] index) {
        Arrays.sort(index);
        for (int i : index) {
            int counter = 0;
            cards.remove(i);
            abilityLevel.remove(i);
            while (counter < index.length - 1) {
                counter++;
                if (index[counter] > index[counter - 1]) {
                    index[counter]--;
                }
            }
        }
    }

    /**
     * Checks if the card to be played requires a target, or it is a defensive one
     *
     * @param index index of the tested card
     * @return true if the card is not special
     */
    public boolean needsTarget(int index) {
        return !cards.get(index).isSpecial();
    }

}
