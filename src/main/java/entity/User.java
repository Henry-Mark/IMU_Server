package entity;

/**
 * Created by wangbl on 2016/11/8.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/11/8. 16:11
 * description: 用户实体，对应User表
 */
public class User {
    /**
     * 用户ID
     */
    public int userId;
    /**
     * 用户昵称
     */
    public String nickname;
    /**
     * 用户真名
     */
    public String username;
    /**
     * 用户密码：6-16字节，字母、数字组合
     */
    public String password;
    /**
     * 用户类型：
     * 0.普通用户
     * 1.VIP用户
     */
    public int type;
    /**
     * 年龄
     */
    public int age;
    /**
     * 手机号码
     */
    public String phone;
    /**
     * * 性别：
     * 0：男
     * 1：女
     * 2.未知
     */
    public int sex;
    /**
     * 头像url
     */
    public String avatarUrl;
    /**
     * 签名
     */
    public String signature;
    /**
     * 地址
     */
    public String address;
    /**
     * 注册时间 ms
     */
    public long RegistrationTimeMillis;
    /**
     * 上次访问时间 ms
     */
    public long LastAccessTimeMillis;
    /**
     * 预留key
     */
    public String Reservedfiled1;
    public String Reservedfiled2;
}
