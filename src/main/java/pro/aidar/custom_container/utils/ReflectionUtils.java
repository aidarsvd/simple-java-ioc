package pro.aidar.custom_container.utils;

import java.lang.reflect.Field;

public class ReflectionUtils {

    public static void setFieldValue(Field field, Object target, Object value) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(target, value);
    }

}
