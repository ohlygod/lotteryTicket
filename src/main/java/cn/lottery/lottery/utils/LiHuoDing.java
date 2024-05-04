package cn.lottery.lottery.utils;

import cn.lottery.lottery.entity.Ssq;

import java.util.ArrayList;
import java.util.List;
public class LiHuoDing {

    public static Integer rule01(Ssq ssq01){
        Integer sum = 0;
        //获得前一期的六个红球个位数相加的和减去5
        String red01 = ssq01.getRed1(); // 前一期的红球01
        String red02 = ssq01.getRed2(); // 前一期的红球02
        String red03 = ssq01.getRed3(); // 前一期的红球03
        String red04 = ssq01.getRed4(); // 前一期的红球04
        String red05 = ssq01.getRed5(); // 前一期的红球05
        String red06 = ssq01.getRed6(); // 前一期的红球06

        // 计算个位数相加的和
        sum += Integer.parseInt(red01) % 10;
        sum += Integer.parseInt(red02) % 10;
        sum += Integer.parseInt(red03) % 10;
        sum += Integer.parseInt(red04) % 10;
        sum += Integer.parseInt(red05) % 10;
        sum += Integer.parseInt(red06) % 10;
        return sum;
    }

    public static Integer rule02(Ssq ssq01){
        // 获取前一期的最小红球和蓝球
        int danma;
        int minRed = Math.min(Integer.parseInt(ssq01.getRed1()), Integer.parseInt(ssq01.getRed2()));
        int blue = Integer.parseInt(ssq01.getBlue1());

        // 判断最小红球和蓝球是否连号
        if (Math.abs(minRed - blue) == 1) {
            // 最小红球加上蓝球再减去1作为胆码
            danma = minRed + blue - 1;
            System.out.println("上期最小红球和蓝球是连号，胆码为: " + danma);
        } else {
            // 最小红球加上蓝球作为胆码
            danma = minRed + blue;
            System.out.println("上期最小红球和蓝球不是连号，胆码为: " + danma);
        }
        return danma;
    }

    public static Integer rule03(Ssq ssq01){
        int blue = Integer.parseInt(ssq01.getBlue1());
        //获得前一期的六个红球个位数相加的和减去5
        String red01 = ssq01.getRed1(); // 前一期的红球01
        String red02 = ssq01.getRed2(); // 前一期的红球02
        String red03 = ssq01.getRed3(); // 前一期的红球03
        String red04 = ssq01.getRed4(); // 前一期的红球04
        String red05 = ssq01.getRed5(); // 前一期的红球05
        String red06 = ssq01.getRed6(); // 前一期的红球06

        List<String> list = new ArrayList<>();
        list.add(red01);
        list.add(red02);
        list.add(red03);
        list.add(red04);
        list.add(red05);
        list.add(red06);

        int primeCount = 0;
        for (String redBall : list) {
            int number = Integer.parseInt(redBall);
            if (isPrime(number)) {
                primeCount++;
            }
        }

        int keyNumber = blue + primeCount;
        return keyNumber;
    }

    public static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static Integer rule04(Ssq ssq01){
        //获得前一期的六个红球个位数相加的和减去5
        String red01 = ssq01.getRed1(); // 前一期的红球01
        String red02 = ssq01.getRed2(); // 前一期的红球02
        String red03 = ssq01.getRed3(); // 前一期的红球03
        String red04 = ssq01.getRed4(); // 前一期的红球04
        String red05 = ssq01.getRed5(); // 前一期的红球05
        String red06 = ssq01.getRed6(); // 前一期的红球06

        int keyNum = Integer.parseInt(red01) + Integer.parseInt(red03);

        if (keyNum>33){
            keyNum -=33;
        }
        return keyNum;
    }
}
