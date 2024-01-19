package cards;

import characters.Character;
import characters.Monster;
import game.Element;

/**
 * Represents the magic cards Runa can use
 *
 * @author usoia
 * @version 1.0
 */
public enum RunaMagicCard implements RunaCard {
    /* specific hero magic cards */
    WATER(Element.LIGHTNING, 4, 0),
    ICE(Element.WATER, 4, 2),
    FIRE(Element.ICE, 5, 0),
    LIGHTNING(Element.FIRE, 5, 2);

    private final Element strongAgainst;
    private final int damageMultiplier = 2;
    private final int bonusDamage;
    private final int bonusDamage2;
    private int focusPoints = 0;
    private final int focusPointCost = 1;

    /**
     * Initializes Runa's possible physical cards with their constants
     *
     * @param strongAgainst Element they are strong against (deal bonus damage)
     * @param bonusDamage   constant bonus damage
     * @param bonusDamage2  constant bonus damage
     */
    RunaMagicCard(Element strongAgainst, int bonusDamage, int bonusDamage2) {
        this.strongAgainst = strongAgainst;
        this.bonusDamage = bonusDamage;
        this.bonusDamage2 = bonusDamage2;
    }

    /**
     * Causes its effect on the given character no matter defensive or offensive
     *
     * @param character the affected character
     * @param level     card ability level
     * @return 0 reflected damage
     */
    @Override
    public int causeEffect(Character character, int level) {
        dealDamage((Monster) character, level);
        return 0;
    }

    /**
     * Deals magic damage to the given character (monster)
     *
     * @param monster character to be damaged
     * @param level   card ability level
     */
    private void dealDamage(Monster monster, int level) {
        int damage = (damageMultiplier * level + bonusDamage) * focusPoints + bonusDamage2;
        if (this.strongAgainst.equals(monster.getElement())) {
            damage += damageMultiplier * level;
        }
        monster.takeMagicDamage(damage);
    }

    /**
     * Checks if the Card deals physical damage
     *
     * @return true if so
     */
    @Override
    public boolean isPhysical() {
        return false;
    }

    /**
     * Checks if the Card deals magical damage
     *
     * @return true if so
     */
    @Override
    public boolean isMagic() {
        return true;
    }

    /**
     * Checks if the Card is defensive/special
     *
     * @return true if so
     */
    @Override
    public boolean isSpecial() {
        return false;
    }

    /**
     * Getter for the focus point cost (mainly for magic cards)
     *
     * @param level game level (monster cards' focus point cost depends on it)
     * @return focus point cost
     */
    @Override
    public int getFocusPointCost(int level) {
        return focusPointCost;
    }

    /**
     * Set the dice (mainly for physical cards)
     *
     * @param dice amount rolled
     */
    @Override
    public void setDice(int dice) {
        this.focusPoints = dice;
    }

    /**
     * Returns the card name starting with a capital letter
     *
     * @return String representation of the card
     */
    @Override
    public String toString() {
        char character = name().charAt(0);
        return character + this.name().substring(1).toLowerCase();
    }
}
