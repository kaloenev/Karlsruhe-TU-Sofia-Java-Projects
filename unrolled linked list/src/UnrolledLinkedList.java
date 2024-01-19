/**
 * Class representing the data structure unrolled linked list
 *
 * @author usoia
 * @version 1.0
 */
public class UnrolledLinkedList {
    /**
     * Inner class representing the cell of the linked list
     */
    private class ListCell {
        int[] array;
        int intentionalZeros;
        ListCell prev;
        ListCell next;

        /**
         * Constructor
         *
         * @param p previous cell
         * @param n next cell
         */
        ListCell(ListCell p, ListCell n) {
            this.array = new int[arraySize];
            this.intentionalZeros = 0;
            this.prev = p;
            this.next = n;
        }
    }

    private int countCells;
    private final int arraySize;
    private ListCell first;
    private ListCell last;

    /**
     * Constructor
     *
     * @param arraySize size of the array in every cell
     */
    public UnrolledLinkedList(int arraySize) {
        this.countCells = 0;
        this.arraySize = arraySize;
        this.first = this.last = null;
    }

    /**
     * This method adds a new cell to save a new array, when it needs to be created
     */
    private void addLast() {
        ListCell newListCell = new ListCell(this.last, null);
        if (this.first == null) { // add to empty list
            this.first = newListCell;
        } else {
            this.last.next = newListCell;
        }
        this.last = newListCell;
        countCells++;
    }

    /**
     * adds an element to the end of the data structure
     *
     * @param element element to be added
     * @return true if new cell was added
     */
    public boolean push(int element) {
        // avoids null pointer exception
        if (last != null) {
            int countIntZeros = last.intentionalZeros;
            for (int i = 0; i < last.array.length; i++) {
                if (last.array[i] == 0 && countIntZeros != 0) {
                    countIntZeros--;
                } else if (last.array[i] == 0) {
                    last.array[i] = element;
                    if (element == 0) last.intentionalZeros++;
                    return false;
                }
            }
        }
        addLast();
        last.array[0] = element;
        return true;
    }

    /**
     * removes an element at the end of the data structure
     *
     * @return true if a cell at the end was removed
     */
    public boolean pop() {
        for (int i = last.array.length - 1; i >= 0; i--) {
            if (last.array[i] != 0) {
                last.array[i] = 0;
                break;
            } else if (last.intentionalZeros != 0) {
                last.intentionalZeros--;
                break;
            }
        }
        if (last.array[0] == 0 && last.intentionalZeros == 0) {
            return removeLast();
        }
        return false;
    }

    /**
     * removes the last cell if it is not needed
     *
     * @return true if a cell was removed
     */
    private boolean removeLast() {
        if (last.prev == null) {
            return false;
        }
        last = last.prev;
        last.next = null;
        return true;
    }

    /**
     * get an element at position i
     *
     * @param i position of the desired element
     * @return the element at position i
     */
    public int get(int i) {
        int cellNumber = i / arraySize;
        int indexInArray = i % arraySize;
        ListCell cell;

        if (size() / 2 < cellNumber + 1) {
            cell = first;
            for (int j = 1; j < cellNumber; j++) {
                cell = cell.next;
            }
            return cell.array[indexInArray];
        } else {
            cell = last;
            for (int j = 1; j < cellNumber - (size() / arraySize - cellNumber); j++) {
                cell = cell.prev;
            }
            return cell.array[arraySize - indexInArray - 1];
        }
    }

    /**
     * calculate the size of the data structure
     *
     * @return the size of the data structure
     */
    public int size() {
        int counter = countCells;
        ListCell cell = last;

        counter *= arraySize;
        int countIntZeros = cell.intentionalZeros;

        for (int i : cell.array) {
            if (i == 0 && countIntZeros != 0) {
                countIntZeros--;
            } else if (i == 0) return counter;
            counter++;
        }
        return counter;
    }

    /**
     * converts the data structure into String
     *
     * @param separator the element that is to separate the elements
     * @return elements of the data structure in a String
     */
    public String toString(String separator) {
        StringBuilder builder = new StringBuilder();
        ListCell cell = first;
        while (cell != null) {
            for (int i : cell.array) {
                if (i == cell.array.length - 1 && cell.next == null) builder.append(i);
                builder.append(i).append(separator);
            }
            cell = cell.next;
        }
        return builder.toString();
    }
}
