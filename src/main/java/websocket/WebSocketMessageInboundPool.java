package websocket;

import Utils.DaoUtils;
import Utils.DataDaoUtils;
import Utils.LogUtils;
import entity.Friend;
import entity.SqlParam;
import entity.User;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.*;

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
        LogUtils.i("user " + inbound.getUser().getAccount() + " join..");
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
        LogUtils.i("user " + inbound.getUser().getAccount() + " exit..");
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
            WebSocketMessageInbound inbound = connections.get(user.getAccount());
            if (inbound != null) {
                inbound.getWsOutbound().writeTextMessage(CharBuffer.wrap(message));
                LogUtils.d("send to " + user.getAccount() + " >> " + message);
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
                    inbound.getWsOutbound().writeTextMessage(CharBuffer.wrap(message));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 向所有在线好友发送消息
     *
     * @param inbound
     * @param message
     */
    public static void sendMessageToOnLineFriend(WebSocketMessageInbound inbound, String message) {
        for (User user : getOnLineFriendList(inbound)) {
            sendMessageToUser(user, message);
        }
    }


    /**
     * 获取所有在线好友
     *
     * @param inbound
     * @return
     */
    private static List<User> getOnLineFriendList(WebSocketMessageInbound inbound) {
        List<User> users = new ArrayList<User>();
        //所有好友
        List<Friend> friends = DataDaoUtils.getFriendList(inbound.getUser());
        //所有在线用户
        Set<String> keySet = connections.keySet();

        for (String key : keySet) {
            WebSocketMessageInbound bound = connections.get(key);
            if (bound != null && bound.getUser() != null) {
                for (Friend friend : friends) {
                    if (friend.getFriendUid() == bound.getUser().getUserId()) {
                        users.add(bound.getUser());
                    }
                }
            }
        }
        return users;
    }

    /**
     * 判断用户是否在线
     *
     * @param inbound
     * @param id
     * @return 在线则返回用户信息，不在线则返回null
     */
    public static User isUserInPool(WebSocketMessageInbound inbound, long id) {
        //所有在线用户
        Set<String> keySet = connections.keySet();
        for (String key : keySet) {
            WebSocketMessageInbound bound = connections.get(key);
            if (bound != null && bound.getUser() != null && id == bound.getUser().getUserId()) {
                return bound.getUser();
            }
        }
        return null;
    }
}
