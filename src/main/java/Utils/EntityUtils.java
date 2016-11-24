package Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wangbl on 2016/11/18.
 * Creator:henry
 * email:heneymark@gmail.com
 * time: 2016/11/18. 10:31
 * description: 实体类的辅助类，相关方法
 */
public class EntityUtils {

    /**
     * 实体的key、value
     */
    public static final String KEY_ENTITY = "key";
    public static final String VALUE_ENTITY = "value";
    /**
     * 数据类型
     */
    private static final String TYPE_INT = "int";
    private static final String TYPE_LONG = "long";
    private static final String TYPE_BOOBLE = "boolean";
    private static final String TYPE_FLOAT = "float";
    private static final String TYPE_DOUBLE = "double";
    private static final String TYPE_SHORT = "short";

    /**
     * 通过反射获取任意对象的字段名及字段值
     *
     * @param entity
     * @param <T>
     * @return 包括字段名与字段值的HashMap列表
     * @throws IllegalAccessException
     */
    public static <T> List<HashMap> reflect(T entity) throws IllegalAccessException {
        if (entity == null)
            return null;
        List<HashMap> maps = new ArrayList<HashMap>();
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            HashMap map = new HashMap<String, Object>();
            map.put(KEY_ENTITY, field.getName());
            if (field.getType().getName().equals(java.lang.String.class.getName())) {
                //key为String类型
                map.put(VALUE_ENTITY, (String) field.get(entity));
            } else if (field.getType().getName().equals(java.lang.Integer.class.getName())
                    || field.getType().getName().equals(TYPE_INT)) {
                //key为Integer或int类型
                map.put(VALUE_ENTITY, field.getInt(entity));
            } else if (field.getType().getName().equals(java.lang.Long.class.getName())
                    || field.getType().getName().equals(TYPE_LONG)) {
                //key为Long或long类型
                map.put(VALUE_ENTITY, field.getLong(entity));
            } else if (field.getType().getName().equals(java.lang.Boolean.class.getName())
                    || field.getType().getName().equals(TYPE_BOOBLE)) {
                //key为Boolean或boolean类型
                map.put(VALUE_ENTITY, field.getBoolean(entity));
            } else if (field.getType().getName().equals(java.lang.Float.class.getName())
                    || field.getType().getName().equals(TYPE_FLOAT)) {
                //key为Float或float类型
                map.put(VALUE_ENTITY, field.getFloat(entity));
            } else if (field.getType().getName().equals(java.lang.Double.class.getName())
                    || field.getType().getName().equals(TYPE_DOUBLE)) {
                //key为Double或double类型
                map.put(VALUE_ENTITY, field.getDouble(entity));
            } else if (field.getType().getName().equals(java.lang.Short.class.getName())
                    || field.getType().getName().equals(TYPE_SHORT)) {
                //key为Short或short类型
                map.put(VALUE_ENTITY, field.getShort(entity));
            } else {
                //key为其他类型
                map.put(VALUE_ENTITY, field.get(entity));
            }
            maps.add(map);
        }
        return maps;
    }

    public static <T> T nullToSpace(T entity) throws IllegalAccessException {

        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(entity)==null){
                field.set(entity,"");
            }
        }
        return entity;
    }
}
