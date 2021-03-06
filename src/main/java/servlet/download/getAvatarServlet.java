package servlet.download;

import Utils.DaoUtils;
import entity.User;
import servlet.BaseHttpServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by wangbl on 2016/12/1.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/12/1. 13:43
 * description:头像图片下载
 */
@WebServlet("/getAvatar")
public class getAvatarServlet extends BaseHttpServlet {

    //参数
    private String param = "id";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String data = req.getParameter(param);
        FileInputStream fis = null;
        if (data != null) {
            User user = getUserById(Long.parseLong(data));

            //通过路径将图片以流的形式得到
            //通过相对路径得到绝对路径
            if (user != null) {
                //String path = this.getServletContext().getRealPath("/WEB-INF/classes/test.jpg");
                String path = AVATARPATH + File.separator + user.getUserId() + user.getRegistrationTimeMillis() + ".jpg";
                //有了路径可以得到输入流（图片，要用字节输出流）
                fis = new FileInputStream(path); //使用绝对路径将图片用输入流变成一个流  fis（图片）

                //用response创建一个输出流
                ServletOutputStream outputStream = resp.getOutputStream();

                /*我们要将图片下载下来 */
                //得到要下载的文件名
                String fileName = path.substring(path.lastIndexOf(File.separator) + 1);
                LogD("download " + fileName + "...");
                //设置文件的编码
                fileName = URLEncoder.encode(fileName, "UTF-8");
                //告知客户端要下载文件
                resp.setHeader("content-disposition", "attachment;filename=" + fileName);
                resp.setHeader("content-type", "image/jpeg");

                if (fis != null) {
                    //执行fileOutputStream的输出操作
                    int len = 1;
                    byte[] b = new byte[1024];
                    while ((len = fis.read(b)) != -1) {
                        outputStream.write(b, 0, len);
                    }
                    outputStream.close();
                    fis.close();
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
