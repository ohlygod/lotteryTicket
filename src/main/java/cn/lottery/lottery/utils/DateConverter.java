package cn.lottery.lottery.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    public static String convertToTimestamp(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        try {
            Date date = format.parse(dateString);
            long timestamp = date.getTime() / 1000; // 将毫秒转换为秒
            return String.valueOf(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
