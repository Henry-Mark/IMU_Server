package websocket;

import Utils.LogUtils;
import entity.User;
import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

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

    public WebSocketMessageInbound(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    @Override
    protected void onOpen(WsOutbound outbound) {

        // 触发连接事件，在连接池中添加连接
//        JSONObject result = new JSONObject();
//        result.element("type", "user_join");
//        result.element("user", this.user);
        //向所有在线用户推送当前用户上线的消息
        WebSocketMessageInboundPool.sendMessage("connected...." );
        LogUtils.d("user_join:" + this.user.getAccount());

//        result = new JSONObject();
//        result.element("type", "get_online_user");
//        result.element("list", WebSocketMessageInboundPool.getOnlineUser());
        //向连接池添加当前的连接对象
        WebSocketMessageInboundPool.addMessageInbound(this);
        //向当前连接发送当前在线用户的列表
        WebSocketMessageInboundPool.sendMessageToUser(this.user, "get_online_user:" + WebSocketMessageInboundPool.getOnlineUser());
        LogUtils.d("get_online_user:" + WebSocketMessageInboundPool.getOnlineUser());
    }

    @Override
    protected void onClose(int status) {
        // 触发关闭事件，在连接池中移除连接
        WebSocketMessageInboundPool.removeMessageInbound(this);
//        JSONObject result = new JSONObject();
//        result.element("type", "user_leave");
//        result.element("user", this.user);
        //向在线用户发送当前用户退出的消息
        WebSocketMessageInboundPool.sendMessage("user_leave:" + this.user);
        LogUtils.d("user_leave:" + this.user);
    }

    protected void onBinaryMessage(ByteBuffer byteBuffer) throws IOException {
        throw new UnsupportedOperationException("Binary message not supported.");
    }

    protected void onTextMessage(CharBuffer charBuffer) throws IOException {
        //向所有在线用户发送消息
        WebSocketMessageInboundPool.sendMessage(charBuffer.toString());
    }
}
