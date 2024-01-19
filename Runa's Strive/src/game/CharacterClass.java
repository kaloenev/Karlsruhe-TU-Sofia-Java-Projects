package game;

import cards.RunaCard;
import cards.RunaMagicCard;
import cards.RunaPhysicalCard1;
import cards.RunaPhysicalCard2;
import cards.RunaSpecialCard;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * This class represents the type of character Runa can play as
 *
 * @author usoia
 * @version 1.0
 */
public enum CharacterClass {
    /* All possible classes a hero can be */
    MAGE(new LinkedList<>(Arrays.asList(RunaSpecialCard.FOCUS, RunaMagicCard.WATER))),
    WARRIOR(new LinkedList<>(Arrays.asList(RunaPhysicalCard2.THRUST, RunaSpecialCard.PARRY))),
    PALADIN(new LinkedList<>(Arrays.asList(RunaPhysicalCard1.SLASH, RunaSpecialCard.REFLECT)));

    private final LinkedList<RunaCard> specificCards;

    /**
     * Initializes the classes with their specific cards
     *
     * @param specificCards class specific starting cards
     */
    CharacterClass(LinkedList<RunaCard> specificCards) {
        this.specificCards = specificCards;
    }

    /**
     * Getter
     *
     * @return class specific cards
     */
    public LinkedList<RunaCard> getSpecificCards() {
        return new LinkedList<>(specificCards);
    }
}
