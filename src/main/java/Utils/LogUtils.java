package Utils;

/**
 * Created by wangbl on 2016/11/18.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/11/18. 11:33
 * description: 打印日志辅助类
 */
public class LogUtils {
    /**
     * 打印错误日志
     *
     * @param c
     */
    public static void e(CharSequence c) {
        System.err.println("[" + TimeUtils.getCurrentDateStr() + "] [error] " + c);
    }

    /**
     * 打印debug消息
     *
     * @param c
     */
    public static void d(CharSequence c) {
        System.out.println("[" + TimeUtils.getCurrentDateStr() + "] [denug] " + c);
    }

    /**
     * 打印输出消息
     *
     * @param c
     */
    public static void i(CharSequence c) {
        System.out.println("[" + TimeUtils.getCurrentDateStr() + "] [info] " + c);
    }
}
