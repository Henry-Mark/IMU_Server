package websocket;

import Utils.DaoUtils;
import Utils.LogUtils;
import com.google.gson.Gson;
import entity.ChatMsg;
import entity.Message;
import entity.SqlParam;
import entity.User;
import org.apache.catalina.authenticator.Constants;
import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.List;

/**
 * Created by wangbl on 2016/12/12.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/12/12. 11:40
 * description:
 */
public class WebSocketMessageInbound extends MessageInbound {

    //当前连接的用户名称
    private final User user;

    private Gson gson = new Gson();

    public WebSocketMessageInbound(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    /**
     * 建立连接
     *
     * @param outbound
     */
    @Override
    protected void onOpen(WsOutbound outbound) {

        //向连接池添加当前的连接对象
        WebSocketMessageInboundPool.addMessageInbound(this);
        LogUtils.d("目前在线人数：" + WebSocketMessageInboundPool.getOnlineUser().size());
        //修改用户状态并存放在数据库
        user.setState(constants.Constants.User.ONLINE);
        DaoUtils.update(user, new SqlParam(DaoUtils.USER_ID, user.getUserId()));

        //连接成功，先查看是否含有未发送的聊天消息，如果有，发送
        List<ChatMsg> chatMsgs = DaoUtils.findByParams(ChatMsg.class, new SqlParam(DaoUtils.CHATMSG_TOUSERID, user.getUserId()));
        if (chatMsgs != null && chatMsgs.size() != 0) {
            for (int i = 0; i < chatMsgs.size(); i++) {
                ChatMsg chatMsg = chatMsgs.get(i);
                if (2 == chatMsg.getState()) {
                    chatMsg.setType(Message.CHAT);
                    WebSocketMessageInboundPool.sendMessageToUser(this.user, gson.toJson(chatMsgs.get(i)));
                }
            }
        }

        //向所有好友发送上线提醒消息
        WebSocketMessageInboundPool.sendMessageToOnLineFriend(
                this, gson.toJson(new Message(Message.ONLINE_REMINDER, user.getAccount(), System.currentTimeMillis())));
    }

    @Override
    protected void onClose(int status) {
        // 触发关闭事件，在连接池中移除连接
        WebSocketMessageInboundPool.removeMessageInbound(this);
        LogUtils.d("目前在线人数：" + WebSocketMessageInboundPool.getOnlineUser().size());
        //修改用户状态并存放在数据库
        user.setState(constants.Constants.User.OFFLINE);
        DaoUtils.update(user, new SqlParam(DaoUtils.USER_ID, user.getUserId()));

        //向所有好友发送下线提醒消息
        WebSocketMessageInboundPool.sendMessageToOnLineFriend(
                this, gson.toJson(new Message(Message.OFFLINE_REMINDER, user.getAccount(), System.currentTimeMillis())));
    }

    protected void onBinaryMessage(ByteBuffer byteBuffer) throws IOException {
        throw new UnsupportedOperationException("Binary message not supported.");
    }

    protected void onTextMessage(CharBuffer charBuffer) throws IOException {
        //向所有在线用户发送消息
        WebSocketMessageInboundPool.sendMessage(charBuffer.toString());
    }
}
