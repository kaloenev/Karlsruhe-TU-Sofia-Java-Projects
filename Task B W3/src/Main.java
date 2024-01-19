import java.io.BufferedReader;
import java.io.Console;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        //Reads the given file
        BufferedReader reader1 = new BufferedReader(new FileReader(args[0]));
        String[] gameBoard = reader1.lines().toArray(String[]::new);

        Moves moves = new Moves();

        //Creates a console reader
        Console reader2 = System.console();

        //Finds the initial position of the Ameise on the given board
        moves.findInitialPosition(gameBoard);

        //Ensures the commands are accepted until the quit command is called
        while (true) {
            String line = reader2.readLine();

            //Splits the string
            String[] tokens = line.split(" ");

            // Possible commands
            switch (tokens[0]) {
                case "move":
                    moves.move(gameBoard, Integer.parseInt(tokens[1]));
                    break;
                case "print":
                    //Prints out every line of the board
                    for (String s : gameBoard) {
                        System.out.println(s);
                    }
                    break;
                case "position":
                    System.out.println(moves.getPositionY() + "," + moves.getPositionX());
                    break;
                case "field":
                    //Splits the string into the two desired coordinates
                    String[] tokens2 = tokens[1].split(",");
                    System.out.println(gameBoard[Integer.parseInt(tokens2[0])].charAt(Integer.parseInt(tokens2[1])));
                    break;
                case "quit":
                    return;
            }
        }
    }
}
