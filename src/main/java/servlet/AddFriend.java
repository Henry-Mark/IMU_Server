package servlet;

import Utils.DaoUtils;
import com.google.gson.Gson;
import entity.Friend;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wangbl on 2016/12/5.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/12/5. 17:27
 * description:添加好友
 */
@WebServlet("/addFriend")
public class AddFriend extends BaseHttpServlet {

    //参数
    private String param = "addFriendMsg";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收客户端信息
        String data = req.getParameter(param);
        Gson gson = new Gson();
        String response = null;

        if (data != null) {
            Friend friend = gson.fromJson(data, Friend.class);
//            if (DaoUtils.findByParams(User.class,friend.getUserUid()))
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
