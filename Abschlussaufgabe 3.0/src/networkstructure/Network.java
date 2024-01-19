package networkstructure;

import exceptions.ParseException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * This class represents a network structure of tree topologies and executes functions on them
 * @author usoia
 * @version 1.0
 */
public class Network {

    private final List<TreeTopology> topologies;

    /**
     * Constructor that checks if valid ip-s are given creates a network out of them,
     * calling functions directly in the NetworkStructure.TreeTopology class
     *
     * @param root     the parent ip
     * @param children the children ip-s connected to the root
     */
    public Network(final IP root, final List<IP> children) {
        this.topologies = new ArrayList<>();
        topologies.add(new TreeTopology());

        if (root == null) {
            throw new RuntimeException("Error, root must be initialized");
        }
        for (IP ip : children) {
            if (ip == null) {
                throw new RuntimeException("Error, one of the children nodes is not initialized");
            } else if (ip == root) {
                throw new RuntimeException("Error, tree property not fulfilled (a root cannot also be a child");
            } else if (Collections.frequency(children, ip) != 1) {
                throw new RuntimeException("Error, tree property not fulfilled (root already connected to this child");
            }
            topologies.get(0).addConnection(root, ip);
        }
    }

    /**
     * Creates a new network of tree topologies from the given bracket notation
     *
     * @param bracketNotation a structure of ip-s in a String representation with brackets representing a new level
     * @throws ParseException if the structure of an ip or the bracket notation are violated
     */
    public Network(final String bracketNotation) throws ParseException {
        checkBracketNotation(bracketNotation);
        this.topologies = new ArrayList<>();
        topologies.add(new TreeTopology());
        Set<IP> ips = new HashSet<>();

        constructorHelper(bracketNotation, ips);
    }

    /**
     * Checks for violations in the bracket notation
     *
     * @param bracketNotation a structure of ip-s in a String representation with brackets representing a new level
     * @throws ParseException if the structure of an ip or the bracket notation are violated
     */
    private void checkBracketNotation(String bracketNotation) throws ParseException {
        if (bracketNotation == null || bracketNotation.isBlank() || bracketNotation.charAt(0) != '(') {
            throw new ParseException("Error, bracket notation format must start with a bracket");
        }
        int bracketCounter = 0;

        for (int i = 0; i < bracketNotation.length(); i++) {
            if (bracketNotation.charAt(i) == '(') {
                bracketCounter++;
            } else if (bracketNotation.charAt(i) == ')') {
                bracketCounter--;
            }
        }
        if (bracketCounter != 0) {
            throw new ParseException("Error, every open bracket must be closed for the notation");
        }
    }

    /**
     * The actual recursive function that creates the network adding the deeper level nodes first
     *
     * @param bracketNotation a structure of ip-s in a String representation with brackets representing a new level
     * @param ips             a set that contains all ip-s, every ip is added there and searched for, in order to avoid loops
     * @throws ParseException if the structure of an ip or the bracket notation are violated
     */
    private void constructorHelper(String bracketNotation, Set<IP> ips) throws ParseException {
        String[] ipDecimal = bracketNotation.split(" ");
        if (ipDecimal.length < 2) {
            throw new ParseException("Error, there must be at least one edge in the network");
        }
        IP parentIp = null;
        IP childIp;

        int j = 0;
        while (j < ipDecimal.length) {
            String ip = ipDecimal[j];
            if (ip.charAt(0) == '(') {
                // check if this is the first bracket (indicates start of this network) or the start of a subnetwork
                if (j == 0) {
                    // remove starting bracket
                    parentIp = (new IP(ip.substring(1)));
                    // check if the ip is linked to another ip so that they create a loop
                    for (IP ip1 : ips) {
                        if (ip1.compareTo(parentIp) == 0) {
                            throw new ParseException("Error, tree property violated");
                        }
                    }
                    ips.add(parentIp);
                } else {
                    boolean isLastIp = true;
                    StringBuilder ipBracketNotation = new StringBuilder();
                    for (int i = j; isLastIp; i++) {
                        if (ipDecimal[i].charAt(ipDecimal[i].length() - 1) == ')') {
                            ipBracketNotation.append(ipDecimal[i]);
                            isLastIp = false;
                        } else {
                            ipBracketNotation.append(ipDecimal[i]).append(" ");
                            j++;
                        }
                    }
                    childIp = new IP(ip.substring(1));
                    topologies.get(0).addConnection(parentIp, childIp);
                    constructorHelper(ipBracketNotation.toString(), ips);
                }
            } else {
                int i = 0;
                do {
                    i++;
                } while (ip.charAt(ip.length() - i) == ')');
                childIp = new IP(ip.substring(0, ip.length() - i + 1));

                // check if the ip is linked to another ip so that they create a loop
                for (IP ip1 : ips) {
                    if (ip1.compareTo(childIp) == 0) {
                        throw new ParseException("Error, tree property violated");
                    }
                }
                ips.add(childIp);
                // make a connection between parent and child ips
                topologies.get(0).addConnection(parentIp, childIp);
            }
            j++;
        }
    }

    /**
     * Merges a given network with the current one
     *
     * @param subnet the given network
     * @return true if the merge was successful, false if there was a violation of the tree rules
     */
    public boolean add(final Network subnet) {
        boolean hasDuplicates = false;
        int topologiesSize = topologies.size();
        for (int i = 0; i < topologiesSize; i++) {
            TreeTopology treeTopology = topologies.get(i);
            for (TreeTopology treeTopology1 : subnet.topologies) {
                if (treeTopology.hasDuplicateNodes(treeTopology1)) {
                    hasDuplicates = true;
                } else {
                    topologies.add(treeTopology1);
                }
            }
        }
        return hasDuplicates;
    }

    /**
     * Gets the nodes of each topology, sorts them and returns an unmodifiable list of them
     *
     * @return an unmodifiable list of the nodes in this network sorted by ip
     */
    public List<IP> list() {
        List<IP> sortedNodes = new LinkedList<>();
        for (TreeTopology topology : topologies) {
            for (IP ip : topology.getUnsortedNodes()) {
                boolean isNotContained = true;
                for (IP ip2 : sortedNodes) {
                    if (ip.compareTo(ip2) == 0) {
                        isNotContained = false;
                        break;
                    }
                }
                if (isNotContained) {
                    sortedNodes.add(ip);
                }
            }
        }
        sortedNodes.sort(IP::compareTo);
        return Collections.unmodifiableList(sortedNodes);
    }

    /**
     * Connects two nodes of two tree topologies
     *
     * @param ip1 the first ip
     * @param ip2 the second ip
     * @return true if successful, false if there was a violation
     */
    public boolean connect(final IP ip1, final IP ip2) {
        for (TreeTopology topology : topologies) {
            for (TreeTopology topology2 : topologies) {
                if (topology.contains(ip1) && topology2.contains(ip2)) {
                    topology.addConnection(ip1, ip2);
                    if (topology.hasDuplicateNodes(topology2)) {
                        return true;
                    } else {
                        topology.removeEdge(ip1, ip2);
                        break;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Disconnects two nodes and creates a new tree topology in the network if needed
     *
     * @param ip1 first ip
     * @param ip2 second ip
     * @return true if it was successful, false if there was a violation
     */
    public boolean disconnect(final IP ip1, final IP ip2) {
        int index;
        boolean disconnectedFromTree = false;
        boolean disconnectedFromNetwork = true;
        for (TreeTopology topology : topologies) {
            index = topology.removeEdge(ip1, ip2);
            if (index == 0) {
                disconnectedFromTree = true;
            } else if (index == -2) {
                disconnectedFromNetwork = false;
            } else if (index != -1) {
                topologies.add(new TreeTopology(topology, index));
                disconnectedFromTree = true;
            }
        }
        return disconnectedFromTree && disconnectedFromNetwork;
    }

    /**
     * Checks if the given ip is contained in the network
     *
     * @param ip given ip
     * @return true if it is contained
     */
    public boolean contains(final IP ip) {
        for (TreeTopology topology : topologies) {
            if (topology.contains(ip)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the height of the tree topology, that contains the given ip
     *
     * @param root given ip
     * @return 0 if the ip was not found, the height of the tree otherwise
     */
    public int getHeight(final IP root) {
        int height;
        for (TreeTopology topology : topologies) {
            height = topology.getHeight(root);
            if (height != -1) {
                return height;
            }
        }
        return 0;
    }

    /**
     * Gets the ip-s of a topology that contains the given ip, sorts them and
     * returns an unmodifiable list of the tree levels (each level sorted by ip)
     *
     * @param root given ip
     * @return a list of the levels (lists of sorted ip-s)
     */
    public List<List<IP>> getLevels(final IP root) {
        List<List<IP>> levels;
        for (TreeTopology topology : topologies) {
            levels = topology.getLevels(root);
            if (!levels.isEmpty()) {
                return Collections.unmodifiableList(levels);
            }
        }
        return new LinkedList<>();
    }

    /**
     * Gives the shortest path/route from the given ip start to the given ip end in a tree topology that contains them
     *
     * @param start starting ip
     * @param end   end ip
     * @return a list of the traversed edges (the route)
     */
    public List<IP> getRoute(final IP start, final IP end) {
        List<IP> route;
        for (TreeTopology topology : topologies) {
            route = topology.getRoute(start, end);
            if (!route.isEmpty()) {
                return route;
            }
        }
        return new LinkedList<>();
    }

    /**
     * Gives a bracket representation of the ip-s that are in the tree containing the given ip
     *
     * @param root the starting ip
     * @return ip-s of the present nodes in bracket notation
     */
    public String toString(IP root) {
        String bracketNotation = "";
        for (TreeTopology topology : topologies) {
            bracketNotation = topology.toString(root);
            if (!bracketNotation.isEmpty()) {
                return bracketNotation;
            }
        }
        return bracketNotation;
    }

    /**
     * Compares the current network with a given object by structure (of the topologies in it)
     *
     * @param potentialNetwork given object (a potential network)
     * @return true if they have the same structure
     */
    @Override
    public boolean equals(Object potentialNetwork) {
        // check if this is the same object to save resources
        if (potentialNetwork == this) {
            return true;
        }
        // check if this is an instance of the network class
        if (!(potentialNetwork instanceof Network)) {
            return false;
        }
        // check if the tree topologies the network has, are isomorphic/have the same structure
        int counter = ((Network) potentialNetwork).topologies.size();
        for (TreeTopology topology : topologies) {
            for (TreeTopology topology1 : ((Network) potentialNetwork).topologies) {
                if (topology.hasSameStructure(topology1)) {
                    break;
                }
                counter--;
                // return false if the last element of the network to be checked,
                // also does not have the same structure as topology
                if (counter == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
