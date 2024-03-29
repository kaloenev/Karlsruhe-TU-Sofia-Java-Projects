package networkstructure;

import exceptions.ParseException;

import java.util.List;

public class Main {

    public static void main(String[] args) throws ParseException {
        IP kur = new IP ("39.20.222.120");
        IP kur2 = new IP ("77.135.84.171");
        System.out.println(kur.compareTo(kur2));
//        NetworkStructure.Network network = new NetworkStructure.Network("(85.193.148.81 (141.255.1.133 122.117.67.158 0.146.197.108) 34.49.145.239 (231.189.0.127 77.135.84.171 39.20.222.120 252.29.23.0 116.132.83.77)");
////        List<NetworkStructure.IP> hui = new ArrayList<>();
////        hui.add(kur2);
////        network.add(new NetworkStructure.Network(kur, hui));

        // Construct initial network
        IP root = new IP("141.255.1.133");
        List<List<IP>> levels = List.of(List.of(root),
                List.of(new IP("0.146.197.108"), new IP("122.117.67.158")));
        final Network network = new Network(root, levels.get(1));
        final Network network1 = new Network("(0.146.197.108 (141.255.1.133 200.35.67.89 122.117.67.0))");
        System.out.println(network.connect(root, new IP("200.35.67.89")));
        System.out.println(network.equals(network1));
        System.out.println(network.equals(new IP("0.146.197.108")));
        // (141.255.1.133 0.146.197.108 122.117.67.158)
        System.out.println(network.toString(root));
        // true
        System.out.println((levels.size() - 1) == network.getHeight(root));
        // true
        System.out.println(List.of(List.of(root), levels.get(1))
                .equals(network.getLevels(root)));

        // "Change" root and call toString, getHeight and getLevels again
        root = new IP("122.117.67.158");
        levels = List.of(List.of(root), List.of(new IP("141.255.1.133")),
                List.of(new IP("0.146.197.108")));
        // true
        System.out.println("(122.117.67.158 (141.255.1.133 0.146.197.108))"
                .equals(network.toString(root)));
        // true
        System.out.println((levels.size() - 1) == network.getHeight(root));
        // true
        System.out.println(levels.equals(network.getLevels(root)));

        // Try to add circular dependency
        // false
        System.out.println(
                network.add(new Network("(122.117.67.158 0.146.197.108)")));

        // Merge two subnets with initial network
        // true
        System.out.println(network.add(new Network(
                "(85.193.148.81 34.49.145.239 231.189.0.127 141.255.1.133)")));
        // true
        System.out
                .println(network.add(new Network("(231.189.0.127 252.29.23.0"
                        + " 116.132.83.77 39.20.222.120 77.135.84.171)")));
        root = new IP("85.193.148.81");
        levels = List.of(List.of(root),
                List.of(new IP("34.49.145.239"), new IP("141.255.1.133"),
                        new IP("231.189.0.127")),
                List.of(new IP("0.146.197.108"), new IP("39.20.222.120"),
                        new IP("77.135.84.171"), new IP("116.132.83.77"),
                        new IP("122.117.67.158"), new IP("252.29.23.0")));
        // true
        System.out.println(
                ("(85.193.148.81 34.49.145.239 (141.255.1.133 0.146.197.108"
                        + " 122.117.67.158) (231.189.0.127 39.20.222.120"
                        + " 77.135.84.171 116.132.83.77 252.29.23.0))"
                )
                        .equals(network.toString(root)));
        // true
        System.out.println((levels.size() - 1) == network.getHeight(root));
        // true
        System.out.println(levels.equals(network.getLevels(root)));
        // true
        System.out.println(List
                .of(new IP("141.255.1.133"), new IP("85.193.148.81"),
                        new IP("231.189.0.127"))
                .equals(network.getRoute(new IP("141.255.1.133"),
                        new IP("231.189.0.127"))));

        // "Change" root and call getHeight again
        root = new IP("34.49.145.239");
        levels = List.of(List.of(root), List.of(new IP("85.193.148.81")),
                List.of(new IP("141.255.1.133"), new IP("231.189.0.127")),
                List.of(new IP("0.146.197.108"), new IP("39.20.222.120"),
                        new IP("77.135.84.171"), new IP("116.132.83.77"),
                        new IP("122.117.67.158"), new IP("252.29.23.0")));
        // true
        System.out.println((levels.size() - 1) == network.getHeight(root));
        // "Change" root and call toString, getHeight and getLevels again
        // Remove edge and list tree afterwards
        // true
        System.out.println(network.disconnect(new IP("85.193.148.81"),
                new IP("34.49.145.239")));
        // true
        System.out.println(
                List.of(new IP("0.146.197.108"), new IP("39.20.222.120"),
                        new IP("77.135.84.171"), new IP("85.193.148.81"),
                        new IP("116.132.83.77"), new IP("122.117.67.158"),
                        new IP("141.255.1.133"), new IP("231.189.0.127"),
                        new IP("252.29.23.0")).equals(network.list()));
    }
}
