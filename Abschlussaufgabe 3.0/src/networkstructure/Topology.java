package networkstructure;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public final class Topology {

    private Topology() {
    }

    /**
     * Gets the height of the current tree starting from the given ip
     *
     * @param index    the given ip (starting point)
     * @param nodes    tree topology nodes
     * @param edgeList tree topology edges
     * @return height of the tree
     */
    public static int getHeight(int index, List<Node> nodes, List<List<Node>> edgeList) {
        // start with height equal to 1, as the first node is alone in the first level
        int height = -1;
        Node node = nodes.get(index);
        Queue<Node> nextNodes = new LinkedList<>(Collections.singletonList(node));
        Queue<Node> prevNodes = new LinkedList<>();
        Queue<Node> currentNodes = new LinkedList<>();
        // loop until there are no new nodes unexplored
        do {
            int prevSize = nextNodes.size();
            int removeSize = prevNodes.size();
            for (int i = 0; i < prevSize; i++) {
                // remove the old nodes (the one with index i, though a queue is faster so no need for index)
                node = nextNodes.remove();
                // add the next nodes connected with edges
                nextNodes.addAll(edgeList.get(nodes.indexOf(node)));
                currentNodes.add(node);
                // remove the parent from the next nodes
                for (int j = 0; j < removeSize; j++) {
                    node = prevNodes.remove();
                    nextNodes.remove(node);
                    prevNodes.add(node);
                }
            }
            prevNodes.clear();
            prevNodes.addAll(currentNodes);
            currentNodes.clear();
            height++;
        }
        while (!nextNodes.isEmpty());
        return height;
    }

    /**
     * Returns the leveling structure of the current tree starting from the given ip with the levels sorted by ip
     *
     * @param index    starting point (ip)
     * @param nodes    tree topology nodes
     * @param edgeList tree topology edges
     * @return a list which contains lists of the levels (those contain sorted nodes)
     */
    public static List<List<IP>> getLevels(int index, List<Node> nodes, List<List<Node>> edgeList) {
        List<List<IP>> levels = new LinkedList<>();

        Node node = nodes.get(index);
        Queue<Node> nextNodes = new LinkedList<>(Collections.singletonList(node));
        Queue<Node> prevNodes = new LinkedList<>();
        Queue<Node> currentNodes = new LinkedList<>();
        // basically the same algorithm to getHeight, but instead of counting the height,
        // adding each level to a list and sorting it
        do {
            int prevSize = nextNodes.size();
            int removeSize = prevNodes.size();
            List<IP> currentLevel = new LinkedList<>();
            for (int i = 0; i < prevSize; i++) {
                node = nextNodes.remove();
                currentNodes.add(node);
                currentLevel.add(node.getIp());
                nextNodes.addAll(edgeList.get(nodes.indexOf(node)));
                for (int j = 0; j < removeSize; j++) {
                    node = prevNodes.remove();
                    nextNodes.remove(node);
                    prevNodes.add(node);
                }
            }
            prevNodes.clear();
            prevNodes.addAll(currentNodes);
            currentNodes.clear();
            currentLevel.sort(IP::compareTo);
            levels.add(Collections.unmodifiableList((currentLevel)));
        }
        while (!nextNodes.isEmpty());
        return levels;
    }

    /**
     * Gives the shortest path/route from the given ip start to the given ip end
     *
     * @param start    starting ip
     * @param end      end ip
     * @param nodes    tree topology nodes
     * @param edgeList tree topology edges
     * @return a list of the traversed edges (the route)
     */
    public static List<IP> getRoute(IP end, List<Node> nodes, List<List<Node>> edgeList, IP start) {
        List<IP> shortestPath = new LinkedList<>();
        int index = getIndex(start, nodes);

        if (index == -1 || !contains(end, nodes)) {
            return shortestPath;
        }
        boolean[] traversed = new boolean[nodes.size()];
        Stack<Integer> toVisit = new Stack<>();
        Stack<Integer> counted = new Stack<>();
        int index2;
        int counter = 0;
        int size;
        Node node;
        toVisit.add(index);
        traversed[index] = true;
        // loop through the graph until the node is found
        do {
            index = toVisit.pop();
            node = nodes.get(index);
            shortestPath.add(node.getIp());
            size = edgeList.get(index).size();
            if (size != 1 || counter == 0) {
                for (Node node1 : edgeList.get(index)) {
                    index2 = nodes.indexOf(node1);
                    if (!traversed[index2]) {
                        toVisit.add(index2);
                        traversed[index2] = true;
                    }
                }
                if (size > 2 || (counter == 0 && size >= 2)) {
                    counted.add(counter);
                }
                counter++;
            } else {
                index2 = shortestPath.size();
                while (counter != counted.pop()) {
                    index2--;
                    shortestPath.remove(index2);
                    counter--;
                }
            }
        } while (node.getIp().compareTo(end) != 0);
        return shortestPath;
    }

    /**
     * Checks if the ip is represented by any node in this topology
     *
     * @param ip that is searched for in nodes
     * @return true if the ip was found
     */
    public static boolean contains(IP ip, List<Node> nodes) {
        for (Node node : nodes) {
            if (node.getIp().compareTo(ip) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the index of the given ip in this topology
     *
     * @param root ip, whose index is searched
     * @return -1 if ip not found in the tree, the index in nodes otherwise
     */
    public static int getIndex(IP root, List<Node> nodes) {
        int index = -1;
        int counter1 = 0;
        for (Node node : nodes) {
            if (node.getIp().compareTo(root) == 0) {
                index = counter1;
                break;
            }
            counter1++;
        }
        return index;
    }

}
