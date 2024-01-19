import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author usoia
 * This class manages the data
 */
public class DataManaging {
    private final ArrayList<Flight> flights;
    private final ArrayList<Booking> bookings;

    /**
     * Constructor
     */
    public DataManaging() {
        this.flights = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    /**
     * This method adds a new flight to the list
     *
     * @param tokens2 contains the needed data to create a new flight
     */
    public void addFlight(String[] tokens2) {
        // catches NumberFormatException as well as the one given from the valueOf method on enums
        try {
            try {
                // checks if the condition for the flight number is fulfilled
                if (Integer.parseInt(tokens2[0]) >= 1 && Integer.parseInt(tokens2[0]) <= 99999) {
                    // checks if the flight to be added already exists
                    for (Flight flight : flights) {
                        if (flight.getId() == Integer.parseInt(tokens2[0])) {
                            System.out.println("Error, a flight with this id already exists");
                            return;
                        }
                    }
                    // adds the newly created flight to the list of bookable flights
                    flights.add(new Flight(Integer.parseInt(tokens2[0]), tokens2[1], tokens2[2],
                            Double.parseDouble(tokens2[3]), Currency.valueOf(tokens2[4])));

                    System.out.println("OK");
                } else System.out.println("Error, invalid flight id (must be between 1 and 99999)");
            } catch (NumberFormatException e) {
                System.out.println("Error, flightId and price must be integers");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error, currency must be eur, gbp, usd or jpy");
        }
    }

    /**
     * This method removes the flight with the given id from the list
     *
     * @param tokens flight id
     */
    public void removeFlight(String tokens) {
        for (Flight flight : flights) {

            // checks the list of existing flights for the given from console flight number
            if (flight.getId() == Integer.parseInt(tokens)) {

                // removes the bookings that were made on this flight from the list of bookings
                bookings.removeIf(Booking -> flight.getBookings().contains(Booking));

                // removes the flight with the given number from the list of bookable flights
                flights.remove(flight);
                System.out.println("OK");
                return;
            }
        }
        // if no such flight is found prints an error
        System.out.println("Error, no flight with this id exists");
    }

    /**
     * This method prints out all the available flights sorted
     */
    public void listFlights() {
        // sorts the flights list by flight id
        flights.sort(Comparator.comparing(Flight::getId));

        // prints out the available flights sorted by flight id
        for (Flight flight : flights) {
            System.out.println(String.format("%05d", flight.getId()) + ";" + flight.getStart()
                    + ";" + flight.getTarget() + ";" + String.format("%.2f", flight.getPrice())
                    + ";" + flight.getCurrency());
        }
    }

    /**
     * This method books flight, gives back a bill for it and references it to the passenger
     *
     * @param tokens3 contains the two names of the passenger
     */
    public void bookFlight(String[] tokens3) {
        // saves the name of the passenger in a single String variable, so it is easier to check it
        String passengerName = tokens3[1] + " " + tokens3[2];

        for (Flight flight : flights) {
            // catches NumberFormatException on line 101 if the flight id format is wrong
            try {
                // checks if a flight with this number exists
                if (flight.getId() == Integer.parseInt(tokens3[0])) {
                    for (int j = 0; j < bookings.size(); j++) {

                        // checks if a Passenger with this name has booked another flight
                        if (bookings.get(j).getPassengerName().equals(passengerName)) {

                            // creates a new booking for the already existing Passenger
                            Booking booking = new Booking(bookings.get(j).getPassengerId(),
                                    bookings.get(j).getPassengerName(), Integer.parseInt(tokens3[0]));

                            // adds the newly created booking to the list of bookings
                            bookings.add(booking);

                            // adds the new booking to the list of bookings for the current flight
                            flight.addBooking(booking);

                            System.out.println(booking.getBillId() + ";" + booking.getPassengerId());
                            return;
                        }
                    }
                    // creates a new Passenger and gets their Id for their new booking
                    Passenger passenger = new Passenger();
                    Booking booking = new Booking(passenger.getPassengerId(), passengerName,
                            Integer.parseInt(tokens3[0]));

                    bookings.add(booking);

                    // adds the new booking to the list of bookings for the current flight
                    flight.addBooking(booking);

                    System.out.println(booking.getBillId() + ";" + booking.getPassengerId());
                    System.out.println(bookings.get(0).getBillId());
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error, invalid format of flight number");
                return;
            }
        }
        System.out.println("Error, flight number does not exist");
    }

    /**
     * This method prints out all bookings that have been made sorted by the passenger id
     */
    public void listBookings() {
        // sorts the bookings first by flight id then by passenger id (so passenger id has priority)
        bookings.sort(Comparator.comparing(Booking::getBillId));
        bookings.sort(Comparator.comparing(Booking::getPassengerId));

        // prints out the available bookings sorted by passenger id
        for (Booking booking : bookings) {

            System.out.println(booking.getPassengerId() + ";" + String.format("%05d", booking.getFlightId())
                    + ";" + booking.getBillId());

        }
    }
}
