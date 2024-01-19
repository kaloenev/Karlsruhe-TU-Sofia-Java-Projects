package game;

/**
 * This class represents Runa's dice
 *
 * @author usoia
 * @version 1.0
 */
public enum Dice {
    /* All possible dices a hero can have */
    D4(4), D6(6), D8(8), D10(10), D12(12);

    private final int maxFocusPoints;

    /**
     * Initializes the dices with their max focus points equal to their highest possible value
     *
     * @param maxFocusPoints highest possible value on the dice
     */
    Dice(int maxFocusPoints) {
        this.maxFocusPoints = maxFocusPoints;
    }

    /**
     * Getter
     *
     * @return maximum focus points when playing with a given dice
     */
    public int getMaxFocusPoints() {
        return maxFocusPoints;
    }
}
