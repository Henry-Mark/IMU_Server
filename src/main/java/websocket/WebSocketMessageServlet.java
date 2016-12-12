package websocket;

import Utils.DaoUtils;
import Utils.LogUtils;
import entity.SqlParam;
import entity.User;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by wangbl on 2016/12/12.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/12/12. 13:52
 * description:
 */
@WebServlet(urlPatterns = {"/message"})
public class WebSocketMessageServlet extends WebSocketServlet {

    private String param = "userId";

    /**
     * 根据连接，获取对应的用户信息
     * @param request
     * @return
     */
    public User getUser(HttpServletRequest request) {
        String value = request.getParameter(param);
        List<User> users = DaoUtils.findByParams(User.class, new SqlParam("userId", Integer.valueOf(value)));
        if (users != null && users.size() != 0)
            return users.get(0);
        else
            return new User();
    }

    /**
     *
     * @param s
     * @param httpServletRequest
     * @return
     */
    protected StreamInbound createWebSocketInbound(String s, HttpServletRequest httpServletRequest) {
        return new WebSocketMessageInbound(this.getUser(httpServletRequest));
    }
}
