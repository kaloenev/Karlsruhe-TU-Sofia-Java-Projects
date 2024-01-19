package networkstructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * This class represents a tree topology structure with all of it's required functions
 *
 * @author usoia
 * @version 1.0
 */
public class TreeTopology {
    private List<Node> nodes;
    private int size;
    private List<List<Node>> edgeList;

    /**
     * Standard constructor (initializes the attributes)
     */
    public TreeTopology() {
        this.nodes = new ArrayList<>();
        this.size = 0;
        this.edgeList = new LinkedList<>();
    }

    /**
     * Creates a new tree topology from a part of an existing one
     * (splits the given tree topology in two separate ones at the given index)
     *
     * @param topology the tree topology to be separated
     * @param index    index, from which the new NetworkStructure.TreeTopology starts taking elements from @topology
     */
    public TreeTopology(TreeTopology topology, int index) {
        TreeTopology smallerTopology = new TreeTopology();
        Node node = topology.nodes.get(index);
        Queue<Node> nextNodes = new LinkedList<>(Collections.singletonList(node));
        Queue<Node> prevNodes = new LinkedList<>();
        Queue<Node> currentNodes = new LinkedList<>();
        int index2;
        do {
            for (Node node2 : nextNodes) {
                topology.edgeList.remove(topology.nodes.indexOf(node2));
            }
            topology.nodes.removeAll(nextNodes);
            int prevSize = nextNodes.size();
            int removeSize = prevNodes.size();
            for (int i = 0; i < prevSize; i++) {
                node = nextNodes.remove();
                index2 = topology.nodes.indexOf(node);
                nextNodes.addAll(topology.edgeList.get(index2));
                currentNodes.add(node);
                for (int j = 0; j < removeSize; j++) {
                    node = prevNodes.remove();
                    nextNodes.remove(node);
                    prevNodes.add(node);
                }
            }
            for (Node node1 : currentNodes) {
                for (Node node2 : nextNodes) {
                    smallerTopology.addConnection(node1.getIp(), node2.getIp());
                }
            }
            prevNodes.clear();
            prevNodes.addAll(currentNodes);
            currentNodes.clear();
        }
        while (!nextNodes.isEmpty());
    }

    /**
     * Searches for ip1 and ip2 in the topology, if they are not present, creates new Nodes with the given ips
     * Creates a connection between the Nodes (corresponding to the ips) (adds them to edgeList)
     *
     * @param ip1 parent ip
     * @param ip2 child ip
     */
    public void addConnection(IP ip1, IP ip2) {
        int indexIp1 = -1;
        int indexIp2 = -1;
        Node parent = null;
        Node child = null;
        // search for the ip nodes
        for (Node node : nodes) {
            if (node.getIp().compareTo(ip1) == 0) {
                parent = node;
                indexIp1 = nodes.indexOf(parent);
            }
            if (node.getIp().compareTo(ip2) == 0) {
                child = node;
                indexIp2 = nodes.indexOf(child);
            }
        }
        if (parent == null) {
            parent = new Node(ip1);
            indexIp1 = size;
            nodes.add(indexIp1, parent);
            edgeList.add(new LinkedList<>());
            size++;
        }
        if (child == null) {
            child = new Node(ip2);
            indexIp2 = size;
            nodes.add(indexIp2, child);
            edgeList.add(new LinkedList<>());
            size++;
        }
        edgeList.get(indexIp1).add(child);
        edgeList.get(indexIp2).add(parent);
    }

    /**
     * Checks if the current topology has any matching nodes (ip-s) with the given treeTopology, if so, it merges them
     *
     * @param treeTopology the topology, which is checked for duplicates
     * @return true if it has duplicate nodes and the trees have been successfully merged
     */
    public boolean hasDuplicateNodes(TreeTopology treeTopology) {
        // copy the nodes and edges of the current topology, so they can be reverted,
        // if connecting it to the treeTopology violates one of the topology rules
        List<Node> nodes1 = new LinkedList<>(nodes);
        List<List<Node>> edges1 = new LinkedList<>(edgeList);
        int duplicateIndex = -1;
        for (Node node : nodes) {
            duplicateIndex = treeTopology.getIndex(node.getIp());
            if (duplicateIndex != -1) {
                break;
            }
        }
        if (duplicateIndex == -1) {
            return false;
        }
        Node node = treeTopology.nodes.get(duplicateIndex);
        Queue<Node> nextNodes = new LinkedList<>(Collections.singletonList(node));
        Queue<Node> prevNodes = new LinkedList<>();
        Queue<Node> currentNodes = new LinkedList<>();
        // loop until all tree leaves have been traversed
        do {
            // save how many nodes there are in the lists as they are edited
            int prevSize = nextNodes.size();
            int removeSize = prevNodes.size();
            for (int i = 0; i < prevSize; i++) {
                node = nextNodes.remove();
                currentNodes.add(node);
                duplicateIndex = treeTopology.nodes.indexOf(node);
                if (checkRuleViolations(treeTopology, duplicateIndex, node)) {
                    this.nodes = nodes1;
                    this.edgeList = edges1;
                    return false;
                }
                // access the next children nodes of the current node
                nextNodes.addAll(treeTopology.edgeList.get(duplicateIndex));
                // remove the parent node from the list of children's children
                for (int j = 0; j < removeSize; j++) {
                    node = prevNodes.remove();
                    nextNodes.remove(node);
                    prevNodes.add(node);
                }
            }
            // add the connections between parents and children
            for (Node node1 : currentNodes) {
                for (Node node2 : nextNodes) {
                    addConnection(node1.getIp(), node2.getIp());
                }
            }
            prevNodes.clear();
            prevNodes.addAll(currentNodes);
            currentNodes.clear();
        }
        while (!nextNodes.isEmpty());
        return true;
    }

    /**
     * Checks for violations of the tree topology properties
     *
     * @param treeTopology   topology, from which the nodes are being added to the current one
     * @param duplicateIndex index of the first found duplicate element
     * @param node           the currentNode that is being added
     * @return true if a violation is found
     */
    private boolean checkRuleViolations(TreeTopology treeTopology, int duplicateIndex, Node node) {
        int index2;
        List<Node> temp;
        int counter3;
        for (Node node2 : nodes) {
            if (node.compareTo(node2) == 0) {
                for (Node node3 : treeTopology.edgeList.get(duplicateIndex)) {
                    index2 = 0;
                    outer:
                    for (Node node4 : nodes) {
                        if (node3.compareTo(node4) == 0) {
                            counter3 = 1;
                            temp = edgeList.get(index2);
                            for (Node node5 : temp) {
                                if (node2.compareTo(node5) == 0) {
                                    break outer;
                                }
                                if (counter3 == temp.size()) {
                                    return true;
                                }
                                counter3++;
                            }
                        }
                        index2++;
                    }
                }
            } else {
                counter3 = 0;
                for (Node node3 : treeTopology.edgeList.get(duplicateIndex)) {
                    for (Node node4 : nodes) {
                        if (node3.compareTo(node4) == 0) {
                            counter3++;
                        }
                        if (counter3 > 1) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Gets the ip that are represented by the given nodes in this topology
     *
     * @return a list of the ip-s bounded to the nodes
     */
    public List<IP> getUnsortedNodes() {
        List<IP> unsortedIp = new LinkedList<>();
        for (Node node : nodes) {
            unsortedIp.add(node.getIp());
        }
        return unsortedIp;
    }

    /**
     * Checks if the ip is represented by any node in this topology
     *
     * @param ip that is searched for in nodes
     * @return true if the ip was found
     */
    public boolean contains(IP ip) {
        for (Node node : nodes) {
            if (node.getIp().compareTo(ip) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the current tree topology has the same structure as the given one
     *
     * @param topology the topology, against which the structure is being checked
     * @return true if they have the same structure
     */
    public boolean hasSameStructure(TreeTopology topology) {
        for (Node node : nodes) {
            boolean foundNode = false;
            // find the node with the same ip
            for (Node node2 : topology.nodes) {
                if (node.compareTo(node2) == 0) {
                    // save the indexes (they are the same in edgeList and nodes list)
                    int index = nodes.indexOf(node);
                    int index2 = topology.nodes.indexOf(node2);
                    // if the size is different -> there are more edges in the tree -> not structurally equal
                    if (edgeList.get(index).size() != topology.edgeList.get(index2).size()) {
                        return false;
                    }
                    // can not use if (!topology.edgeList.get(index2).containsAll(edgeList.get(index)))
                    // as the nodes must be compared by ip!
                    for (Node node3 : edgeList.get(index)) {
                        boolean foundNode3 = false;
                        // find the edge in the list of edges the current node is connected to (based on index)
                        for (Node node4 : topology.edgeList.get(index2)) {
                            if (node3.compareTo(node4) == 0) {
                                foundNode3 = true;
                                break;
                            }
                        }
                        if (!foundNode3) {
                            return false;
                        }
                    }
                    foundNode = true;
                    break;
                }
            }
            if (!foundNode) {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes an edge between two nodes from the current topology,
     * if possible and needed creates a new topology with the removed elements, so they are not lost
     *
     * @param ip1 ip represented by the first node
     * @param ip2 ip represented by the second node
     * @return -1 if any of the ip-s was not found in the current tree; -2 if removing not possible;
     * 0 if successful and no need for a new tree; the index of the node in the current topology,
     * from which the tree has to be separated
     */
    public int removeEdge(IP ip1, IP ip2) {
        int counter = 0;
        int index1 = -1;
        int index2 = -1;
        for (Node node : nodes) {
            if (node.getIp().compareTo(ip1) == 0) {
                index1 = counter;
            }
            if (node.getIp().compareTo(ip2) == 0) {
                index2 = counter;
            }
            counter++;
        }
        if (index1 != -1 && index2 != -1) {
            // check if the first node is a leaf
            if (edgeList.get(index1).size() == 1) {
                if (edgeList.get(index2).size() == 1) {
                    return -2;
                }
                edgeList.get(index2).removeIf(node -> node.getIp().compareTo(ip1) == 0);
                nodes.remove(index1);
                edgeList.remove(index1);
                return 0;
            }
            // check if the other node is a leaf
            else if (edgeList.get(index2).size() == 1) {
                edgeList.get(index1).removeIf(node -> node.getIp().compareTo(ip2) == 0);
                nodes.remove(index2);
                edgeList.remove(index2);
                return 0;
            }
            // if this case is reached then none of the two nodes on this edge are leaves (have grad 1)
            else {
                edgeList.get(index1).removeIf(node -> node.getIp().compareTo(ip2) == 0);
                edgeList.get(index2).removeIf(node -> node.getIp().compareTo(ip1) == 0);
                if (edgeList.get(index1).size() <= edgeList.get(index2).size()) {
                    return index1;
                } else {
                    return index2;
                }
            }
        }
        return -1;
    }

    /**
     * Gets the height of the current tree starting from the given ip
     *
     * @param root the given ip (starting point)
     * @return height of the tree
     */
    public int getHeight(IP root) {
        int index = getIndex(root);

        if (index == -1) {
            return index;
        }
        List<Node> nodes1 = new LinkedList<>(nodes);
        List<List<Node>> edges = new LinkedList<>(edgeList);
        return Topology.getHeight(index, nodes1, edges);
    }

    /**
     * Returns the leveling structure of the current tree starting from the given ip with the levels sorted by ip
     *
     * @param root starting point (ip)
     * @return a list which contains lists of the levels (those contain sorted nodes)
     */
    public List<List<IP>> getLevels(IP root) {
        List<List<IP>> levels = new LinkedList<>();
        int index = getIndex(root);
        if (index == -1) {
            return levels;
        }
        List<Node> nodes1 = new LinkedList<>(nodes);
        List<List<Node>> edges = new LinkedList<>(edgeList);
        levels = Topology.getLevels(index, nodes1, edges);
        return levels;
    }

    /**
     * Gives the shortest path/route from the given ip start to the given ip end
     *
     * @param start starting ip
     * @param end   end ip
     * @return a list of the traversed edges (the route)
     */
    public List<IP> getRoute(IP start, IP end) {
        List<IP> shortestPath;
        List<Node> nodes1 = new LinkedList<>(nodes);
        List<List<Node>> edges = new LinkedList<>(edgeList);
        shortestPath = Topology.getRoute(end, nodes1, edges, start);
        return shortestPath;
    }

    /**
     * Gives a bracket representation of the ip-s that are in this tree
     *
     * @param root the starting ip
     * @return ip-s of the present nodes in bracket notation
     */
    public String toString(IP root) {
        String topology = "";
        int index = getIndex(root);
        if (index == -1) {
            return topology;
        }
        Node node = nodes.get(index);
        List<Node> nextNodes = new LinkedList<>(Collections.singletonList(node));
        Queue<Node> prevNodes = new LinkedList<>(Collections.singletonList(node));
        StringBuilder bracketNotation = new StringBuilder("(");
        toStringHelp(nextNodes, prevNodes, bracketNotation, false, true);
        bracketNotation.append(")");
        topology = bracketNotation.toString();
        return topology;
    }

    /**
     * Recursive function that explores the edges going to the lower levels first, as the bracket notation requires it
     *
     * @param currentLevel    the nodes from the current level of iteration
     * @param prevNodes       the nodes which are parents and are repeated when accessing the
     * @param bracketNotation the bracket representation of the sorted ip at the levels
     * @param counter         checks if this is the first level of recursion (the root node - a special case)
     * @param parentLevelsEnd true if all levels above are at their last index at the list of nodes,
     *                        so that the correct number of closing brackets are added
     */
    public void toStringHelp(List<Node> currentLevel, Queue<Node> prevNodes, StringBuilder bracketNotation,
                             boolean counter, boolean parentLevelsEnd) {
        List<Node> nextNodes = new LinkedList<>();
        Queue<Node> currentNodes = new LinkedList<>();
        boolean openedBracket = false;
        boolean levelEnd = false;
        int currentSize = currentLevel.size();
        Node node1;
        for (Node node : currentLevel) {
            nextNodes.addAll(edgeList.get(nodes.indexOf(node)));
            node1 = prevNodes.remove();
            currentNodes.add(node1);
            nextNodes.remove(node1);
            prevNodes.add(node);
            nextNodes.sort(Node::compareTo);
            if (!nextNodes.isEmpty() && counter) {
                bracketNotation.append("(");
                openedBracket = true;
            }
            if (currentLevel.get(currentSize - 1).equals(node)) {
                levelEnd = true;
            }

            if (currentLevel.get(currentSize - 1).equals(node) && nextNodes.isEmpty() && counter) {
                bracketNotation.append(node.getIp().toString());
            } else {
                bracketNotation.append(node.getIp().toString()).append(" ");
            }

            if (!nextNodes.isEmpty()) {
                toStringHelp(nextNodes, prevNodes, bracketNotation, true, levelEnd);
            }
            prevNodes.remove();
            prevNodes.add(currentNodes.remove());
            if (openedBracket) {
                bracketNotation.append(")");
                if (parentLevelsEnd && !levelEnd) {
                    bracketNotation.append(" ");
                }
            }
            nextNodes.clear();
        }
    }

    /**
     * Returns the index of the given ip in this topology
     *
     * @param root ip, whose index is searched
     * @return -1 if ip not found in the tree, the index in nodes otherwise
     */
    private int getIndex(IP root) {
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