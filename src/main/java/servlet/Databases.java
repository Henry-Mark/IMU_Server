package servlet;

import Utils.DaoUtils;
import Utils.LogUtils;
import com.google.gson.Gson;
import constants.Constants;
import entity.Response;
import entity.SqlParam;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by wangbl on 2016/11/8.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/11/8. 11:25
 * description:
 */
@WebServlet("/Databases")
public class Databases extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        SqlParam[] params = {new SqlParam("userId",100000001)};
//        List<User> list =  DaoUtils.findByParams(User.class,params);
//        Gson gson = new Gson();
//        String str = gson.toJson(new Response<User>(1,list.get(0)));

//        LogUtils.d("JSON:"+str);

//        User user = new User();
//        user.setUserId(100000004);
//        user.setAge(21);
//        user.setPhone("ds343");
//        user.setLastAccessTimeMillis(81254335);
//        int ret = DaoUtils.update(user);
//        List<User> list = DaoUtils.findAll(User.class);
//        DaoUtils.delete(list.get(3));
//        for (User user1 : DaoUtils.findAll(User.class)) {
//            System.out.println(user1.toString());
//            try {
//                List<HashMap> maps = DaoUtils.reflect(user);
//                for (HashMap<String, Object> map : maps) {
//                    System.out.println("KEY=" + map.get(DaoUtils.KEY_ENTITY));
//                    System.out.println("VAULE=" + map.get(DaoUtils.VALUE_ENTITY));
//                }
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
        getServletContext().log("日志日志顶呱呱");
//        User user = new User();
//        System.out.println("isTrue:"+DaoUtils.s(user,"userId"));


        java.sql.Connection conn = null;
        java.sql.Statement stmt = null;
        // 设置响应内容类型
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        String title = "Servlet Mysql 测试 - 菜鸟教程";
        String docType = "<!DOCTYPE html>\n";
        out.println(docType +
                "<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body bgcolor=\"#f0f0f0\">\n" +
                "<h1 align=\"center\">" + title + "</h1>\n");
        try {
            // 注册 JDBC 驱动器
            Class.forName(Constants.Mysql.JDBC_DRIVER);

            // 打开一个连接
            conn = DriverManager.getConnection(Constants.Mysql.DB_URL, Constants.Mysql.USER, Constants.Mysql.PASS);

            // 执行 SQL 查询
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT userid, username, age FROM USER ";
            ResultSet rs = stmt.executeQuery(sql);

            // 展开结果集数据库
            while (rs.next()) {
                // 通过字段检索
                int id = rs.getInt("userid");
                String name = rs.getString("username");
                String url = rs.getString("age");

                // 输出数据
                out.println("ID: " + id);
                out.println(", 站点名称: " + name);
                out.println(", 站点 URL: " + url);
                out.println("<br />");
            }
            out.println("</body></html>");

            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            // 最后是用于关闭资源的块
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
