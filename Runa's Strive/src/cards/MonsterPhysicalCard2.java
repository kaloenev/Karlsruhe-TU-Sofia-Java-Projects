package cards;

import characters.Character;
import characters.Hero;

/**
 * Represents the physical cards the Monsters can use
 *
 * @author usoia
 * @version 1.0
 */
public enum MonsterPhysicalCard2 implements MonsterCard {
    /* specific monster physical cards */
    SMASH(8), BITE(10);
    private final int damageMultiplier;

    /**
     * Initializes the monsters' physical cards
     *
     * @param damageMultiplier constant damage multiplier
     */
    MonsterPhysicalCard2(int damageMultiplier) {
        this.damageMultiplier = damageMultiplier;
    }

    /**
     * Deals physical damage to the given character (monster)
     *
     * @param hero  character to be damaged
     * @param level card ability level
     */
    public void dealDamage(int level, Hero hero) {
        int damage = damageMultiplier * level;
        hero.takePhysicalDamage(damage);
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
        dealDamage(level, (Hero) character);
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
