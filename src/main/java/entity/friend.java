package entity;

/**
 * Created by wangbl on 2016/11/24.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/11/24. 14:42
 * description:好友关系实体
 */
public class friend {
    /**
     * 用户Id
     */
    private int userId;
    /**
     * 好友Id
     */
    private int friendId;
    /**
     * 成为好友时间
     */
    private long beFriendTimeMillis;
    /**
     * 分手时间
     */
    private long splitTimeMillis;
    /**
     * 标签（将好友分类）
     */
    private String lable;

    @Override
    public String toString() {
        String string = "\nuserId=" + userId
                + "\nfriendId=" + friendId
                + "\nbeFriendTimeMillis=" + beFriendTimeMillis
                + "\nsplitTimeMillis=" + splitTimeMillis
                + "\nlable=" + lable;
        return string;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setBeFriendTimeMillis(long beFriendTimeMillis) {
        this.beFriendTimeMillis = beFriendTimeMillis;
    }

    public long getBeFriendTimeMillis() {
        return beFriendTimeMillis;
    }

    public void setSplitTimeMillis(long splitTimeMillis) {
        this.splitTimeMillis = splitTimeMillis;
    }

    public long getSplitTimeMillis() {
        return splitTimeMillis;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getLable() {
        return lable;
    }
}
