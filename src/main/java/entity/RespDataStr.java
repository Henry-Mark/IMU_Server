package entity;

/**
 * Created by wangbl on 2016/11/24.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/11/24. 10:41
 * description: 用于返回错误信息data数值
 */
public class RespDataStr {
    private String message;

    public RespDataStr(String message) {
        this.message = message;
    }

    public void setData(String message) {
        this.message = message;
    }

    public String getData() {
        return message;
    }
}
