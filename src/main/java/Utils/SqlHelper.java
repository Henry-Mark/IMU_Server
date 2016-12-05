package Utils;

import constants.Constants;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangbl on 2016/11/8.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/11/8. 13:22
 * description: 数据库操作辅助类
 */
public class SqlHelper {
    /**
     * JDBC驱动类型
     */
    public static Class aClass = null;

    /**
     * 获取数据库连接对象
     *
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {

        if (aClass == null) {
            aClass = Class.forName(Constants.Mysql.JDBC_DRIVER);
        }
        return DriverManager.getConnection(Constants.Mysql.DB_URL, Constants.Mysql.USER, Constants.Mysql.PASS);
    }

    /**
     * 执行SQL语句不返回查询的操作，返回受影响的行数
     *
     * @param sql SQL语句
     * @return 受影响的行数
     */
    public static int exexuteNorQuery(String sql) {
        int result = -1;
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = getConnection();
            preparedStatement = conn.prepareStatement(sql);
            result = preparedStatement.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, preparedStatement, null);
        }
        return result;
    }

    /**
     * 关闭JDBC对象，释放资源
     *
     * @param conn      连接对象
     * @param statement 命令对象
     * @param resultSet 结果集对象
     */
    private static void close(Connection conn, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
                resultSet = null;
            }
            if (statement != null) {
                statement.close();
                statement = null;
            }
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行Insert语句，返回Insert成功后标识列的值
     *
     * @param sql
     * @return
     */
    public static int executeIdentity(String sql) {
        int identity = -1;
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();
            statement = conn.createStatement();
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
//                 identity = resultSet.getInt("GENERATED_KEYS");
                identity = resultSet.getInt(1);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return identity;
    }

    /**
     * 执行不返回结果集的存储过程
     *
     * @param sql    存储过程名称
     * @param params 存储过程参数
     */
    public static void executeNonQuery(String sql,
                                       SqlParameter... params) {
        Connection conn = null;
        CallableStatement callableStatement = null;
        try {
            conn = getConnection();
            callableStatement = conn.prepareCall(sql);
            setSqlParameter(callableStatement, params);
            callableStatement.executeUpdate();
            getSqlParameter(callableStatement, params);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, callableStatement, null);
        }
    }

    /**
     * 执行返回聚合函数的操作
     *
     * @param sql 含有聚合函数的SQL语句
     * @return 聚合函数的执行结果
     */
    public static int executeScalar(String sql) {
        int result = -1;
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, preparedStatement, resultSet);
        }
        return result;
    }

    /**
     * 执行返回型集合的SQL语句
     *
     * @param cls
     * @param sql
     * @param <T>
     * @return
     */
    public static <T> List<T> executeList(Class<T> cls, String sql) {
        List<T> list = new ArrayList<T>();
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
             while (resultSet.next()) {
                T obj = executeResultSet(cls, resultSet);
                list.add(obj);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            close(conn, preparedStatement, resultSet);
        }
        return list;
    }

    /**
     * 执行返回泛型集合的存储过程
     *
     * @param cls    泛型类型
     * @param sql    存储过程名称
     * @param params 存储过程参数
     * @param <T>
     * @return
     */
    public static <T> List<T> executeList(Class<T> cls, String sql, SqlParameter... params) {
        List<T> list = new ArrayList<T>();
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();
            stmt = conn.prepareCall(sql);
            setSqlParameter(stmt, params);
            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                T obj = executeResultSet(cls, resultSet);
                list.add(obj);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            close(conn, stmt, resultSet);
        }
        return list;
    }

    /**
     * 执行返回泛型类型对象的SQL语句
     *
     * @param cls 泛型类型
     * @param sql SQL语句
     * @param <T>
     * @return 泛型类型对象
     */
    public static <T> T executeEntity(Class<T> cls, String sql) {
        T obj = null;
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                obj = executeResultSet(cls, resultSet);
                break;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            close(conn, preparedStatement, resultSet);
        }
        return obj;
    }

    /**
     * 将一条记录转成一个对象
     *
     * @param cls       泛型类型
     * @param resultSet ResultSet对象
     * @param <T>
     * @return 泛型类型对象
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws SQLException
     */
    private static <T> T executeResultSet(Class<T> cls, ResultSet resultSet)
            throws InstantiationException, IllegalAccessException, SQLException {
        T obj = cls.newInstance();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        Field[] fields = cls.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String fieldName = field.getName();
            for (int j = 1; j <= columnCount; j++) {
                String columnName = resultSetMetaData.getColumnName(j);
                if (fieldName.equalsIgnoreCase(columnName)) {
                    Object value = resultSet.getObject(j);
                    field.setAccessible(true);
                    field.set(obj, value);
                    break;
                }
            }
        }
        return obj;
    }

    /**
     * 执行返回泛型类型对象的存储过程
     *
     * @param cls    泛型类型
     * @param sql    SQL语句
     * @param params 存储过程参数
     * @param <T>
     * @return 泛型类型对象
     */
    public static <T> T executeEntity(Class<T> cls, String sql,
                                      SqlParameter... params) {
        T obj = null;
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            cs = con.prepareCall(sql);
            setSqlParameter(cs, params);
            rs = cs.executeQuery();
            while (rs.next()) {
                obj = executeResultSet(cls, rs);
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con, cs, rs);
        }
        return obj;
    }

    /**
     * 设置存储过程参数名称，参数值，参数方向
     *
     * @param callableStatement
     * @param params
     * @throws SQLException
     */
    private static void setSqlParameter(CallableStatement callableStatement,
                                        SqlParameter... params) throws SQLException {
        if (params != null) {
            for (SqlParameter param : params) {
                if (param.OutPut) {
                    String paramName = param.Name;
                    if (paramName == null || paramName.equals("")) {
                        callableStatement.registerOutParameter(1, param.Type);//设置返回类型参数
                    } else {
                        callableStatement.registerOutParameter(paramName, param.Type);//设置输出类型参
                    }
                } else {
                    callableStatement.setObject(param.Name, param.Value);//设置输入类型参数
                }
            }
        }
    }

    /**
     * 得到存储过程参数执行结果
     *
     * @param callableStatement
     * @param params
     * @throws SQLException
     */
    private static void getSqlParameter(CallableStatement callableStatement,
                                        SqlParameter... params) throws SQLException {
        for (SqlParameter param : params) {
            if (param.OutPut) {
                String paramName = param.Name;
                if (paramName == null || paramName.equals("")) {
                    param.Value = callableStatement.getObject(1);// 返回类型参数值
                } else {
                    param.Value = callableStatement.getObject(paramName);// 输出类型参数值
                }
            }
        }
    }
}
