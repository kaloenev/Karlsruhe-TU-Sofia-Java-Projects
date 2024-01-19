public class User {

    // No setter is needed as the username is set by the constructor and does not need to be changed
    private final String userName;
    // No setter is needed as the id has a pattern, which it follows and once set it must not be changed
    private int id = 0;

    private Status onlineStatus;
    private String statusMessage;

    private Role role;

    public User(String username) {
        this.userName = username;
        this.onlineStatus = Status.OFFLINE;
        //The attribute ID does not need a setter, as it changes when a new User is created
        this.id++;
    }

    /**
     * Getter, this attribute needs to be accessed by another class
     *
     * @return userName (the username of a given user)
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Getter, this attribute needs to be accessed by another class
     *
     * @return id (the id of a given user)
     */
    public int getId() {
        return id;
    }

    public void setOnlineStatus(Status onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public Status getOnlineStatus() {
        return onlineStatus;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    //Setter
    public void updateStatusMessage(String message) {
        this.statusMessage = message;
    }

    @Override
    public String toString() {
        return "[" + getOnlineStatus() + "," + getRole() + "]" + getUserName() +
                "(" + getId() + ")" + ":" + getStatusMessage();
    }
}