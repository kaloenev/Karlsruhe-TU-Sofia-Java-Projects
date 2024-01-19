package networkstructure;

import exceptions.ParseException;

import java.util.Objects;

/**
 * This class represents an ip, contains an ip in point notation as a String
 * @author usoia
 * @version 1.0
 */
public class IP implements Comparable<IP> {

    private final String ipV4;

    /**
     * Checks if the notation of the ip is correct
     * @param pointNotation ipv4 String representation
     * @throws ParseException if the pointNotation does not match the ipV4 structure
     */
    public IP(final String pointNotation) throws ParseException {
        int subStringStart = 0;

        if (!Character.isDigit(pointNotation.charAt(0))) {
            throw new ParseException("Error, an ip must start with a number");
        }

        for (int i = 0; i < pointNotation.length(); i++) {
            if (i == pointNotation.length() - 1 || pointNotation.charAt(i) == '.') {
                int ipPartNumber;
                if (i == pointNotation.length() - 1) {
                    ipPartNumber = Integer.parseInt(pointNotation.substring(subStringStart, i + 1));
                } else {
                    ipPartNumber = Integer.parseInt(pointNotation.substring(subStringStart, i));
                }
                // checks if the number before the decimal point is between 0 and 256
                if (ipPartNumber > 256) {
                    throw new ParseException("Error, ip numbers must be between 0 and 256");
                }
                // a zero is a valid ip part before a decimal point
                else if (ipPartNumber == 0) {
                    subStringStart = i + 1;
                    continue;
                }
                // leading zeros check
                else if (pointNotation.charAt(subStringStart) == '0') {
                    throw new ParseException("Error, the decimal format does not allow leading zeros");
                }
                // saves where the number after the decimal point starts in order to be checked later
                subStringStart = i + 1;
            } else if (!Character.isDigit(pointNotation.charAt(i))) {
                throw new ParseException("Error, ip must be in decimal format");
            }
        }
        this.ipV4 = pointNotation;
    }

    /**
     * Returns a string representation of the ip
     * @return point notation of the ip
     */
    @Override
    public String toString() {
        return ipV4;
    }

    /**
     * Compares the current ip with the given one, character by character
     * @param o the given ip
     * @return -1 if the current ip is smaller, 0 if they are equal, 1 if the current ip is bigger (bitwise)
     */
    @Override
    public int compareTo(IP o) {
        String ipToCompare = o.ipV4;
        int compareValue = 0;
        int compareValue2 = 0;
        int countIterations = Integer.MAX_VALUE;
        int countIterations2 = Integer.MAX_VALUE;

        for (int i = 0; i < ipToCompare.length() && i < ipV4.length(); i++) {
            if (ipV4.charAt(i) == '.') {
                if (ipToCompare.charAt(i) != '.') {
                    compareValue = -1;
                    return compareValue;
                }
                if (countIterations < 2  && countIterations < countIterations2) {
                    return compareValue;
                }
                else if (countIterations2 < 2 && countIterations2 < countIterations) {
                    return compareValue2;
                }
            } else if (ipToCompare.charAt(i) == '.') {
                if (ipV4.charAt(i) != '.') {
                    compareValue = 1;
                    return compareValue;
                }
                if (countIterations < 2) {
                    return compareValue;
                }
                else if (countIterations2 < 2) {
                    return compareValue2;
                }
            }

            if (Character.getNumericValue(ipV4.charAt(i)) < Character.getNumericValue(ipToCompare.charAt(i))
                    && countIterations > 2) {
                compareValue = -1;
                countIterations = 2;
            } else if (Character.getNumericValue(ipV4.charAt(i)) > Character.getNumericValue(ipToCompare.charAt(i))
                    && countIterations2 > 2) {
                compareValue2 = 1;
                countIterations2 = 2;
            }

            if (countIterations == 0) {
                return compareValue;
            }
            else if (countIterations2 == 0) {
                return compareValue2;
            }

            if (i == ipV4.length() - 1 || i == ipToCompare.length() - 1) {
                if (countIterations < 2) {
                    return compareValue;
                }
                else if (countIterations2 < 2) {
                    return compareValue2;
                }
            }

            countIterations--;
            countIterations2--;
        }
        if (countIterations <= countIterations2) {
            return compareValue;
        }
        else {
            return  compareValue2;
        }
    }

    /**
     * Checks if the current ip object is equal to the given ip object
     * @param o the given (potential) ip
     * @return true if they are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IP)) return false;
        IP ip = (IP) o;
        return Objects.equals(ipV4, ip.ipV4);
    }
}
