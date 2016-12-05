package entity;

/**
 * Created by wangbl on 2016/12/5.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/12/5. 11:48
 * description:验证码
 */
public class Code {
    private String codeMsg;

    public Code(String codeMsg) {
        this.codeMsg = codeMsg;
    }

    public void setCodeMsg(String codeMsg) {
        this.codeMsg = codeMsg;
    }

    public String getCodeMsg() {
        return codeMsg;
    }

    @Override
    public String toString() {
        String msg = "codeMsg=" + codeMsg;
        return msg;
    }
}
