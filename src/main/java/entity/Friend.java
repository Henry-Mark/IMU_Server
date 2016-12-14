package entity;

/**
 * Created by wangbl on 2016/11/24.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/11/24. 14:42
 * description:好友关系实体
 */
public class Friend {
    /**
     * 用户Id
     */
    private int userUid;
    /**
     * 好友Id
     */
    private int friendUid;
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
    private String label;
    /*备注名*/
    private String remarkName;
    /**
     * 亲密度（1-5，五个等级，五颗心）
     */
    private int intimacy;
    /**
     * 好友信息
     */
    private User friendInfo = new User();

    @Override
    public String toString() {
        String string = "\nuserId=" + userUid
                + "\nfriendId=" + friendUid
                + "\nremarkName=" + remarkName
                + "\nbeFriendTimeMillis=" + beFriendTimeMillis
                + "\nsplitTimeMillis=" + splitTimeMillis
                + "\nintimacy=" + intimacy
                + "\nlabel=" + label;
        return string;
    }

    public void setFriendUid(int friendUid) {
        this.friendUid = friendUid;
    }

    public int getFriendUid() {
        return friendUid;
    }

    public void setUserUid(int userUid) {
        this.userUid = userUid;
    }

    public int getUserUid() {
        return userUid;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }

    public String getRemarkName() {
        return remarkName;
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

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setIntimacy(int intimacy) {
        this.intimacy = intimacy;
    }

    public int getIntimacy() {
        return intimacy;
    }

    public void setFriendInfo(User friendInfo) {
        this.friendInfo = friendInfo;
    }

    public User getFriendInfo() {
        return friendInfo;
    }
}
