import java.util.Stack;

public class Scrabble {
    private char[][] gameBoard;
    private Player playerOne;
    private Player playerTwo;
    private Player activePlayer;

    public Scrabble(Player playerOne, Player playerTwo) {
        this.gameBoard = new char[10][10];
        // fills the gameBoard with the required initial characters
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                gameBoard[i][j] = '#';
            }
        }
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.activePlayer = playerOne;
    }

    public char[][] getGameBoard() {
        return gameBoard;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    private void changeActivePlayer() {
        if (activePlayer.equals(playerOne)) {
            activePlayer = playerTwo;
        }
        else activePlayer = playerOne;
    }

    private boolean errorBehind(String signs, int i, int j, char[][] tempGameBoard) {
        try {
            switch(tempGameBoard[i][j]) {
                case '+':
                    if (signs.charAt(1) == '+' || signs.charAt(1) == '*' || signs.charAt(0) == '-') {
                        System.out.println("Error, not a valid equation");
                        return true;
                    }
                    else {
                        return false;
                    }
                case '*':
                    break;
                case '-':
                    break;
                case '#':
                    break;
                    // when the previous character is a number
                default:
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {}
        return false;
    }

    private boolean errorInFront(String signs, int i, int j, char[][] tempGameBoard) {
        try {
            switch(tempGameBoard[i][j]) {
                case '+':

                    break;
                case '*':
                    break;
                case '-':
                    break;
                case '#':
                    break;
                    // when the next character is a number
                default:
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {}
        return false;
    }

    public void place(String signs, int i, int j, int numberOfSigns, char plane) {
        char[][] tempGameBoard = gameBoard;
        int stringCounter = 0;
        if (plane == 'V') {
            int limit = i + numberOfSigns;
            while (i < limit) {
                // checks for overlapping of characters
                if (gameBoard[i][j] != '#') {
                    System.out.println("Error, you can only place a character on an empty field");
                    return;
                }
                tempGameBoard[i][j] = signs.charAt(stringCounter);
                i++;
                stringCounter++;
            }
            if (errorBehind(signs, i - numberOfSigns, j, tempGameBoard) || errorInFront(signs, i + 1, j, tempGameBoard)) {
                System.out.println("Error, ");
                return;
            }
            for (int k = 0; k < numberOfSigns; k++) {
                if (errorBehind(signs, i - k, j - 1, tempGameBoard) || errorInFront(signs, i - k, j + 1, tempGameBoard)) {
                    System.out.println("Error, ");
                    return;
                }
            }
            activePlayer.removeSigns(signs);
            gameBoard = tempGameBoard;
            System.out.println("OK");
            // sets the score to the value the method that evaluates the postfix expression returns;
            activePlayer.setScore(evaluateScore(signs));
        }

        // povtoreniq!
        else if (plane == 'H') {
            int limit = j + numberOfSigns;
            while (j < limit) {
                if (gameBoard[i][j] != '#') {
                    System.out.println("Error, you can only place a character on an empty field");
                    return;
                }
                tempGameBoard[i][j] = signs.charAt(stringCounter);
                j++;
                stringCounter++;
            }
        }
        else {
            System.out.println("Error, please select a correct plane Vertical (V) or Horizontal (H)");
            return;
        }
        changeActivePlayer();
    }

    private Integer evaluateScore(String signs) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < signs.length(); i++) {
            try {
                stack.push(Integer.parseInt(String.valueOf(signs.charAt(i))));
            } catch (NumberFormatException e) {
                if (signs.charAt(i) == '*') {
                    stack.push(stack.pop() * stack.pop());
                }
                else if (signs.charAt(i) == '+') {
                    stack.push(stack.pop() + stack.pop());
                }
                else stack.push(stack.pop() - stack.pop());
            }
        }
        return stack.pop();
    }
}
