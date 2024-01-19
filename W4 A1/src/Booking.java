/**
 * @author usoia
 * This class represents the bookings
 */
public class Booking {
    private final int passengerId;
    private final String passengerName;
    private final int flightId;
    static int billId = 0;

    /**
     * Constructor
     *
     * @param passengerId id of the passenger who booked a flight
     * @param name        names of the passenger
     * @param flightId    id of the booked flight
     */
    public Booking(int passengerId, String name, int flightId) {
        this.passengerId = passengerId;
        this.passengerName = name;
        this.flightId = flightId;
        billId++;
    }

    /**
     * Getter
     *
     * @return passengerId id of the passenger who booked a flight
     */
    public int getPassengerId() {
        return passengerId;
    }

    /**
     * Getter
     *
     * @return passengerName name of the passenger who booked a flight
     */
    public String getPassengerName() {
        return passengerName;
    }

    /**
     * Getter
     *
     * @return flightId id of the booked flight
     */
    public int getFlightId() {
        return flightId;
    }

    /**
     * Getter
     *
     * @return billId the number of the bill the passenger has to pay for the booking
     */
    public int getBillId() {
        return billId;
    }
}
