package cn.lottery.lottery.utils;

import java.util.ArrayList;
import java.util.List;

public class Rule {
    //正态分布
    final static int[] ssq_red = new int[]{
            0, 1, 2,
            3, 4, 5, 6,
            7, 8, 9, 10, 11, 12,
            13, 14, 15, 16, 17, 18, 19,
            20, 21, 22, 23, 24, 25,
            26, 27, 28, 29,
            30, 31, 32
    };
    final static int[] ssq_blue = new int[]{
            0,
            1, 2,
            3, 4, 5,
            6, 7, 8, 9,
            10, 11, 12,
            13, 14,
            15
    };
    final static int[] dlt_red = new int[]{
            0, 1, 2,
            3, 4, 5, 6, 7,
            8, 9, 10, 11, 12, 13,
            14, 15, 16, 17, 18, 19, 20,
            21, 22, 23, 24, 25, 26,
            27, 28, 29, 30, 31,
            32, 33, 34
    };
    final static int[] dlt_blue = new int[]{
            0,
            1,
            2, 3,
            4, 5, 6, 7,
            8, 9,
            10,
            11
    };

    public static List<String> getListByRule(String type, String color, List<String> list) {
        List<String> newList = new ArrayList<>();
        if (type.equals("ssq")) {
            if (color.equals("red")) {
                for (int i : ssq_red) {
                    newList.add(list.get(i));
                }
            }
            if (color.equals("blue")) {
                for (int i : ssq_blue) {
                    newList.add(list.get(i));
                }
            }
        }
        if (type.equals("dlt")) {
            if (color.equals("red")) {
                for (int i : dlt_red) {
                    newList.add(list.get(i));
                }
            }
            if (color.equals("blue")) {
                for (int i : dlt_blue) {
                    newList.add(list.get(i));
                }
            }
        }
        return newList;
    }
}