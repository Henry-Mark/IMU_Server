package Utils;

import constants.Constants;
import entity.Friend;
import entity.SqlParam;
import entity.User;

import java.util.HashMap;
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
        else if (cls == Friend.class)
            table = Constants.Table.FRIEND;
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
        LogUtils.i("MYSQL 查询: " + sql);
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
            LogUtils.i("MYSQL 查询: " + sql);
            return SqlHelper.executeList(cls, sql);
        }
        return null;
    }

    /**
     * update数据表，对应实体类
     *
     * @param entity 要更新的数据的实体类
     * @param params 参数，update时根据的值，即where之后的参数
     * @param <T>
     * @return
     */
    public static <T> int update(T entity, SqlParam... params) {
        int result = -1;
        String sql = "UPDATE " + getTableName(entity.getClass()) + " SET ";
        String sql_set = null;
        String sql_where = null;
        //实体的KEY和VAULE的map集合
        List<HashMap> maps = null;
        //第一个要更新的字段
        boolean firstParam = true;
        //第一个条件的字段
        boolean firstCondition = true;
        try {
            maps = EntityUtils.reflect(entity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //拼接sql语句
        if (maps != null) {
            for (HashMap map : maps) {
                for (SqlParam param : params) {
                    if (map.get(EntityUtils.KEY_ENTITY).equals(param.Name)) {
                        if (firstCondition) {
                            sql_where = " where " + param.Name + " = " + getValue(param.Value);
                            firstCondition = false;
                        } else {
                            sql_where += " and " + param.Name + " = " + getValue(param.Value);
                        }
                    } else {
                        if (map.get(EntityUtils.VALUE_ENTITY) != null) {
                            if (firstParam) {
                                sql_set = map.get(EntityUtils.KEY_ENTITY) + " = "
                                        + getValue(map.get(EntityUtils.VALUE_ENTITY));
                                firstParam = false;
                            } else {
                                sql_set = sql_set + "," + map.get(EntityUtils.KEY_ENTITY)
                                        + " = " + getValue(map.get(EntityUtils.VALUE_ENTITY));
                            }
                        }
                    }
                }
            }
        }

        if (sql_set != null) {
            sql = sql + sql_set + sql_where + ";";
            result = SqlHelper.exexuteNorQuery(sql);
            LogUtils.i("MYSQL UPDATE: " + sql);
            LogUtils.i("UPDATE RESULT: " + result);
        }
        return result;
    }

    /**
     * 数据表添加记录 INSERT语句
     *
     * @param entity 实体类
     * @param <T>
     * @return
     */
    public static <T> int insert(T entity) {
        int ret = -1;
        String sql = "INSERT INTO " + getTableName(entity.getClass()) + "( ";
        String sql_key = null;
        String sql_value = null;
        //第一个值
        boolean firstParam = true;
        //实体的KEY和VAULE的map集合
        List<HashMap> maps = null;
        try {
            maps = EntityUtils.reflect(entity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //拼接sql语句
        if (maps != null) {
            for (HashMap map : maps) {
                if (map.get(EntityUtils.VALUE_ENTITY) != null) {
                    if (firstParam) {
                        sql_key = map.get(EntityUtils.KEY_ENTITY).toString();
                        sql_value = " ) VALUES ( " + getValue(map.get(EntityUtils.VALUE_ENTITY));
                        firstParam = false;
                    } else {
                        sql_key += ", " + map.get(EntityUtils.KEY_ENTITY).toString();
                        sql_value += "," + getValue(map.get(EntityUtils.VALUE_ENTITY));
                    }
                }
            }
        }

        if (sql_key != null && sql_value != null) {
            sql += sql_key + sql_value + " );";
            ret = SqlHelper.exexuteNorQuery(sql);
            LogUtils.i("MYSQL INSERT: " + sql);
            LogUtils.i("INSERT RESULT: " + ret);
        }

        return ret;
    }

    /**
     * 从数据表中删除实体对应的记录
     *
     * @param entity 实体类
     * @param <T>
     * @return
     */
    public static <T> int delete(T entity) {
        int result = -1;
        String sql = "delete from " + getTableName(entity.getClass()) + " where ";
        boolean firstParam = true;
        //实体的KEY和VAULE的map集合
        List<HashMap> maps = null;
        try {
            maps = EntityUtils.reflect(entity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (maps != null) {
            for (HashMap map : maps) {
                if (map.get(EntityUtils.VALUE_ENTITY) != null) {
                    if (firstParam) {
                        sql += map.get(EntityUtils.KEY_ENTITY)
                                + " = " + getValue(map.get(EntityUtils.VALUE_ENTITY));
                        firstParam = false;
                    } else {
                        sql += " and " + map.get(EntityUtils.KEY_ENTITY)
                                + " = " + getValue(map.get(EntityUtils.VALUE_ENTITY));
                    }
                }
            }
            sql += ";";
            result = SqlHelper.exexuteNorQuery(sql);
            LogUtils.i("MYSQL DELETE: " + sql);
            LogUtils.i("DELETE RESULT: " + result);
        }
        return result;
    }

}
