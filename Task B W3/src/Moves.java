public class Moves {

    //direction of the Ameise (N, O, S, W)
    private char direction;

    //color of the square the Ameise left
    private char lastColor;

    //color of the square the Ameise is stepping on
    private char nextColor;

    //coordinates of the Ameise on the gameboard
    private int positionY;
    private int positionX;

    //counts how many moves have been made
    //used to determine the first ever move the Ameise makes
    private int moveCount = 0;

    public Moves() {
    }

    //Getter for y coordinate
    public int getPositionY() {
        return positionY;
    }

    //Getter for x coordinate
    public int getPositionX() {
        return positionX;
    }

    /**
     * This method represents the change of the board through the first move.
     * A different method needs to be created as the square, where the "Ameise" started does not change its color,
     * as the coloring should appear when it steps on the given square, not after moving away from it.
     *
     * @param gameBoard the defined by reading the file gameBoard
     * @param moves     number of times the "Ameise" move
     */
    public void move(String[] gameBoard, int moves) {
        int initialPositionX = positionX;
        int initialPositionY = positionY;
        while (moves > 0) {
            switch (direction) {
                case 'N':
                    if (gameBoard[positionY - 1].charAt(positionX) == '0') {
                        moveNorthWhite(gameBoard);
                    } else moveNorthBlack(gameBoard);
                    break;
                case 'O':
                    if (gameBoard[positionY].charAt(positionX + 1) == '0') {
                        moveEastWhite(gameBoard);
                    } else moveEastBlack(gameBoard);
                    break;
                case 'S':
                    if (gameBoard[positionY + 1].charAt(positionX) == '0') {
                        moveSouthWhite(gameBoard);
                    } else moveSouthBlack(gameBoard);
                    break;
                case 'W':
                    if (gameBoard[positionY].charAt(positionX - 1) == '0') {
                        moveWestWhite(gameBoard);
                    } else moveWestBlack(gameBoard);
                    break;
            }

            //decides if that was the first move made by the Ameise (also if that's the first call of the move method)
            if (moveCount == 0) {
                //Ensures that the first square the Ameise has left has remained white
                gameBoard[initialPositionY] = gameBoard[initialPositionY].substring(0, initialPositionX) + '0'
                        + gameBoard[initialPositionY].substring(initialPositionX + 1);
            }
            moves--;
            moveCount++;
        }
    }

    /**
     * Moves the Ameise to the white square above it
     *
     * @param gameBoard the array which represents the gameboard
     */
    public void moveNorthWhite(String[] gameBoard) {
        //Save the color of the square the Ameise has stepped on in a variable, so position[3] can save the
        //color of the future square for the next move
        lastColor = nextColor;

        /*
         *The color of the new square on which the Ameise is about to step on.
         *It is saved so it can be changed while the next move is occuring.
         *In case a new function, which prints only the board colors without the Ameise on it, is to be added,
         *all we need to do is to find the square the Ameise is on and replace it with the opposite color
         *The program does not require the color of the square the Ameise is on, so I did not add such a function
         */
        nextColor = (gameBoard[positionY - 1].charAt(positionX));

        //builds a new String for the given lines in gameTBoard moving the Ameise north (represents the actual move)
        gameBoard[positionY - 1] = gameBoard[positionY - 1].substring(0, positionX) + 'O'
                + gameBoard[positionY - 1].substring(positionX + 1);

        //changes the color of the square the Ameise has just left by using the color variable
        changeColor(gameBoard);

        //change the other parameters to be used in the next move
        direction = 'O';
        positionY -= 1;
        //PositionX remains unchanged
    }

    //The other Helpermethods "move<Direction><Color>" do the same thing but in another direction for the other color
    //They also have the same definitions for @parameters, so I do not think I need to add Javadoc for each method
    public void moveEastWhite(String[] gameBoard) {
        lastColor = nextColor;
        nextColor = (gameBoard[positionY].charAt(positionX + 1));

        gameBoard[positionY] = gameBoard[positionY].substring(0, positionX + 1) + 'S'
                + gameBoard[positionY].substring(positionX + 2);

        changeColor(gameBoard);

        direction = 'S';
        positionX += 1;
        //PositionY remains unchanged
    }

    public void moveSouthWhite(String[] gameBoard) {
        lastColor = nextColor;
        nextColor = (gameBoard[positionY + 1].charAt(positionX));

        gameBoard[positionY + 1] = gameBoard[positionY + 1].substring(0, positionX) + 'W'
                + gameBoard[positionY + 1].substring(positionX + 1);

        changeColor(gameBoard);

        direction = 'W';
        positionY += 1;
        //PositionX remains unchanged
    }

    public void moveWestWhite(String[] gameBoard) {
        lastColor = nextColor;
        nextColor = (gameBoard[positionY].charAt(positionX - 1));

        gameBoard[positionY] = gameBoard[positionY].substring(0, positionX - 1) + 'N'
                + gameBoard[positionY].substring(positionX);

        changeColor(gameBoard);

        direction = 'N';
        positionX -= 1;
        //PositionY remains unchanged
    }

    public void moveNorthBlack(String[] gameBoard) {
        lastColor = nextColor;
        nextColor = (gameBoard[positionY - 1].charAt(positionX));

        gameBoard[positionY - 1] = gameBoard[positionY - 1].substring(0, positionX) + 'W'
                + gameBoard[positionY - 1].substring(positionX + 1);

        changeColor(gameBoard);

        direction = 'W';
        positionY -= 1;
        //PositionX remains unchanged
    }

    public void moveEastBlack(String[] gameBoard) {
        lastColor = nextColor;
        nextColor = (gameBoard[positionY].charAt(positionX + 1));

        gameBoard[positionY] = gameBoard[positionY].substring(0, positionX + 1) + 'N'
                + gameBoard[positionY].substring(positionX + 2);

        changeColor(gameBoard);

        direction = 'N';
        positionX += 1;
        //PositionY remains unchanged
    }

    public void moveSouthBlack(String[] gameBoard) {
        lastColor = nextColor;
        nextColor = (gameBoard[positionY + 1].charAt(positionX));

        gameBoard[positionY - 1] = gameBoard[positionY - 1].substring(0, positionX) + 'O'
                + gameBoard[positionY - 1].substring(positionX + 1);

        changeColor(gameBoard);

        direction = 'O';
        positionY += 1;
        //PositionX remains unchanged
    }

    public void moveWestBlack(String[] gameBoard) {
        lastColor = nextColor;
        nextColor = (gameBoard[positionY].charAt(positionX - 1));

        gameBoard[positionY] = gameBoard[positionY].substring(0, positionX - 1) + 'S'
                + gameBoard[positionY].substring(positionX);

        changeColor(gameBoard);

        direction = 'S';
        positionX -= 1;
        //PositionY remains unchanged
    }

    /**
     * Finds where the Ameise stays on the gameBoard and which direction it faces
     * This process is executed only once in the beginning as it is relatively expensive in resources
     *
     * @param gameBoard the gameboard (an array, where the Ameise moves)
     */
    public void findInitialPosition(String[] gameBoard) {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length(); j++) {
                if (gameBoard[i].charAt(j) != '0' && gameBoard[i].charAt(j) != '1') {
                    direction = gameBoard[i].charAt(j);
                    positionY = i;
                    positionX = j;

                    //assuming the color of the starting square is always white
                    nextColor = '0';
                }
            }
        }
    }

    /**
     * Changes the color of the square the Ameise has just left by using the color variable which has it's last color
     *
     * @param gameBoard the gameboard (an array, where the Ameise moves)
     */
    public void changeColor(String[] gameBoard) {
        if (lastColor == '1') {
            gameBoard[positionY] = gameBoard[positionY].substring(0, positionX) + '0'
                    + gameBoard[positionY].substring(positionX + 1);
        } else {
            gameBoard[positionY] = gameBoard[positionY].substring(0, positionX) + '1'
                    + gameBoard[positionY].substring(positionX + 1);
        }
    }
}
