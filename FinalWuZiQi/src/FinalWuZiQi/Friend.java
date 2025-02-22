package FinalWuZiQi;

public class Friend {
    private String id;
    private String userName;
    private int rank;

    private Friend() {}
    public Friend(String id, String userName, int rank) {
        this.id = id;
        this.userName = userName;
        this.rank = rank;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public int getRank() {
        return rank;
    }

    public String getInfo() {
        return  id + "." + userName + "." + rank;
    }
}