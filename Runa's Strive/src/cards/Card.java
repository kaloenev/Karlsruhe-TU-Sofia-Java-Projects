package cards;

import characters.Character;

/**
 * Represents all ability cards
 *
 * @author usoia
 * @version 1.0
 */
public interface Card {

    /**
     * Checks if the Card deals physical damage
     *
     * @return true if so
     */
    boolean isPhysical();

    /**
     * Checks if the Card deals magical damage
     *
     * @return true if so
     */
    boolean isMagic();

    /**
     * Checks if the Card is defensive/special
     *
     * @return true if so
     */
    boolean isSpecial();

    /**
     * Getter for the focus point cost (mainly for magic cards)
     *
     * @param level game level (monster cards' focus point cost depends on it)
     * @return focus point cost
     */
    int getFocusPointCost(int level);

    /**
     * Causes its effect on the given character no matter defensive or offensive
     *
     * @param character the affected character
     * @param level     card ability level
     * @return in case damage has been reflected returns its amount, so it can be dealt to the character
     */
    int causeEffect(Character character, int level);
}
