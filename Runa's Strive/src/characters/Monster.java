package characters;

import game.Element;
import cards.MonsterCard;

/**
 * Interface representing the monsters with most of their functions
 *
 * @author usoia
 * @version 1.0
 */
public interface Monster extends Character {
    /**
     * Getter for the monster name
     *
     * @return monster name
     */
    String getName();

    /**
     * Getter for the monster level
     *
     * @return monster level
     */
    int getLevel();

    /**
     * Getter for the next card to be played by the monster
     *
     * @return the next card to be played
     */
    MonsterCard getNextCard();

    /**
     * Setter for the next card to be played by the monster
     *
     * @param nextCard monster's next card in list
     */
    void setNextCard(MonsterCard nextCard);

    /**
     * Getter for the element of the monster
     *
     * @return element
     */
    Element getElement();

    /**
     * Getter for the monster current card ability level
     *
     * @return ability level
     */
    int getAbilityLevel();

    /**
     * Monster plays the current card represented by nextCard dealing the damage to the given character,
     * if the card is not special
     *
     * @param character given character
     */
    void takeTurn(Character character);
}
