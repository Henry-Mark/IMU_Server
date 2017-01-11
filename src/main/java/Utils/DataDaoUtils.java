package Utils;

import entity.Friend;
import entity.SqlParam;
import entity.User;

import java.util.List;

/**
 * Created by wangbl on 2016/12/13.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/12/13. 11:22
 * description: 处理数据库操作过程的数据
 */
public class DataDaoUtils {

    /**
     * 获取好友列表
     *
     * @param user
     * @return
     */
    public static List<Friend> getFriendList(User user) {
        //所有好友
        List<Friend> friends = DaoUtils.findByParams(Friend.class,
                new SqlParam(DaoUtils.FRIEND_USERID, user.getUserId()));
        return friends;
    }

    /**
     * 根据用户id查询用户信息
     *
     * @param id
     * @return
     */
    public static User getUserById(int id) {
        List<User> users = DaoUtils.findByParams(User.class, new SqlParam(DaoUtils.USER_ID, id));
        if (users.isEmpty() || users.size() == 0) {
            return null;
        } else {
            return users.get(0);
        }
    }

    /**
     * 添加好友
     *
     * @param user1
     * @param user2
     */
    public static boolean addFriend(User user1, User user2) {
        Friend friend1 = new Friend();
        Friend friend2 = new Friend();
        friend1.setUserUid(user1.getUserId());
        friend1.setFriendUid(user2.getUserId());
        friend1.setFriendInfo(user2);
        friend1.setBeFriendTimeMillis(System.currentTimeMillis());
        friend2.setBeFriendTimeMillis(System.currentTimeMillis());
        friend2.setUserUid(user2.getUserId());
        friend2.setFriendUid(user1.getUserId());
        friend2.setFriendInfo(user1);
        if (DaoUtils.insert(friend1) != -1 && DaoUtils.insert(friend2) != -1) {
            return true;
        }
        return false;
    }
}
