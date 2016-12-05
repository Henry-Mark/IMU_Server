package servlet;

import Utils.DaoUtils;
import com.google.gson.Gson;
import entity.Friend;
import entity.Response;
import entity.SqlParam;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by wangbl on 2016/12/5.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/12/5. 15:34
 * description:获取好友列表
 */

@WebServlet("/friendlist")
public class FriendListServlet extends BaseHttpServlet {

    private String param = "id";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收客户端信息
        String data = req.getParameter(param);
        Gson gson = new Gson();
        String response;
        if (data != null) {

            List<Friend> friends = DaoUtils.findByParams(Friend.class, new SqlParam("userUid", data));
            if (friends == null || friends.size() == 0) {
            } else {
                for (int i = 0; i < friends.size(); i++) {
                    int friendUid = friends.get(i).getFriendUid();
                    List<User> friendinfo = DaoUtils.findByParams(User.class, new SqlParam("userId", friendUid));
                    if (friendinfo != null && friendinfo.size() != 0) {
                        friends.get(i).setFriendInfo(friendinfo.get(0));
                    }
                }

            }
            response = gson.toJson(new Response<Friend>(1, friends));
            putMsg(resp, response);
            LogD(response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
