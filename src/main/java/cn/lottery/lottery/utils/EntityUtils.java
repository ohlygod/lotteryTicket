package cn.lottery.lottery.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityUtils {
    public static String toCsvString(Object obj) {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        return Arrays.stream(fields)
                .map(field -> {
                    field.setAccessible(true);

                    try {
                        Object o = field.get(obj);
                        return field.get(obj).toString();
                    } catch (IllegalAccessException e) {
                        return "";
                    }
                })
                .collect(Collectors.joining(","));
    }

    public static Integer getRedShootNum(Object obj,String result){
        Class<?> clazz = obj.getClass();
        List<String> list = Arrays.stream(result.split(",")).collect(Collectors.toList());
        Field[] fields = clazz.getDeclaredFields();
        Integer num = 0;
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.getName().contains("red")&&list.contains(field.get(obj).toString())){
                    num ++;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return num;
    }
}
