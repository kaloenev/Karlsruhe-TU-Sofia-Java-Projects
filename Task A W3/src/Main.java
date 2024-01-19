import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        Server server1 = new Server("TeaPot", LocalDateTime.of(2020, 1, 1, 12, 0));
        for (int i = 0; i < 33; i++) {
            new User("Sid");
        }
        User user1 = new User ("Tommy");
        server1.addUser(user1);
        user1.setOnlineStatus(Status.ONLINE);
        server1.changeUserRole(33, Role.ADMIN);
        server1.listUsers();
        user1.updateStatusMessage("Ich bin jetzt bereit!");
        server1.addChat("General");
        User user2 = new User("Bobby");
        server1.addUser(user2);

        ChatMessage chatMessage1 = new ChatMessage("Hallo!", user2,
                LocalDateTime.of(2020, 1, 1, 13, 0));
        server1.commitMessage(0, chatMessage1);

        ChatMessage chatMessage2 = new ChatMessage("Hallo Bobby", user1,
                LocalDateTime.of(2020, 1, 1, 14, 0));
        server1.commitMessage(0, chatMessage2);

        server1.listChats();

        server1.getChats().get(0).listMessages();

        server1.changeUserRole(34, Role.MODERATOR);
        server1.listUsers();
        server1.removeUser(34);

        System.out.println(user1.getId());
        System.out.println(user2.getId());
    }
}
