import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author usoia
 * This class does input and output formatting
 */
public class Main {

    /**
     * Main method
     *
     * @param args commandline arguments
     */
    public static void main(String[] args) throws IOException {

        DataManaging data = new DataManaging();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //Ensures the commands are accepted until the quit command is called
        while (true) {
            String line = reader.readLine();

            //Splits the String
            String[] tokens = line.split(" ");

            // Possible commands
            switch (tokens[0]) {
                case "add":
                    String[] tokens2 = tokens[1].split(";");
                    data.addFlight(tokens2);
                    break;
                case "remove":
                    data.removeFlight(tokens[1]);
                    break;
                case "list-route":
                    data.listFlights();
                    break;
                case "book":
                    String[] tokens3 = tokens[1].split(";");
                    data.bookFlight(tokens3);
                    break;
                case "list-bookings":
                    data.listBookings();
                    break;
                case "quit":
                    return;
                default:
                    System.out.println("Error, wrong command");
            }
        }
    }
}
