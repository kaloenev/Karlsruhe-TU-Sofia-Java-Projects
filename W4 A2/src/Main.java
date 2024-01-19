import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author usoia
 * This class does the input and output formatting of the program
 */
public class Main {

    /**
     * Prints out the sequence of moved locks with an R in front of each number and divided by commas
     *
     * @param print a List of integers representing the moved locks
     */
    private static void print(List<Integer> print) {
        for (int i = 0; i < print.size(); i++) {
            // checks when the last element is reached, so no comma is printed behind it
            if (i == print.size() - 1) {
                System.out.println("R" + print.get(i));
                break;
            }
            System.out.print("R" + print.get(i) + ", ");
        }
    }

    private static List<String> checkPosition(List<String> position) {
        for (String lock : position) {
            // checks if all the locks are given correct states (auf, zu)
            if (!lock.equals("zu") && !lock.equals("auf")) {
                System.out.println("Error, the state of a given lock can be either 'zu' or 'auf'");
                // ensures the program does not take a wrong sequence of locks
                position = Collections.singletonList("a");
                break;
            }
        }
        return position;
    }

    public static void main(String[] args) throws IOException {
        // saves the initial sequence of locks
        List<String> position;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // ensures there is an initialization
        while (true) {
            // takes the first input using the console
            String line = reader.readLine();

            // splits the String
            String[] tokens = line.split(" ");

            if (tokens[0].equals("init")) {
                position = Arrays.asList(tokens[1].split(","));
                position = checkPosition(position);
                break;
            } else if (tokens[0].equals("quit")) return;
            else System.out.println("Error, wrong command or you have not initialized a new set of locks yet");
        }

        // Ensures the commands are accepted until the quit command is called
        while (true) {
            String line = reader.readLine();

            // splits the new String
            String[] tokens = line.split(" ");

            // possible commands
            switch (tokens[0]) {
                case "init":
                    position = Arrays.asList(tokens[1].split(","));
                    position = checkPosition(position);
                    break;
                case "open":
                    if (position.get(0).equals("a")) {
                        System.out.println("Error, please initialize a new set of locks");
                        break;
                    }
                    // saves the required sequence to unlock the door and prints it
                    List<Integer> printUnlocked = new LockingSystem(position).open();
                    print(printUnlocked);
                    break;
                case "close":
                    if (position.get(0).equals("a")) {
                        System.out.println("Error, please initialize a new set of locks");
                        break;
                    }
                    // saves the required sequence to lock the door and prints it
                    List<Integer> printLocked = new LockingSystem(position).close();
                    print(printLocked);
                    break;
                case "quit":
                    return;
                default:
                    System.out.println("Error, wrong command");
            }
        }
    }
}
