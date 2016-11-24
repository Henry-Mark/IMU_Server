package servlet;

import Utils.DaoUtils;
import Utils.EntityUtils;
import com.google.gson.Gson;
import entity.RespDataStr;
import entity.Response;
import entity.SqlParam;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by wangbl on 2016/11/24.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/11/24. 14:54
 * description:登录
 */
public class Login extends BaseHttpServlet {

    /**
     * request: login={"account":"hh","password":"123456"}
     * response:
     * 1.正常数据
     * 2.账号或密码错误
     * 3.没有该账号
     * 4.错误参数
     */
    //参数
    private String param = "login";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收客户端信息
        String data = req.getParameter(param);
        Gson gson = new Gson();
        String response = null;

        if (data != null) {
            User user = gson.fromJson(data, User.class);

            SqlParam[] params = {new SqlParam("account", user.getAccount()), new SqlParam("password", user.getPassword())};
            List<User> users = DaoUtils.findByParams(User.class, params);
            //账号密码正确
            if (users != null && users.size() == 1) {
                user.setState(1);//设置为在线状态
                if (DaoUtils.update(user, new SqlParam("account", user.getAccount())) != -1) {
                    users.get(0).setState(1);
                    response = gson.toJson(new Response<User>(1, users.get(0)));
                    LogD("返回：" + response);
                }
            } else {
                List<User> list = DaoUtils.findByParams(User.class, new SqlParam("account", user.getAccount()));
                if (list != null && list.size() == 1) {
                    response = gson.toJson(new Response<RespDataStr>(2, new RespDataStr("账号或密码错误")));
                    LogErr("账号或密码错误");
                } else {
                    response = gson.toJson(new Response<RespDataStr>(3, new RespDataStr("没有该账号")));
                    LogErr("没有该账号");
                }
            }
        } else {
            response = gson.toJson(new Response<RespDataStr>(4, new RespDataStr("错误参数")));
            LogErr("错误参数");
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
