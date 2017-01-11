package websocket;

import Utils.DaoUtils;
import Utils.LogUtils;
import com.google.gson.Gson;
import entity.ChatMsg;
import entity.Message;
import entity.SqlParam;
import entity.User;
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

    /**
     * 接收消息格式：
     * {"fromUserId":100002,"messageType":0,"state":0,"toUserId":100001,"SendTimeMillis":1482469800959,"content":"sdf","type":"chat","uid":1482469800959}
     */

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
//                    chatMsg.setType(Message.CHAT);
//                    if (chatMsg.getType() == Message.CHAT) {
                    User toFriend = User.getUserById(chatMsg.getToUserId());
                    if (toFriend != null) {
                        WebSocketMessageInboundPool.sendMessageToUser(toFriend, gson.toJson(chatMsgs.get(i)));
                        chatMsg.setState(1);        //设置状态为发送成功

                        DaoUtils.update(chatMsg, new SqlParam(DaoUtils.CHATMSG_UID, chatMsg.getUid()));
                    }
//                    }
                }
            }
        }

        //向所有好友发送上线提醒消息
        WebSocketMessageInboundPool.sendMessageToOnLineFriend(
                this, gson.toJson(new Message(Message.ONLINE_REMINDER,
                        user.getAccount(), System.currentTimeMillis(), -1)));
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
                this, gson.toJson(new Message(Message.OFFLINE_REMINDER,
                        user.getAccount(), System.currentTimeMillis(), -1)));
    }

    protected void onBinaryMessage(ByteBuffer byteBuffer) throws IOException {
        throw new UnsupportedOperationException("Binary message not supported.");
    }

    /**
     * 接收消息
     *
     * @param charBuffer
     * @throws IOException
     */
    protected void onTextMessage(CharBuffer charBuffer) throws IOException {
        LogUtils.i("get msg from " + user.getAccount() + "::    " + charBuffer.toString());
        ChatMsg msg = gson.fromJson(charBuffer.toString(), ChatMsg.class);
        LogUtils.d("msg=" + msg.toString());
        String type = msg.getType();
        if (type != null && !type.equals("")) {
            if (type.equals(Message.CHAT)) {     //聊天消息
                dealChatMsg(msg);
            } else if (type.equals(Message.ADDFRIEND)) {      //添加好友
                dealFriendAddMsg(msg);
            }else if (type.equals(Message.ADDFRIEND_CONFORM)){  //好友确认

            }
        }
        //向所有在线用户发送消息
//        WebSocketMessageInboundPool.sendMessage(charBuffer.toString());
    }

    /**
     * 判断用户是否在线
     *
     * @param userId
     * @return
     */
    private boolean isUserOnline(long userId) {
        List<User> users = DaoUtils.findByParams(User.class, new SqlParam(DaoUtils.USER_ID, userId));
        if (users != null && users.size() != 0) {
            if (users.get(0).getState() != 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 处理聊天消息
     *
     * @param msg
     */
    private void dealChatMsg(ChatMsg msg) {
        sendMsg(msg, Message.CHAT_BACK);
//        msg.setSendTimeMillis(System.currentTimeMillis());  //设置发送时间为当前时间
//        //判断聊天对象是否在线
//        User userTo = WebSocketMessageInboundPool.isUserInPool(this, msg.getToUserId());
//        if (userTo != null) {
//            //发送消息至对方
//            msg.setState(1);    //设置为发送成功
//            WebSocketMessageInboundPool.sendMessageToUser(userTo, gson.toJson(msg)); //发送
//            //写入数据库
//            DaoUtils.insert(msg);
//        } else {
//            msg.setState(2);    //设置为保存数据库待发送状态
//            //写入数据库
//            DaoUtils.insert(msg);
//        }
//        //返回确认消息
//        Message message = new Message(Message.CHAT_BACK, "", System.currentTimeMillis(), msg.getUid());
//        WebSocketMessageInboundPool.sendMessageToUser(user, gson.toJson(message));
    }

    /**
     * 处理好友申请
     *
     * @param chatMsg
     */
    private void dealFriendAddMsg(ChatMsg chatMsg) {
//        //设置发送时间为当前时间
//        chatMsg.setSendTimeMillis(System.currentTimeMillis());
        //设置请求者Id
        chatMsg.setFromUserId(user.getUserId());
        //设置被请求者Id
        chatMsg.setToUserId(Integer.valueOf(chatMsg.getContent().toString()));
        //设置内容为空
        chatMsg.setContent("");
        sendMsg(chatMsg, Message.ADDFRIEND_BACK);
//        //判断添加的对象是否在线
//        User userTo = WebSocketMessageInboundPool.isUserInPool(this, chatMsg.getToUserId());
//        if (userTo != null) {
//            //发送消息至对方
//            chatMsg.setState(1);    //设置为发送成功
//            WebSocketMessageInboundPool.sendMessageToUser(userTo, gson.toJson(chatMsg)); //发送
//            //写入数据库
//            DaoUtils.insert(chatMsg);
//        } else {
//            chatMsg.setState(2);    //设置为保存数据库待发送状态
//            //写入数据库
//            DaoUtils.insert(chatMsg);
//        }
//
//        //返回确认消息
//        Message message = new Message(Message.ADDFRIEND, "", System.currentTimeMillis(), chatMsg.getUid());
//        WebSocketMessageInboundPool.sendMessageToUser(user, gson.toJson(message));
    }

    /**
     * 在线则发送消息，不在线则保存到数据库
     *
     * @param msg
     * @param type
     */
    private void sendMsg(ChatMsg msg, String type) {
        msg.setSendTimeMillis(System.currentTimeMillis());  //设置发送时间为当前时间
        //判断聊天对象是否在线
        User userTo = WebSocketMessageInboundPool.isUserInPool(this, msg.getToUserId());
        if (userTo != null) {
            //发送消息至对方
            msg.setState(1);    //设置为发送成功
            WebSocketMessageInboundPool.sendMessageToUser(userTo, gson.toJson(msg)); //发送
            //写入数据库
            DaoUtils.insert(msg);
        } else {
            msg.setState(2);    //设置为保存数据库待发送状态
            //写入数据库
            DaoUtils.insert(msg);
        }
        //返回确认消息
        Message message = new Message(type, "", System.currentTimeMillis(), msg.getUid());
        WebSocketMessageInboundPool.sendMessageToUser(user, gson.toJson(message));
    }
}
