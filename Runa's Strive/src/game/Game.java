package game;

/**
 * This interface represents the Game with its functions
 *
 * @author usoia
 * @version 1.0
 */
public interface Game {

    /**
     * Checks the hp of Runa and the monsters and calls a print function if runa gained focus points
     */
    void beforeTurn();

    /**
     * Shuffles the cards and monsters lists with the given cardSeed and monsterSeed
     *
     * @param cardSeed    given card seed
     * @param monsterSeed given monster seed
     */
    void shuffle(int cardSeed, int monsterSeed);

    /**
     * Represents the sequential taking of a turn (first Runa then the Monsters)
     *
     * @param index        index of Runa's card to be used
     * @param monsterIndex index of the target monster
     * @param dice         value of the dice
     */
    void takeTurn(int index, int monsterIndex, int dice);

    /**
     * Builds a String of the cards runa has to choose from as reward
     *
     * @return String with the possible cards Runa can get as reward
     */
    String getRewardCardOptions();

    /**
     * Checks if the current Stage has ended (all monsters have been killed)
     *
     * @return true if the stage continues, false otherwise
     */
    boolean checkStageConditions();

    /**
     * Checks if the level was switched
     *
     * @return true if the level continues, false otherwise
     */
    boolean checkLevelConditions();

    /**
     * Builds a String array of the cards runa has and returns it
     *
     * @return String array with the possible cards Runa could play with their ability level
     */
    String[] getCardOptions();

    /**
     * Getter
     *
     * @return the current level
     */
    int getLevel();

    /**
     * Getter
     *
     * @return the current stage
     */
    int getStage();

    /**
     * Getter
     *
     * @return hp of Runa
     */
    int getRunaHp();

    /**
     * Getter
     *
     * @return the current focus points of Runa
     */
    int getRunaFocusPoints();

    /**
     * Getter
     *
     * @return the maximum focus points Runa could have
     */
    int getRunaMaxFocusPoints();

    /**
     * Builds a String that brings information about the current monsters
     *
     * @return String with the current monster attributes, separated with commas
     */
    String getCurrentMonstersStats();

    /**
     * Getter
     *
     * @return maximum hp Runa could have
     */
    int getRunaMaxHp();

    /**
     * Checks if Runa must roll the dice
     *
     * @param index index of the card to be played
     * @return true if she must roll
     */
    boolean mustRollDice(int index);

    /**
     * Checks the max value of Runa's dice
     *
     * @return the highest number Runa could roll with the current dice
     */
    int heroMaxDice();

    /**
     * Maximum value of a dice getter
     *
     * @return maximum value a dice can have
     */
    int getMaxDice();

    /**
     * Upgrades the Runa's current dice to the next one
     *
     * @return the value of the dice, so it can be printed
     */
    int upgradeDice();

    /**
     * Represents the drawing of the cards form the card possibilities List into Runa's abilities List
     *
     * @param cardNumbers the indexes of the cards to be added to Runa's abilities
     */
    void drawCards(int[] cardNumbers);

    /**
     * Checks if Runa is still alive (has not lost the game)
     *
     * @return true if she lost (is dead) false otherwise
     */
    boolean runaHasLost();

    /**
     * Discards the cards with the given indexes from Runa's abilities and heals her for max 10 hp per discard
     *
     * @param index given card indexes
     */
    void discard(int[] index);

    /**
     * Checks if the card with the given index needs a target or is a defensive one
     *
     * @param index given card index
     * @return true if it needs a target
     */
    boolean needsTarget(int index);
}
