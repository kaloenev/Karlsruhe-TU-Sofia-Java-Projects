package cards;

import characters.Character;
import characters.Monster;

/**
 * Represents the physical cards Runa can use
 *
 * @author usoia
 * @version 1.0
 */
public enum RunaPhysicalCard1 implements RunaCard {
    /* specific hero physical cards */
    SLASH(4, 1), SWING(5, 1);
    private final int damageMultiplier;
    private final int diceMultiplier;
    private int dice = 0;

    /**
     * Initializes Runa's possible physical cards with their constants
     *
     * @param damageMultiplier constant multiplier
     * @param diceMultiplier   constant multiplier
     */
    RunaPhysicalCard1(int damageMultiplier, int diceMultiplier) {
        this.damageMultiplier = damageMultiplier;
        this.diceMultiplier = diceMultiplier;
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
        dealDamage(level, (Monster) character);
        breakFocus((Monster) character);
        return 0;
    }

    /**
     * Getter for the focus point cost (mainly for magic cards)
     *
     * @param level game level (monster cards' focus point cost depends on it)
     * @return focus point cost
     */
    @Override
    public int getFocusPointCost(int level) {
        return 0;
    }

    /**
     * Deals physical damage to the given character (hero)
     *
     * @param monster character to be damaged
     * @param level   card ability level
     */
    private void dealDamage(int level, Monster monster) {
        int damage = damageMultiplier * level + diceMultiplier * dice;
        monster.takePhysicalDamage(damage);
        this.dice = 0;
    }

    /**
     * Breaks the focus of the given monster, in case they played the focus card
     *
     * @param monster given monster
     */
    private void breakFocus(Monster monster) {
        monster.setFocusPointsPromotion(0);
    }

    /**
     * Checks if the Card deals physical damage
     *
     * @return true if so
     */
    @Override
    public boolean isPhysical() {
        return true;
    }

    /**
     * Checks if the Card deals magical damage
     *
     * @return true if so
     */
    @Override
    public boolean isMagic() {
        return false;
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
     * Set the dice (mainly for physical cards)
     *
     * @param dice amount rolled
     */
    public void setDice(int dice) {
        this.dice = dice;
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
