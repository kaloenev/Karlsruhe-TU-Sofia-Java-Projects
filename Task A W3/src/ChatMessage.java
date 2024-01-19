import java.time.LocalDateTime;

public class ChatMessage {

    // Those attributes have no setters and are final, because they must not be changed
    private final String message;
    private final User sender;
    private final LocalDateTime localDateTime;

    public String getMessage() {
        return message;
    }

    public User getSender() {
        return sender;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public ChatMessage(String message, User sender, LocalDateTime localDateTime) {
        this.message = message;
        this.sender = sender;
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return sender.getUserName() + "[" + getLocalDateTime() + "]" + ":" + message;
    }
}
