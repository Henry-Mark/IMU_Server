package servlet;

import Utils.DaoUtils;
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
 * Created by wangbl on 2016/11/23.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/11/23. 17:21
 * description:注册
 */
public class Register extends BaseHttpServlet {

    /**
     * request: register={"account":"henry","nickname":"henry","password":"123456"}
     * response:
     * 1.正常数据
     * 2.该账号已存在
     * 3.错误参数
     * 4.某参数值为空
     */

    //参数
    private String param = "register";

    /**
     * get方法
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收客户端信息
        String data = req.getParameter(param);
        Gson gson = new Gson();
        String response = null;
        if (data != null) {
            User user = gson.fromJson(data, User.class);
            if (user.getAccount() != null && user.getNickname() != null && user.getPassword() != null) {
                //查看数据表中已有内容，判断是否已经存在该用户
                List<User> list = DaoUtils.findByParams(User.class, new SqlParam("account", user.getAccount()));
                if (list != null && list.size() > 0) {
                    response = gson.toJson(new Response<RespDataStr>(2, new RespDataStr("该账号已存在")));
                    LogErr("该账号已存在");
                } else {
                    //添加注册时间、访问时间
                    user.setLastAccessTimeMillis(System.currentTimeMillis());
                    user.setRegistrationTimeMillis(System.currentTimeMillis());
                    user.setState(1);//设置在线状态
                    if (DaoUtils.insert(user) == -1) {
                        LogErr("添加用户失败");
                    } else {
                        List<User> users = DaoUtils.findByParams(User.class, new SqlParam("account", user.getAccount()));
                        response = gson.toJson(new Response<User>(1, users.get(0)));
                        LogD("添加" + users.get(0).getAccount() + "成功");
                        LogD("返回结果：" + response);
                    }
                }
            } else {
                response = gson.toJson(new Response<RespDataStr>(4, new RespDataStr("缺少字段或某字段值为空")));
                LogErr("缺少字段或某字段值为空");
            }
        } else {
            response = gson.toJson(new Response<RespDataStr>(3, new RespDataStr("错误参数")));
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
