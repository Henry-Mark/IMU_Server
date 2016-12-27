package servlet;

import Utils.DaoUtils;
import Utils.FileUtils;
import Utils.LogUtils;
import entity.SqlParam;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by wangbl on 2016/11/23.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/11/23. 17:51
 * description:servlet 基类
 */
public class BaseHttpServlet extends HttpServlet {

    /* 项目作用路径根目录 */
    public static final String ROOTPATH = "D:" + File.separator + "IMU";
    /* 图片路径目录 */
    public static final String IMGPATH = ROOTPATH + File.separator + "img";
    /* 头像保存路径 */
    public static final String AVATARPATH = IMGPATH + File.separator + "avatar";

    @Override
    public void init() throws ServletException {
        super.init();
        createDirs();
    }

    /**
     * 创建文件目录
     */
    private void createDirs() {
        //创建跟目录
        if (FileUtils.createDir(ROOTPATH)) {
            //图片目录
            if (FileUtils.createDir(IMGPATH)) {
                FileUtils.createDir(AVATARPATH);
            }
        }

    }

    /**
     * 根据用户id查询用户信息
     * @param id
     * @return
     */
    protected User getUserById(long id){
        List<User> users = DaoUtils.findByParams(User.class,new SqlParam(DaoUtils.USER_ID,id));
        if (users.isEmpty()||users.size()==0) {
            return null;
        }else {
            return users.get(0);
        }
    }

    /**
     * 打印错误日志
     *
     * @param c
     */
    protected void LogErr(CharSequence c) {
        LogUtils.e(getClass() + ":  " + c);
    }

    /**
     * 打印调试日志
     *
     * @param c
     */
    protected void LogD(CharSequence c) {
        LogUtils.d(getClass() + ":    " + c);
    }

    /**
     * 判断当前系统是不是Linux系统
     *
     * @return
     */
    protected boolean isSystemLinux() {
        if (System.getProperties().getProperty("os.name").indexOf("Linux") == -1) {
            return false;
        } else
            return true;
    }

    /**
     * 判断当前系统是否为Windows系统
     *
     * @return
     */
    protected boolean isSystemWindows() {
        if (System.getProperties().getProperty("os.name").indexOf("Windows") == -1) {
            return false;
        } else
            return true;
    }

    /**
     * 向客户端返回消息
     *
     * @param resp
     * @param msg
     * @throws IOException
     */
    protected void putMsg(HttpServletResponse resp, String msg) throws IOException {
        if (msg != null) {
            resp.setContentType("text/html;charset=UTF-8");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            out.print(msg);
            out.flush();
            out.close();
        }
    }
}
