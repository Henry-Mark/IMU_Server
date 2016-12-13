package entity;

/**
 * Created by wangbl on 2016/12/5.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/12/5. 13:52
 * description:消息
 */
public class ChatMsg extends Message {

    /**
     * 消息类型（0.文字消息；1.图片消息；2.语音消息；3.小视频；4.文件；11.文字阅后即焚；12.图片阅后即焚；13.语音阅后即焚；14.视频阅后即焚）
     */
    private int messageType;

    /**
     * 消息发送状态（0.正在发送；1.发送成功；2.发送失败；）
     */
    private int state;
    /*消息发送者ID*/
    private int fromUserId;
    /*消息接收者ID*/
    private int toUserId;

    /**
     * 图片或视频url
     */
    private String mediaUrl;
    /*缩略图Url*/
    private String thumbnailUrl;

    @Override
    public String toString() {
        String str = super.toString()
                + "\nmessageType=" + messageType
                + "\nstate=" + state
                + "\nfromUserId=" + fromUserId
                + "\ntoUserId=" + toUserId
                + "\nmediaUrl=" + mediaUrl
                + "\nthumbnailUrl=" + thumbnailUrl;
        return str;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public int getToUserId() {
        return toUserId;
    }


    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
