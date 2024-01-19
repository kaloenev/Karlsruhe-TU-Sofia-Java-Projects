import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Character> startSigns;
    private int score;
    private List<String> moves;

    public Player(List<Character> startSigns) {
        this.startSigns = startSigns;
        this.score = 0;
        this.moves = new ArrayList<>();
    }

    public List<Character> getStartSigns() {
        return startSigns;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score += score;
    }

    public List<String> getMoves() {
        return moves;
    }

    public void addMove(String move) {
        this.moves.add(move);
    }

    /**
     * Checks if the signs the player is trying to place are available and have not already been used
     * @param signs
     */
    public void removeSigns(String signs) {
        for (int i = 0; i < signs.length(); i++) {
            for (int j = 0; j < startSigns.size(); j++) {
                if (signs.charAt(i) == startSigns.get(j)) {
                    startSigns.remove(signs.charAt(i));
                    break;
                }
                else if (j == startSigns.size() - 1) {
                    System.out.println("Error, character not available");
                    return;
                }
            }
        }
    }
}
