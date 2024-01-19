import java.util.ArrayList;
import java.util.List;

/**
 * @author usoia
 * This class takes the list of states of the locks and creates a sequence which locks or unlocks the initialized setup
 */
public class LockingSystem {
    private final List<String> position;
    private final List<Integer> state;

    // the two states of the locks
    private final String opened = "auf";
    private final String closed = "zu";

    /**
     * Constructor
     *
     * @param position initial sequence of locks
     */
    public LockingSystem(List<String> position) {
        this.position = position;
        this.state = new ArrayList<>();
    }

    /**
     * This method checks if the fourth condition is applicable and calls the method which completes the sequence
     *
     * @return state the needed sequence to unlock the door
     */
    public List<Integer> open() {
        int counter = position.size() - 1;

        for (String s : position) {
            if (s.equals(opened)) {
                unlock(counter);
                return state;
            }
        }
        changePosition(counter);

        counter--;
        unlock(counter);
        return state;
    }

    /**
     * This method checks if the fourth condition is applicable calls the method which completes the sequence
     * Also checks for locks that are left opened after the last 2 locks have been closed and closes them
     *
     * @return state the needed sequence to lock the door
     */
    public List<Integer> close() {
        int counter4 = position.size() - 1;
        lock();

        int counter = position.size() - 4;

        // checks for locks that are left opened and should be closed after the main pattern has been completed
        // also allows the use of checkConditions and changePosition methods in both open() and close() cases
        while (counter >= 0) {
            if (position.get(counter).equals(opened))
                checkConditions(counter);
            counter--;
        }

        // last condition
        // (it requires less moves if we use this condition, although it was not used in the example)
        if (position.get(counter4).equals(opened)) {
            changePosition(counter4);
        }
        return state;
    }

    /**
     * This method represents the pattern of recursion, which is required to open the door
     *
     * @param counter the number of the last lock that needs to be opened
     */
    private void unlock(int counter) {

        // in case the first element is reached early (so the program works for N <= 2 as well)
        if (counter == 0) {
            changePosition(counter);
            return;
        }

        // searches for the first closed lock
        while (counter >= 0) {
            if (position.get(counter).equals(closed)) {
                // checks the third condition
                if (position.get(counter - 1).equals(opened)) {
                    conditionsHelp(counter);
                } else {
                    int counter2 = counter - 1;

                    while (counter2 >= 0) {
                        checkConditions(counter2);
                        counter2--;
                    }

                    changePosition(counter);
                }

                int counter3 = counter - 2;
                while (counter3 >= 0) {
                    checkConditions(counter3);
                    counter3 -= 2;
                }

                //stops the process when the first locked one is found and the pattern is completed
                break;
            }
            counter--;
        }
    }

    /**
     * This method contains a part of the conditions, which are used in a unlock(), lock() and checkConditions()
     * Using this method more stack memory is used, but it is needed as the checkstyle requirements allow no repeating
     * of the same code in 2 places
     *
     * @param counter the order number of the lock that needs to be closed/opened
     */
    private void conditionsHelp(int counter) {
        int counter4 = counter - 2;
        while (counter4 >= 0) {
            if (position.get(counter4).equals(opened)) {
                checkConditions(counter4);
            }
            counter4--;
        }
        changePosition(counter);
    }

    /**
     * Represents the main conditions of the recursion
     *
     * @param counter the order number of the lock that needs to be closed/opened
     */
    private void checkConditions(int counter) {
        // for the case when the first element is reached (first condition)
        if (counter == 0) {
            changePosition(counter);
            return;
        }
        // checks the third condition
        if (position.get(counter - 1).equals(opened)) {
            conditionsHelp(counter);
        } else {
            checkConditions(counter - 1);
            checkConditions(counter);
        }
    }

    /**
     * Changes the position of a lock to the opposite state (zu -> auf; auf -> zu)
     *
     * @param counter the order number of the lock in the position list
     */
    private void changePosition(int counter) {
        if (position.get(counter).equals(opened)) {

            //sets the last element to auf if it was auf
            position.set(counter, closed);
        } else {

            //sets the last element to auf if it was zu
            position.set(counter, opened);
        }

        //adds the number of the moved lock to the to be printed list
        state.add(counter + 1);
    }

    /**
     * This method represents the pattern of recursion, which is required to open the door
     */
    private void lock() {
        int counter = position.size() - 2;

        // checkstyle will probably indicate this as a copy-paste violation (it is present in another part of the code),
        // but the purpose of it is to stop the other part of this method from initializing (described at unlock())
        if (counter == 0) {
            changePosition(counter);
            return;
        }

        // searches for the first opened lock
        while (counter >= 0) {
            if (position.get(counter).equals(opened)) {
                // checks the third condition
                if (position.get(counter - 1).equals(opened)) {
                    conditionsHelp(counter);
                } else {
                    int counter2 = counter - 1;
                    checkConditions(counter2);

                    // changes the next to last element that needs to be changed (for the last see line 60)
                    checkConditions(counter);
                }
                int counter2 = 0;

                // second part of the required pattern of recursion
                while (counter2 < counter) {
                    checkConditions(counter2);
                    counter2++;
                }

                // stops the process when the first unlocked one is found and the pattern is completed
                break;
            }
            counter--;
        }
    }
}
