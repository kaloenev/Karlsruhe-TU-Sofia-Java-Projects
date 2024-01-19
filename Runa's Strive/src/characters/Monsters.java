package characters;

import cards.MonsterCard;
import cards.MonsterMagicCard;
import cards.MonsterPhysicalCard1;
import cards.MonsterPhysicalCard2;
import cards.MonsterSpecialCard;
import game.Element;
import ui.Ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class representing the monsters with their constant attributes
 *
 * @author usoia
 * @version 1.0
 */
public enum Monsters implements Monster {
    /* The Monsters of each level with their attributes */
    FROG("Frog", 1, new ArrayList<>(Arrays.asList(MonsterSpecialCard.FOCUS, MonsterMagicCard.WATER)),
            Element.WATER, 16, new int[]{1, 1}),
    GHOST("Ghost", 1, new ArrayList<>(Arrays.asList(MonsterSpecialCard.FOCUS, MonsterMagicCard.ICE)),
            Element.ICE, 15, new int[]{1, 1}),
    GORGON("Gorgon", 1, new ArrayList<>(Arrays.asList(MonsterSpecialCard.FOCUS, MonsterMagicCard.FIRE)),
            Element.FIRE, 13, new int[]{1, 1}),
    SKELETON("Skeleton", 1, new ArrayList<>(Arrays.asList(MonsterSpecialCard.FOCUS, MonsterMagicCard.LIGHTNING)),
            Element.LIGHTNING, 14, new int[]{1, 1}),
    SPIDER("Spider", 1, new ArrayList<>(Arrays.asList(MonsterPhysicalCard2.BITE, MonsterSpecialCard.BLOCK)),
            Element.NONE, 15, new int[]{1, 1}),
    GOBLIN("Goblin", 1, new ArrayList<>(Arrays.asList(MonsterPhysicalCard2.SMASH, MonsterSpecialCard.DEFLECT)),
            Element.NONE, 12, new int[]{1, 1}),
    RAT("Rat", 1, new ArrayList<>(Arrays.asList(MonsterSpecialCard.BLOCK, MonsterPhysicalCard1.CLAW)),
            Element.NONE, 14, new int[]{1, 1}),
    MUSHROOMLIN("Mushroomlin", 1, new ArrayList<>(Arrays.asList(MonsterSpecialCard.DEFLECT,
            MonsterPhysicalCard1.SCRATCH)), Element.NONE, 20, new int[]{1, 1}),
    // level 2 characters.Monsters
    SNAKE("Snake", 2, new ArrayList<>(Arrays.asList(MonsterPhysicalCard2.BITE, MonsterSpecialCard.FOCUS,
            MonsterMagicCard.ICE)), Element.ICE, 31, new int[]{2, 2, 2}),
    DARKELF("Dark Elf", 2, new ArrayList<>(Arrays.asList(MonsterSpecialCard.FOCUS, MonsterMagicCard.WATER,
            MonsterMagicCard.LIGHTNING)), Element.NONE, 34, new int[]{2, 1, 1}),
    SHADOWBLADE("Shadow Blade", 2, new ArrayList<>(Arrays.asList(MonsterPhysicalCard1.SCRATCH, MonsterSpecialCard.FOCUS,
            MonsterMagicCard.LIGHTNING)), Element.LIGHTNING, 27, new int[]{2, 2, 2}),
    HORNET("Hornet", 2, new ArrayList<>(Arrays.asList(MonsterPhysicalCard1.SCRATCH, MonsterSpecialCard.FOCUS,
            MonsterMagicCard.FIRE, MonsterMagicCard.FIRE)), Element.FIRE, 32, new int[]{2, 2, 2, 1}),
    TARANTULA("Tarantula", 2, new ArrayList<>(Arrays.asList(MonsterPhysicalCard2.BITE, MonsterSpecialCard.BLOCK,
            MonsterPhysicalCard1.SCRATCH)), Element.NONE, 33, new int[]{2, 2, 2}),
    BEAR("Bear", 2, new ArrayList<>(Arrays.asList(MonsterPhysicalCard1.CLAW, MonsterSpecialCard.DEFLECT,
            MonsterSpecialCard.BLOCK)), Element.NONE, 40, new int[]{2, 2, 2}),
    MUSHROOMLON("Mushroomlon", 2, new ArrayList<>(Arrays.asList(MonsterSpecialCard.DEFLECT,
            MonsterPhysicalCard1.SCRATCH, MonsterSpecialCard.BLOCK)), Element.NONE, 50, new int[]{2, 2, 2}),
    WILDBOAR("Wild Boar", 2, new ArrayList<>(Arrays.asList(MonsterPhysicalCard1.SCRATCH, MonsterSpecialCard.DEFLECT,
            MonsterPhysicalCard1.SCRATCH)), Element.NONE, 27, new int[]{2, 2, 2});

    private final String name;
    private final int level;
    private final List<MonsterCard> cards;
    private MonsterCard nextCard;
    private int nextCardIndex;
    private final Element element;
    private int hp;
    private final int maxHp;
    private final int[] abilityLevel;
    private int magicDamageReduction = 0;
    private int physicalDamageReduction = 0;

    /**
     * Initializes the monsters with their constant attributes
     *
     * @param name         monster name
     * @param level        level on which Runa could play against them
     * @param cards        monster cards to be played
     * @param element      element of the monster
     * @param maxHp        max Hp of the monster
     * @param abilityLevel ability levels of their cards
     */
    Monsters(String name, int level, List<MonsterCard> cards, Element element, int maxHp, int[] abilityLevel) {
        this.name = name;
        this.level = level;
        this.cards = cards;
        this.element = element;
        this.maxHp = maxHp;
        hp = maxHp;
        this.abilityLevel = abilityLevel;
        nextCardIndex = 0;
        nextCard = cards.get(nextCardIndex);
    }

    /**
     * Getter for the monster name
     *
     * @return monster name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * getter for the monster level
     *
     * @return monster level
     */
    @Override
    public int getLevel() {
        return level;
    }

    /**
     * Getter for the next card to be played by the monster
     *
     * @return the next card to be played
     */
    @Override
    public MonsterCard getNextCard() {
        return nextCard;
    }

    /**
     * Setter for the next card to be played by the monster
     */
    @Override
    public void setNextCard(MonsterCard nextCard) {
        this.nextCard = nextCard;
    }

    /**
     * Getter for the element of the monster
     *
     * @return element
     */
    @Override
    public Element getElement() {
        return element;
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
     * Getter for focus points
     *
     * @return current focus points
     */
    @Override
    public int getFocusPoints() {
        return focusPoints;
    }

    /**
     * Reduces the focus points by the cost of a magical card
     *
     * @param focusPoints amount to be reduced with
     */
    @Override
    public void reduceFocusPoints(int focusPoints) {
        this.focusPoints -= focusPoints;
    }

    private int focusPoints = 0;
    private int focusPointsPromotion;

    /**
     * Getter for the monster current card ability level
     *
     * @return ability level
     */
    @Override
    public int getAbilityLevel() {
        return abilityLevel[nextCardIndex];
    }

    /**
     * Monster plays the current card represented by nextCard dealing the damage to the given character if card not special
     *
     * @param character given character
     */
    @Override
    public void takeTurn(Character character) {
        Ui.printUsedCard(name, nextCard.toString(), abilityLevel[nextCardIndex]);
        int reflectDamage = 0;
        while (nextCard.isMagic()) {
            if (focusPoints == 0) {
                if (nextCardIndex == cards.size() - 1) {
                    nextCardIndex = 0;
                } else {
                    nextCardIndex++;
                }
                nextCard = cards.get(nextCardIndex);
            } else {
                reduceFocusPoints(abilityLevel[nextCardIndex]);
                break;
            }
        }
        if (nextCard.isSpecial()) {
            nextCard.causeEffect(this, abilityLevel[nextCardIndex]);
        } else {
            reflectDamage = nextCard.causeEffect(character, abilityLevel[nextCardIndex]);
        }
        if (reflectDamage > 0) {
            takeMagicDamage(reflectDamage);
        }

        if (nextCardIndex == cards.size() - 1) {
            nextCardIndex = 0;
        } else {
            nextCardIndex++;
        }
        nextCard = cards.get(nextCardIndex);
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
        physicalDamageReduction = 0;

        if (hp <= 0) {
            Ui.characterDied(name);
        }
    }

    /**
     * Character takes magic damage
     *
     * @param damage damage taken
     * @return 0 damage is possibly reflected by character
     */
    @Override
    public int takeMagicDamage(int damage) {
        int finalDamage = damage - magicDamageReduction;
        if (finalDamage > 0) {
            hp -= finalDamage;
            Ui.printDamage(name, finalDamage, "mag. damage");
        }
        magicDamageReduction = 0;

        if (hp <= 0) {
            Ui.characterDied(name);
        }

        return 0;
    }

    /**
     * Setter for the magic defense of a character
     *
     * @param damageReduction damage by which next magic damage will be reduced
     */
    @Override
    public void setMagicDamageReduction(int damageReduction) {
        this.magicDamageReduction = damageReduction;
    }

    /**
     * Setter for the physical defense of a character
     *
     * @param damageReduction damage by which next physical damage will be reduced
     */
    @Override
    public void setPhysicalDamageReduction(int damageReduction) {
        this.physicalDamageReduction = damageReduction;
    }

    /**
     * Setter for when the focus card is played
     *
     * @param focusPointsPromotion amount by which focus points will be promoted
     */
    @Override
    public void setFocusPointsPromotion(int focusPointsPromotion) {
        this.focusPointsPromotion = focusPointsPromotion;
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
     * Increases the focus points of the character (called before the actual turn)
     */
    public void increaseFocusPoints() {
        focusPoints += focusPointsPromotion;
    }
}
