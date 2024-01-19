import java.util.ArrayList;
import java.util.Comparator;

public class Chat {

    // chatName does not need a setter as it set by the constructor and it does not require to be changed later
    // however a chatName could easily be made not final and a setter could be added if needed
    private final String chatName;

    // the id is set with the creation of each object (by the constructor following a pattern)
    private int id = 0;
    private ArrayList<User> users;
    private ArrayList<ChatMessage> chatMessages;

    public String getChatName() {
        return chatName;
    }

    public int getId() {
        return id;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public Chat(String chatName) {
        this.chatName = chatName;
        id += 1;
        this.users = new ArrayList<>(100);
        this.chatMessages = new ArrayList<>(1000);
    }

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

    public void commitMessage(ChatMessage message) {
        if (chatMessages.size() == 1000) {
            chatMessages.remove(0);
        }
        chatMessages.add(message);
    }

    public void clearChat() {
        chatMessages.clear();
    }

    //implemented comparator
    public void listMessages() {
        chatMessages.sort(Comparator.comparing(ChatMessage::getLocalDateTime));

        System.out.println(toString());
        for (ChatMessage chatMessage : chatMessages) {
            System.out.println(chatMessage.toString());
        }
    }

    @Override
    public String toString() {
        return getId() + "," + getChatName();
    }
}
