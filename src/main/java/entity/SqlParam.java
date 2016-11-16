package entity;

/**
 * Created by wangbl on 2016/11/16.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/11/16. 15:18
 * description: 数据库参数
 */
public class SqlParam {
    /**
     * 参数名称
     */
    public String Name;
    /**
     * 参数值
     */
    public Object Value;

    /**
     * 构造方法
     *
     * @param name
     * @param value
     */
    public SqlParam(String name, Object value) {
        this.Name = name;
        this.Value = value;
    }

}
