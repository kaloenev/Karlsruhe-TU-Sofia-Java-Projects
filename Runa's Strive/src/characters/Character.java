package characters;

/**
 * Interface representing the characters with most of their functions
 *
 * @author usoia
 * @version 1.0
 */
public interface Character {

    /**
     * Character takes physical damage
     *
     * @param damage damage taken
     */
    void takePhysicalDamage(int damage);

    /**
     * Character takes magic damage
     *
     * @param damage damage taken
     * @return reflected damage by character
     */
    int takeMagicDamage(int damage);

    /**
     * Setter for the magic defense of a character
     *
     * @param damageReduction damage by which next magic damage will be reduced
     */
    void setMagicDamageReduction(int damageReduction);

    /**
     * Setter for the physical defense of a character
     *
     * @param damageReduction damage by which next physical damage will be reduced
     */
    void setPhysicalDamageReduction(int damageReduction);

    /**
     * Represents the resets of magic and physical damage reduction as well as focus points promotion
     * at the beginning of a new turn
     *
     * @return focusPoints gained from previous turn played focus
     */
    int beforeTurn();

    /**
     * Setter for when the focus card is played
     *
     * @param focusPointsPromotion amount by which focus points will be promoted
     */
    void setFocusPointsPromotion(int focusPointsPromotion);

    /**
     * Getter for focus points
     *
     * @return current focus points
     */
    int getFocusPoints();

    /**
     * Getter for hp
     *
     * @return current hp
     */
    int getHp();

    /**
     * Getter for character max hp
     *
     * @return max hp of a character
     */
    int getMaxHp();

    /**
     * Reduces the focus points by the cost of a magical card
     *
     * @param focusPoints amount to be reduced with
     */
    void reduceFocusPoints(int focusPoints);
}
