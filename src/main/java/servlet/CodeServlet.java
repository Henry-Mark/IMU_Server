package servlet;


import com.google.gson.Gson;
import constants.Constants;
import entity.Code;
import entity.RespDataStr;
import entity.Response;
import entity.User;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

/**
 * Created by wangbl on 2016/12/5.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/12/5. 9:32
 * description:获取验证码
 */
@WebServlet("/Code")
public class CodeServlet extends BaseHttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String phone = req.getParameter("phone");
        resp.setCharacterEncoding("UTF-8");
        //获取验证码
        String code = getCode(req);
        //发送短信
        int ret = sendMsg(phone, getCodeMsg(code));
        String respMsg;
        if (ret > 0) {
            respMsg = gson.toJson(new Response<Code>(1, new Code(code)));
        } else {
            respMsg = gson.toJson(new Response<RespDataStr>(2, new RespDataStr("获取验证码失败")));
        }
        resp.getWriter().write(respMsg);
        resp.flushBuffer();
    }

    /**
     * 获取消息
     *
     * @param code
     * @return
     */
    private String getCodeMsg(String code) {
        return "您的验证码是[" + code + "]，请不要泄露给其他人。";
    }

    /**
     * 发送短信
     *
     * @param phone 手机号码（多个手机号用逗号隔开）
     * @param msg   消息
     * @return 返回发送短信的数量
     * @throws HttpException
     * @throws IOException
     */
    private int sendMsg(String phone, String msg) throws HttpException, IOException {
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod("http://utf8.sms.webchinese.cn/");
        post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf8");//在头文件中设置转码
        NameValuePair[] data = {
                new NameValuePair("Uid", Constants.Message.UID),// 注册的用户名
                new NameValuePair("Key", Constants.Message.KEY),// 注册成功后，登录网站后得到的密钥
                new NameValuePair("smsMob", phone),// 手机号码
                new NameValuePair("smsText", "验证码：" + msg)};// 短信内容
        post.setRequestBody(data);

        client.executeMethod(post);
        Header[] headers = post.getResponseHeaders();
        int statusCode = post.getStatusCode();
        System.out.println("statusCode:" + statusCode);
        for (Header h : headers) {
            System.out.println("-----" + h.toString());
        }
        String result = new String(post.getResponseBodyAsString().getBytes("utf8"));
        System.out.println("result=" + result); //打印返回消息状态
        post.releaseConnection();
        return Integer.parseInt(result);
    }

    /**
     * 获取随机验证码
     *
     * @param request
     * @return
     */
    private String getCode(HttpServletRequest request) {
        Random rand = new Random();
        String code = "";
        for (int i = 0; i < 4; i++) {
            code += rand.nextInt(10);
        }
        request.getSession().setAttribute("code", code);
        return code;
    }
}
