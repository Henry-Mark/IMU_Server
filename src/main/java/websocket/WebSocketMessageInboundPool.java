package websocket;

import entity.User;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by wangbl on 2016/12/12.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/12/12. 11:38
 * description:用来管理目前在线的用户的连接
 */
public class WebSocketMessageInboundPool {

    /*保存连接的MAP容器*/
    private static final Map<String, WebSocketMessageInbound> connections = new HashMap<String, WebSocketMessageInbound>();

    /**
     * 向连接池中添加连接
     *
     * @param inbound
     */
    public static void addMessageInbound(WebSocketMessageInbound inbound) {
        //添加连接
        System.out.println("user : " + inbound.getUser().getAccount() + " join..");
        connections.put(inbound.getUser().getAccount(), inbound);
    }

    /**
     * 获取所有的在线用户
     *
     * @return
     */
    public static Set<String> getOnlineUser() {
        return connections.keySet();
    }

    /**
     * 移除连接
     *
     * @param inbound
     */
    public static void removeMessageInbound(WebSocketMessageInbound inbound) {
        System.out.println("user : " + inbound.getUser().getAccount() + " exit..");
        connections.remove(inbound.getUser().getAccount());
    }

    /**
     * 向特定的用户发送数据
     *
     * @param user
     * @param message
     */
    public static void sendMessageToUser(User user, String message) {
        try {
            System.out.println("send message to user : " + user + " ,message content : " + message);
            WebSocketMessageInbound inbound = connections.get(user);
            if (inbound != null) {
                inbound.getWsOutbound().writeTextMessage(CharBuffer.wrap(message));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向所有的用户发送消息
     *
     * @param message
     */
    public static void sendMessage(String message) {
        try {
            Set<String> keySet = connections.keySet();
            for (String key : keySet) {
                WebSocketMessageInbound inbound = connections.get(key);
                if (inbound != null) {
                    System.out.println("send message to user : " + key + " ,message content : " + message);
                    inbound.getWsOutbound().writeTextMessage(CharBuffer.wrap(message));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
