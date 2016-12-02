package servlet;

import Utils.LogUtils;

import javax.servlet.http.HttpServlet;

/**
 * Created by wangbl on 2016/11/23.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/11/23. 17:51
 * description:
 */
public class BaseHttpServlet extends HttpServlet {

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
}
