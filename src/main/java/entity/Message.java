package entity;

/**
 * Created by wangbl on 2016/12/12.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/12/12. 17:14
 * description:
 */
public class Message {

    /*消息类型*/
    private String type;
    /*消息内容*/
    private String content;

    public Message(String type, String content) {
        this.type = type;
        this.content = content;
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

    @Override
    public String toString() {
        String str = "type=" + type + "\ncontent=" + content;
        return super.toString();
    }
}
