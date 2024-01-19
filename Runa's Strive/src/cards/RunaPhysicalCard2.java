package cards;

import characters.Character;
import characters.Monster;

/**
 * Represents the physical cards Runa can use
 *
 * @author usoia
 * @version 1.0
 */
public enum RunaPhysicalCard2 implements RunaCard {
    /* specific monster physical cards */
    THRUST(6, 1, 4), PIERCE(7, 1, 5);
    private final int damageMultiplier;
    private final int diceMultiplier;
    private final int bonusDamage;
    private int dice = 0;

    /**
     * Initializes Runa's possible physical cards with their constants
     *
     * @param damageMultiplier constant multiplier
     * @param diceMultiplier   constant multiplier
     * @param bonusDamage      constant bonus damage
     */
    RunaPhysicalCard2(int damageMultiplier, int diceMultiplier, int bonusDamage) {
        this.damageMultiplier = damageMultiplier;
        this.diceMultiplier = diceMultiplier;
        this.bonusDamage = bonusDamage;
    }

    /**
     * Deals physical damage to the given character (hero)
     *
     * @param monster character to be damaged
     * @param level   card ability level
     */
    public void dealDamage(int level, Monster monster) {
        int damage = damageMultiplier * level + diceMultiplier * dice;
        if (dice >= 6) {
            damage += level * bonusDamage;
        }
        monster.takePhysicalDamage(damage);
        this.dice = 0;
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
