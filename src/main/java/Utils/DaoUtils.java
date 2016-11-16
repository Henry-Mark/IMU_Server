package Utils;

import constants.Constants;
import entity.SqlParam;
import entity.User;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by wangbl on 2016/11/16.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/11/16. 11:46
 * description: 数据库实体辅助类
 */
public class DaoUtils {

    /**
     * 根据实体找到对应的数据表
     *
     * @param cls
     * @param <T>
     * @return
     */
    private static <T> String getTableName(Class<T> cls) {
        String table = null;
        if (cls == User.class)
            table = Constants.Table.USER;
        return table;
    }

    /**
     * 获取不同类型的数据值
     *
     * @param object
     * @return
     */
    private static Object getValue(Object object) {
        if (object instanceof CharSequence) {
            return "'" + object.toString() + "'";
        } else if (object instanceof Long) {
            return ((Long) object).longValue();
        } else if (object instanceof Integer) {
            return ((Integer) object).intValue();
        } else {
            return object;
        }
    }

    /**
     * 获取指定实体对应的的数据表内容
     *
     * @param cls 实体
     * @param <T>
     * @return 实体列表
     */
    public static <T> List<T> findAll(Class<T> cls) {
        String sql = "SELECT * FROM " + getTableName(cls);
        return SqlHelper.executeList(cls, sql);
    }

    /**
     * 根据查询参数，取出实体列表
     *
     * @param cls
     * @param params
     * @param <T>
     * @return
     */
    public static <T> List<T> findByParams(Class<T> cls, SqlParam... params) {
        if (params != null) {
            String sql = "SELECT * FROM " + getTableName(cls) + " WHERE ";
            boolean firstParam = true;
            for (SqlParam param : params) {
                if (firstParam) {
                    sql = sql + param.Name + " = " + getValue(param.Value);
                    firstParam = false;
                } else {
                    sql = sql + " and " + param.Name + " = " + getValue(param.Value);
                }
            }
            sql += ";";
            return SqlHelper.executeList(cls, sql);
        }
        return null;
    }

    public static <T> boolean s(T entity, String field_flag) {
        Field[] fields = entity.getClass().getDeclaredFields();
        String keyField = null;
        String paramField = null;
        boolean firstParam = true;
        for (Field field : fields) {
            if (field.getName().equals(field_flag)) {
                keyField = "UPDATE　"+getTableName(entity.getClass())+" SET ";
                return true;
            }else {
//                field
                if (firstParam) {
                    paramField = "";
                }
            }
        }
        return false;
    }
}
