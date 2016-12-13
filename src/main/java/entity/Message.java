package entity;

/**
 * Created by wangbl on 2016/12/12.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/12/12. 17:14
 * description:
 */
public class Message {

    public static final String CHAT = "chat";                           //聊天
    public static final String ADDFRIEND = "addfriend";                 // 添加好友
    public static final String ONLINE_REMINDER = "onlineReminder";    //好友上线提醒
    public static final String OFFLINE_REMINDER = "offlineReminder";    //好友下线提醒

    /**
     * 消息类型：
     * chat：聊天消息
     * addfriend:添加好友
     */
    private String type;
    /*消息内容*/
    private String content;

    /**
     * 发送时间（发送成功的时间）（ms）
     */
    private long SendTimeMillis;


    public Message(String type, String content, long sendTimeMillis) {
        this.type = type;
        this.content = content;
        this.SendTimeMillis = sendTimeMillis;
    }

    public Message() {

    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setSendTimeMillis(long sendTimeMillis) {
        SendTimeMillis = sendTimeMillis;
    }

    public long getSendTimeMillis() {
        return SendTimeMillis;
    }

    @Override
    public String toString() {
        String str = "type=" + type
                + "\ncontent=" + content
                + "\nSendTimeMillis=" + SendTimeMillis;
        return super.toString();
    }
}
