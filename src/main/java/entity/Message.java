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
    public static final String CHAT_BACK = "chat_back";                   //聊天返回确认，表示发送成功
    public static final String ADDFRIEND = "addfriend";                 // 添加好友
    public static final String ADDFRIEND_CONFORM = "addfriend_conform";                 // 好友申请确认
    public static final String ADDFRIEND_BACK = "addfriend_back";                 // 好友申请消息返回
    public static final String ONLINE_REMINDER = "onlineReminder";    //好友上线提醒
    public static final String OFFLINE_REMINDER = "offlineReminder";    //好友下线提醒


    public static final String APPLY_AGREE = "agree";   //同意添加好友
    public static final String APPLY_DISAGREE = "disagree";   //同意添加好友


    /**
     * 消息类型：
     * chat：聊天消息
     * addfriend:添加好友
     */
    private String type;
    /*消息内容*/
    private String content;

    /**
     * 用于识别消息
     */
    private long uid;

    /**
     * 发送时间（发送成功的时间）（ms）
     */
    private long SendTimeMillis;


    public Message(String type, String content, long sendTimeMillis, long uid) {
        this.type = type;
        this.uid = uid;
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

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getUid() {
        return uid;
    }


    @Override
    public String toString() {
        String str = "type=" + type
                + "\ncontent=" + content
                + "\nuid=" + uid
                + "\nSendTimeMillis=" + SendTimeMillis;
        return super.toString();
    }
}
