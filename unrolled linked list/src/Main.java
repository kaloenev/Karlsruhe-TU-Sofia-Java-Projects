public class Main {

    public static void main(String[] args) {
	    UnrolledLinkedList list = new UnrolledLinkedList(15);
	    for (int i = -1; i <= 27; i++) {
			System.out.println(list.push(i));
        }
	    list.push(0);
		System.out.println(list.pop());
		System.out.println(list.pop());
		System.out.println(list.get(0));
		System.out.println(list.get(25));
		System.out.println(list.size());
	    for (int i = 0; i < 15; i++) {
			System.out.println(list.pop());
		}
        System.out.println(list.toString(", "));
		System.out.println();
    }
}
