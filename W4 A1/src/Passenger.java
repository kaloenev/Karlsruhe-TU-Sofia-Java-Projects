/**
 * @author usoia
 * This class represents the passengers
 */
public class Passenger {
    private static int passengerId = 0;

    /**
     * Constructor
     */
    public Passenger() {
        passengerId++;
    }

    /**
     * Getter
     *
     * @return passengerId
     */
    public int getPassengerId() {
        return passengerId;
    }
}
