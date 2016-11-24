package servlet;

import Utils.LogUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
     * @param c
     */
    protected void LogD(CharSequence c) {
        LogUtils.d(getClass() + ":    " + c);
    }
}
