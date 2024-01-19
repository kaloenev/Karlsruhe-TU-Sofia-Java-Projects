package cards;

/**
 * Represents the cards Runa can use
 *
 * @author usoia
 * @version 1.0
 */
public interface RunaCard extends Card {
    /**
     * Set the dice (mainly for physical cards)
     *
     * @param dice amount rolled
     */
    void setDice(int dice);
}
