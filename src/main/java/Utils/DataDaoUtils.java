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
}
