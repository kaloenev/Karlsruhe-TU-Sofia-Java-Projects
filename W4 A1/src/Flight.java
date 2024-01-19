import java.util.ArrayList;

/**
 * @author usoia
 * This class represents the flights
 */
public class Flight {
    private final String start;
    private final String target;
    private final int id;
    private final double price;
    private final Currency currency;
    private final ArrayList<Booking> bookings;

    /**
     * Constructor
     *
     * @param id       flight id
     * @param start    departure country
     * @param target   destination country
     * @param price    price of a booking
     * @param currency currency of the price
     */
    public Flight(int id, String start, String target, double price, Currency currency) {
        this.start = start;
        this.target = target;
        this.id = id;
        this.price = price;
        this.currency = currency;
        bookings = new ArrayList<>();
    }

    /**
     * Getter
     *
     * @return start departure country
     */
    public String getStart() {
        return start;
    }

    /**
     * Getter
     *
     * @return target destination country
     */
    public String getTarget() {
        return target;
    }

    /**
     * Getter
     *
     * @return id flight id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter
     *
     * @return price price of a booking
     */
    public double getPrice() {
        return price;
    }

    /**
     * Getter
     *
     * @return currency currency of the price
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * Getter
     *
     * @return bookings list of bookings for this flight
     */
    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    /**
     * Adds a booking to the list of bookings for this flight
     */
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }
}
