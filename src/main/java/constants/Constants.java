package constants;

/**
 * Creator:henry
 * email:heneymark@gmail.com
 * time:16-11-3 下午10:05
 * description: 常数
 */
public class Constants {

    //数据库相关
    public class Mysql {
        // JDBC 驱动名及数据库 URL
        public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        public static final String DB_URL = "jdbc:mysql://localhost:3306/HH";

        // 数据库的用户名与密码，需要根据自己的设置
        public static final String USER = "root";
        public static final String PASS = "";
    }

    /**
     * 数据表
     */
    public class Table {
        //用户表，存储用户基本信息
        public static final String USER = "USER";
        //消息表，存储聊天的消息
        public static final String MESSAGE = "MESSAGE";
    }

    /**
     * 短信
     */
    public class Message {
        /* 短信平台接口密钥*/
        public static final String KEY = "8f3ae08f89c95a29b9d8";
        /* 短信平台注册的用户名*/
        public static final String UID = "Henry_Mark";
    }
}
