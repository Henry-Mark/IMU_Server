package Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangbl on 2016/11/18.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/11/18. 13:19
 * description: 时间操作辅助类
 */
public class TimeUtils {
    /**
     * 获取当前日期和时间
     *
     * @return String
     */
    public static String getCurrentDateStr() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }
}
