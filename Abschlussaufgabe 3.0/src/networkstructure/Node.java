package networkstructure;

/**
 * This class represents a node which contains an ip
 * @author usoia
 * @version 1.0
 */
public class Node implements Comparable<Node> {
    private final IP ip;

    /**
     * Creates a node representing the given ip
     * @param ip the given ip
     */
    public Node(IP ip) {
        this.ip = ip;
    }

    /**
     * Getter
     * @return the ip object
     */
    public IP getIp() {
        return ip;
    }

    /**
     * Overrides the compare method of ip, so nodes can be compared based on them
     * @param node the node to compare the current node to by ip
     * @return -1 if the current ip is smaller, 0 if they are equal, 1 if the current ip is bigger (bitwise)
     */
    @Override
    public int compareTo(Node node) {
        return this.ip.compareTo(node.getIp());
    }
}
