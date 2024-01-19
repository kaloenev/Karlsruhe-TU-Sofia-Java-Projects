import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author usoia
 * @version 1.0
 * This class completes the input/output formatting
 *
 */
public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        List<Character> playerListOne = new ArrayList<>();
        List<Character> playerListTwo = new ArrayList<>();

        try {
            for (int i = 0; i < args[0].length(); i++) {
                playerListOne.add(args[0].charAt(i));
            }

            // there are two loops, one for each player's play stones, in case they do not start with the same amount
            for (int i = 0; i < args[1].length(); i++) {
                playerListTwo.add(args[1].charAt(i));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error, you need to set the characters, the players have in the beginning");
            return;
        }
        Player playerOne = new Player(playerListOne);
        Player playerTwo = new Player(playerListTwo);

        Scrabble scrabble = new Scrabble(playerOne, playerTwo);

        //Ensures the commands are accepted until the quit command is called
        while (true) {
            String line = reader.readLine();

            //Splits the String
            String[] tokens = line.split(" ");

            // Possible commands
            switch (tokens[0]) {
                case "place":
                    String[] tokens2 = tokens[1].split(";");
                    // The if cases represent the possible input errors
                    if (tokens2.length != 4) {
                        System.out.println("Error, the place command requires four arguments");
                        break;
                    }
                        try {
                            if (Integer.parseInt(tokens2[1]) < 10 && Integer.parseInt(tokens2[1]) >= 0 &&
                                    Integer.parseInt(tokens2[2]) < 10 && Integer.parseInt(tokens2[2]) >= 0) {

                                scrabble.place(tokens2[0], Integer.parseInt(tokens2[1]), Integer.parseInt(tokens2[2]),
                                        tokens2[0].length(), tokens2[3].charAt(0));
                                break;
                            }
                            else {
                                System.out.println("Error, the game board is a 10x10 square matrix (i,j = [0-9])");
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Error, row and column must be Integers");
                            break;
                        }
                        // maybe next two case's common part wrapped in a static void
                case "score":
                    try {
                        if (tokens[1].equals("P1")) {
                            System.out.println(playerOne.getScore());
                        } else if (tokens[1].equals("P2")) {
                            System.out.println(playerTwo.getScore());
                        } else System.out.println("Error, invalid format");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Error, you need to specify a player");
                    }
                    break;
                case "bag":
                    try {
                        if (tokens[1].equals("P1")) {
                            System.out.println(playerOne.getMoves().toString());
                        } else if (tokens[1].equals("P2")) {
                            System.out.println(playerTwo.getMoves().toString());
                        } else System.out.println("Error, invalid format");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Error, you need to specify a player");
                    }
                    break;
                case "print":
                    if (tokens.length != 1) {
                        System.out.println("Error, the print command does not require arguments");
                        break;
                    }
                    for (int i = 0; i < scrabble.getGameBoard().length; i++) {
                        System.out.println(scrabble.getGameBoard()[i]);
                    }
                    break;
                case "quit":
                    if (tokens.length != 1) {
                        System.out.println("Error, the quit command does not require arguments");
                        break;
                    }
                    System.out.println(playerOne.getScore());
                    System.out.println(playerTwo.getScore());
                    return;
                default:
                    System.out.println("Error, wrong command");
            }
        }
    }
}