package servlet;

import Utils.DaoUtils;
import com.google.gson.Gson;
import entity.RespDataStr;
import entity.Response;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by wangbl on 2017/1/6.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2017/1/6. 11:50
 * description: 根据关键词查找用户
 */
@WebServlet("/QueryUser")
public class QueryUser extends BaseHttpServlet {
    /**
     * request: Msg=1000
     * response:
     * 1.用户信息
     * 2.该用户不存在
     * 3.错误参数
     */

    //参数
    private String param = "Msg";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收客户端信息
        String data = req.getParameter(param);
        Gson gson = new Gson();
        String response = null;

        if (data != null) {
            if (!"".equals(data)) {
                //获取所有好友信息
                List<User> users = DaoUtils.findAll(User.class);
                for (User user : users) {
                    if (user.getAccount().equals(data) || data.equals(String.valueOf(user.getPhone()))) {
                        response = gson.toJson(new Response<User>(1, user));
                        break;
                    }
                }
            }

        } else {
            response = gson.toJson(new Response<RespDataStr>(3, new RespDataStr("错误参数")));
        }

        if (response == null) {
            response = gson.toJson(new Response<RespDataStr>(2, new RespDataStr("该用户不存在")));
        }

        if (response != null) {
            resp.setContentType("text/html;charset=UTF-8");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            out.print(response);
            out.flush();
            out.close();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
