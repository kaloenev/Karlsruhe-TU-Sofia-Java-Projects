package cards;

import characters.Character;

/**
 * Represents the magic cards the Monsters can use
 *
 * @author usoia
 * @version 1.0
 */
public enum MonsterMagicCard implements MonsterCard {
    /* specific monster magic cards */
    /**
     * specific monster magic cards
     */
    WATER(8), ICE(10), FIRE(12), LIGHTNING(14);
    /* Constant damage multiplier */
    private final int damageMultiplier;
    /* Constant bonus damage */
    private final int bonusDamage = 2;

    /**
     * Initializes the cards with their magic multiplier
     *
     * @param damageMultiplier constant multiplier
     */
    MonsterMagicCard(int damageMultiplier) {
        this.damageMultiplier = damageMultiplier;
    }

    /**
     * Causes its effect on the given character no matter defensive or offensive
     *
     * @param character the affected character
     * @param level     card ability level
     * @return in case damage has been reflected returns its amount, so it can be dealt to the character
     */
    @Override
    public int causeEffect(Character character, int level) {
        return dealDamage(character, level);
    }

    /**
     * Deals magic damage to the given character (monster)
     *
     * @param character character to be damaged
     * @param level     card ability level
     * @return reflected damage
     */
    private int dealDamage(Character character, int level) {
        return character.takeMagicDamage(damageMultiplier * level + bonusDamage);
    }

    /**
     * Getter for the focus point cost (mainly for magic cards)
     *
     * @param level game level (monster cards' focus point cost depends on it)
     * @return focus point cost
     */
    @Override
    public int getFocusPointCost(int level) {
        return level;
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
