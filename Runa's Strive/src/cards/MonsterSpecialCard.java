package cards;

import characters.Character;
import characters.Monster;

/**
 * Represents the defensive/special cards could be used by Monsters
 *
 * @author usoia
 * @version 1.0
 */
public enum MonsterSpecialCard implements MonsterCard, SpecialCard {
    /* specific monster special cards */
    DEFLECT, BLOCK, FOCUS;

    /**
     * Causes its effect on the given character no matter defensive or offensive
     *
     * @param character the affected character
     * @param level     card ability level
     * @return 0 reflected damage
     */
    @Override
    public int causeEffect(Character character, int level) {
        if (this == MonsterSpecialCard.DEFLECT) {
            deflect((Monster) character, level);
        } else if (this == MonsterSpecialCard.BLOCK) {
            block((Monster) character, level);
        } else {
            focus((Monster) character, level);
        }
        return 0;
    }

    /**
     * Deflects magic damage in case the hero plays a magic card
     *
     * @param monster the beneficiary
     * @param level   card level
     */
    private void deflect(Monster monster, int level) {
        monster.setMagicDamageReduction(11 * level + 2);
    }

    /**
     * Blocks physical damage in case the hero plays a magic card
     *
     * @param monster the beneficiary
     * @param level   card level
     */
    private void block(Monster monster, int level) {
        monster.setPhysicalDamageReduction(7 * level);
    }

    /**
     * Sets the focus points of the character to be raised next turn
     *
     * @param monster the beneficiary
     * @param level   card level
     */
    private void focus(Monster monster, int level) {
        monster.setFocusPointsPromotion(level);
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
