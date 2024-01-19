import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

public class Server {

    // serverName does not need a setter as it set by the constructor and it does not require to be changed later
    // however a serverName could easily be made not final and a setter could be added if needed
    private final String serverName;
    private int id = 0;

    // the setup time can not be changed so it is final
    private final LocalDateTime setupDateTime;

    // listusers replaces the getter for the users Arraylist
    private ArrayList<User> users;

    public ArrayList<Chat> getChats() {
        return chats;
    }

    private ArrayList<Chat> chats;

    public int getId() {
        return id;
    }

    public LocalDateTime getSetupDateTime() {
        return setupDateTime;
    }

    public Server(String serverName, LocalDateTime setupDateTime) {
        this.serverName = serverName;
        id += 1;
        this.setupDateTime = setupDateTime;
        users = new ArrayList<>(100);
        this.chats = new ArrayList<>(20);
    }

    //Like a setter
    public void addUser(User user) {
        if (users.size() < 100) {
            users.add(user);
        }
    }

    public void removeUser(int id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                users.remove(users.get(i));
            }
        }
    }

    public void addChat(String name) {
        if (chats.size() < 20) {
            Chat newChat = new Chat(name);
            chats.add(newChat);

            // adds the users from the server to the new chat
            for (User user : users) {
                newChat.addUser(user);
            }
        }
    }

    public void removeChat(int id) {
        for (int i = 0; i < chats.size(); i++) {
            if (chats.get(i).getId() == id) {
                chats.remove(chats.get(i));
            }
        }
    }

    public void commitMessage(int chatId, ChatMessage message) {
        for (Chat chat : chats) {
            if (chat.getId() == id) {
                chat.commitMessage(message);
            }
        }
    }

    public void listUsers() {
        users.sort(Comparator.comparing(User::getId));

        System.out.println("# Users=" + users.size());
        for (User user : users) {
            System.out.println(user.toString());
        }
    }

    public void listChats() {
        chats.sort(Comparator.comparing(Chat::getId));

        System.out.println("# Chats=" + chats.size());
        for (Chat chat : chats) {
            System.out.println(chat.toString());
        }
    }

    public void changeUserRole(int userId, Role newRole) {
        for (User user : users) {
            if (user.getId() == userId) {
                user.setRole(newRole);
            }
        }
    }
}
