package FinalWuZiQi;

import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UserInfo {
    private String id;
    private String pwd;
    private String userName;
    private String avatarPath;
    //棋力值
    private int rank;
    private ImageIcon avatar;
    //每次登陆的时候都会询问ip，所以这个并不需要存到文件里
    private String ip;
    //存的是朋友的ID
    private ArrayList<Friend> friends = new ArrayList<>();

    public UserInfo() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public ImageIcon getAvatar() {
        return avatar;
    }
    public void setAvatarAndPath(String path) {
        this.avatar = new ImageIcon(path);
        this.avatarPath = path;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }


    public void addFriend(String friendId, String friendUserName, int friendRank) {
        friends.add(new Friend(friendId, friendUserName ,friendRank));
        sortByRankDescending();
    }
    public void deleteFriend(String friendId) {
        int index = -1;
        for (int i = 0; i < friends.size(); i++) {
            if (friends.get(i).getId().equals(friendId)) {
                index = i;
                break;
            }
        }
        friends.remove(index);
        sortByRankDescending();
    }
    public int getFriendsSize() {
        return friends.size();
    }
    public Friend getFriendByIndex(int index) {
        return friends.get(index);
    }
    public void sortByRankDescending() {
        // 使用自定义的 Comparator 对 friends 列表进行降序排列
        Collections.sort(friends, new Comparator<Friend>() {
            @Override
            public int compare(Friend friend1, Friend friend2) {
                // 比较 rank 字段，降序排列
                return Integer.compare(friend2.getRank(), friend1.getRank());
            }
        });
    }


}
