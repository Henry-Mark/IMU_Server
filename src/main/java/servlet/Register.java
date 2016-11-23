package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wangbl on 2016/11/23.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/11/23. 17:21
 * description:注册
 */
public class Register extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收客户端信息
        String username = req.getParameter("username");
        username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
        String password = req.getParameter("password");
        System.out.println(username + "--" + password);


        PrintWriter out = resp.getWriter();
        out.print("用户名：" + username);
        out.print("密码：" + password);
        out.flush();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
