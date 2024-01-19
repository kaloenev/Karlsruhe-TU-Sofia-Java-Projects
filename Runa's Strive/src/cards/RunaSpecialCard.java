package cards;

import characters.Character;
import characters.Hero;

/**
 * Represents the defensive/special cards could be used by Runa
 *
 * @author usoia
 * @version 1.0
 */
public enum RunaSpecialCard implements SpecialCard {
    /* specific hero special cards */
    PARRY, FOCUS, REFLECT;

    /**
     * Causes its effect on the given character no matter defensive or offensive
     *
     * @param character the affected character
     * @param level     card ability level
     * @return 0 reflected damage
     */
    @Override
    public int causeEffect(Character character, int level) {
        if (this == RunaSpecialCard.PARRY) {
            parry((Hero) character, level);
        } else if (this == RunaSpecialCard.FOCUS) {
            focus((Hero) character, level);
        } else {
            reflect((Hero) character, level);
        }
        return 0;
    }

    /**
     * Sets the magic defense of the character to reflect an amount of damage back to a monster if it uses a Magic card
     *
     * @param character the beneficiary
     * @param level     ability card level
     */
    private void reflect(Hero character, int level) {
        character.setMagicDamageReduction(10 * level);
    }

    /**
     * Sets the physical defense of the character to parry an amount of damage if a monster uses a Physical card
     *
     * @param character the beneficiary
     * @param level     ability card level
     */
    private void parry(Hero character, int level) {
        character.setPhysicalDamageReduction(7 * level);
    }

    /**
     * Sets the focus points of the character to be raised next turn
     *
     * @param character the beneficiary
     * @param level     card level
     */
    private void focus(Hero character, int level) {
        character.setFocusPointsPromotion(level);
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
        return false;
    }

    /**
     * Checks if the Card is defensive/special
     *
     * @return true if so
     */
    @Override
    public boolean isSpecial() {
        return true;
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
     * Set the dice (mainly for physical cards)
     *
     * @param dice amount rolled
     */
    @Override
    public void setDice(int dice) {

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
