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
    private int userId;
    /**
     * 账号
     */
    private String account;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户真名
     */
    private String username;
    /**
     * 用户密码：6-16字节，字母、数字组合
     */
    private String password;
    /**
     * 用户类型：
     * 0.普通用户
     * 1.VIP用户
     */
    private int type;
    /**
     * 状态：
     * 0.离线
     * 1.在线
     */
    private int state;
    /**
     * 年龄
     */
    private int age;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * * 性别：
     * 0：男
     * 1：女
     * 2.未知
     */
    private int sex;
    /**
     * 头像,命名规则，id+注册时间
     */
    private String avatar;
    /**
     * 签名
     */
    private String signature;
    /**
     * 地址
     */
    private String address;
    /**
     * 注册时间 ms
     */
    private long RegistrationTimeMillis;
    /**
     * 上次访问时间 ms
     */
    private long LastAccessTimeMillis;
    /**
     * 上次修改好友列表时间
     */
    private long friendLastChangedTimeMillis;
    private String Reservedfiled2;

    @Override
    public String toString() {
        String string = "USER:"
                + "\nuserId=" + userId
                + "\naccount=" + account
                + "\nnickname=" + nickname
                + "\nusername=" + username
                + "\npassword=" + password
                + "\ntype=" + type
                + "\nage=" + age
                + "\nphone=" + phone
                + "\nsex=" + sex
                + "\navatarUrl=" + avatar
                + "\nsignature=" + signature
                + "\naddress=" + address
                + "\nRegistrationTimeMillis=" + RegistrationTimeMillis
                + "\nLastAccessTimeMillis=" + LastAccessTimeMillis
                + "\nstate=" + state
                + "\nfriendLastChangedTimeMillis=" + friendLastChangedTimeMillis
                + "\nReservedfiled2=" + Reservedfiled2;
        return string;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getSex() {
        return sex;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSignature() {
        return signature;
    }

    public void setRegistrationTimeMillis(long registrationTimeMillis) {
        RegistrationTimeMillis = registrationTimeMillis;
    }

    public long getRegistrationTimeMillis() {
        return RegistrationTimeMillis;
    }

    public void setLastAccessTimeMillis(long lastAccessTimeMillis) {
        LastAccessTimeMillis = lastAccessTimeMillis;
    }

    public long getLastAccessTimeMillis() {
        return LastAccessTimeMillis;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setFriendLastChangedTimeMillis(long friendLastChangedTimeMillis) {
        this.friendLastChangedTimeMillis = friendLastChangedTimeMillis;
    }

    public long getFriendLastChangedTimeMillis() {
        return friendLastChangedTimeMillis;
    }

    public void setReservedfiled2(String reservedfiled2) {
        Reservedfiled2 = reservedfiled2;
    }

    public String getReservedfiled2() {
        return Reservedfiled2;
    }
}
