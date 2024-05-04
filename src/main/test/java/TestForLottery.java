import cn.hutool.http.HttpUtil;
import cn.lottery.lottery.LotteryApplication;
import cn.lottery.lottery.dto.DltDTO;
import cn.lottery.lottery.dto.SsqDTO;
import cn.lottery.lottery.entity.*;
import cn.lottery.lottery.mapper.SsqMapper;
import cn.lottery.lottery.service.*;
import cn.lottery.lottery.utils.Constant;
import cn.lottery.lottery.utils.EntityUtils;
import cn.lottery.lottery.utils.LiHuoDing;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest(classes = LotteryApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class TestForLottery {

    @Autowired
    private LotteryService lotteryService;

    @Test
    public void testForLottery(){

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            int cnt = 0;
            while (true){
                cnt++;
                boolean flag = false;
                SsqDTO ssq = lotteryService.getSsq();
                for (Ssq s : ssq.getSsqList()) {
                    String csv = EntityUtils.toCsvString(s);
                    String result = ",11,12,15,18,29,33,02";
                    if (csv.equals(result)){
                        flag = true;
                        break;
                    }
                }
                if (flag){
                    System.out.println(cnt);
                    break;
                }
            }
            list.add(cnt);
        }
        System.out.println(list);

    }

    @Test
    public void testSsqExcludes(){
//        int cnt = 0;
//        Integer award3 = 0;
//        Integer award4 = 0;
//        Integer noAwardNum = 0;
        String excludeRed = "11 16 14 15 05 06 10 09 20 23 24 25 27 13 07 04 02 01 08";
        String excludeBlue = "10 14 08 03 06 09 11 12 01 04 05 02 15 07";
//        Map map = lotteryService.getSsqList(excludeRed, excludeBlue);
//        System.out.println(map);
        Map list = lotteryService.getSsqList(excludeRed, excludeBlue);
        SsqDTO ssq = lotteryService.getSpecialSsq(excludeRed, excludeBlue);
        System.out.println(ssq);
        System.out.println(list);
//        while (true){
//        Integer award6 = 0;
//            cnt++;
//            boolean flag = false;
//            String excludeRed = "15 33 02 13 17 01 14 18 26 31 33 22 12 19 04 08 05 09 19 24 29";
//            String excludeBlue = "10 02 04 03 05 15 16 06 07 13 08 09 01";
//            Map map = lotteryService.getSsqList(excludeRed, excludeBlue);
//            System.out.println(map);
////            for (Ssq s : ssq.getSsqList()) {
////                if (s.getBlue1().equals("03")){
////                    award6 ++;
////                }
////                String result = ",09,13,14,17,19,27,03";
////                String csv= EntityUtils.toCsvString(s);
////                Integer redShootNum = EntityUtils.getRedShootNum(s, result);
////                if (redShootNum==5&&s.getBlue1().equals("03")){
////                    award3++;
////                }
////                if (redShootNum==4&&s.getBlue1().equals("03"))
////                {
////                    award4++;
////                }
////                if (csv.equals(result)){
////                    flag = true;
////                    break;
////                }else {
////                    noAwardNum ++;
////                }
////            }
////            if (flag){
////                System.out.println(award6);
////                System.out.println(award3);
////                System.out.println(noAwardNum);
////                System.out.println(cnt);
////                break;
////            }
//        }
    }

    @Autowired
    private SsqService ssqService;

    @Autowired
    private SsqExcludeService ssqExcludeService;

    @Autowired
    private SsqPredictService ssqPredictService;

    @Autowired
    private DltService dltService;

    @Autowired
    private LotteryPredictService lotteryPredictService;

    @Test
    public void crawlLotterryInfo(){
        List<String> excludesRed = new ArrayList<>();
        List<String> excludesBlue = new ArrayList<>();
        String id = ssqService.getExcludedTickets(excludesRed, excludesBlue);
        excludesRed = excludesRed.stream().map(e->{
            if (e.length()<=1){
                e="0"+e;
            }
            return e;
        }).distinct().collect(Collectors.toList());
        excludesRed.removeIf(s -> StringUtils.isBlank(s));
        excludesBlue = excludesBlue.stream().map(e->{
            if (e.length()<=1){
                e="0"+e;
            }
            return e;
        }).distinct().collect(Collectors.toList());
        excludesBlue.removeIf(s -> StringUtils.isBlank(s));
        String excludeRedStr = excludesRed.stream().collect(Collectors.joining(" "));
        String excludesBlueStr = excludesBlue.stream().collect(Collectors.joining(" "));
        Map list = lotteryService.getSsqList(excludeRedStr, excludesBlueStr);
        SsqDTO ssq = lotteryService.getSpecialSsq(excludeRedStr, excludesBlueStr);
        ssqExcludeService.removeById(id);
        SsqExclude ssqExclude = new SsqExclude();
        ssqExclude.setId(id);
        ssqExclude.setExcludeRedstr(excludeRedStr);
        ssqExclude.setExcludeBluestr(excludesBlueStr);
        ssqExcludeService.save(ssqExclude);

        List<SsqPredict> ssqPredicts = new ArrayList<>();
        for (Ssq ssqPre : ssq.getSsqList()) {
            SsqPredict ssqPredict = new SsqPredict();
            BeanUtils.copyProperties(ssqPre,ssqPredict);
            ssqPredict.setId(id);
            ssqPredicts.add(ssqPredict);
        }
        ssqPredictService.removeById(id);
        ssqPredictService.saveBatch(ssqPredicts);
        System.out.println(ssq);
        System.out.println(list);
    }

    @Autowired
    DltExcludeService dltExcludeService;

    @Test
    public void generateDlt(){
        List<String> excludesRed = new ArrayList<>();
        List<String> excludesBlue = new ArrayList<>();
        DltExclude dltExclude = new DltExclude();
        String id = dltService.getExcludedTickets(excludesRed, excludesBlue);
        excludesRed = excludesRed.stream().map(e->{
            if (e.length()<=1){
                e="0"+e;
            }
            return e;
        }).distinct().collect(Collectors.toList());
        excludesRed.removeIf(s -> StringUtils.isBlank(s));
        excludesBlue = excludesBlue.stream().map(e->{
            if (e.length()<=1){
                e="0"+e;
            }
            return e;
        }).distinct().collect(Collectors.toList());
        excludesBlue.removeIf(s -> StringUtils.isBlank(s));
        String excludeRedStr = excludesRed.stream().collect(Collectors.joining(" "));
        String excludesBlueStr = excludesBlue.stream().collect(Collectors.joining(" "));

        LotteryPredict predict = dltService.generateDoubleDlt(excludeRedStr, excludesBlueStr);
        System.out.println(predict);
        predict.setId(id);
        predict.setType("大乐透");
        lotteryPredictService.removeById(id);
        lotteryPredictService.save(predict);
        dltExclude.setId(id);
        dltExclude.setExcludeRedstr(excludeRedStr);
        dltExclude.setExcludeBluestr(excludesBlueStr);
        dltExcludeService.removeById(id);
        dltExcludeService.save(dltExclude);

    }

    @Test
    public void testForJdbc(){
        String html11 = HttpUtil.get(Constant.hqsmApiUrl);
        Document document11 = Jsoup.parse(html11);
        if (document11!=null){
            Elements redEles = document11.getElementsByClass("item").get(6).select("p:eq(3)");
            System.out.println(redEles);
        }
    }

    @Test
    public void testForDlt(){
        String excludesRed="24 25 03 01 33 30 ";
    }

    @Test
    public void testForSsq(){
        String excludesRedStr01="11 16 14 15 05 06 09 20 23 25 27 13 07 04 02 01 08 12 17 18 19 21 22 23 03";
        String excludesBlueStr01="10 14 08 03 06 09 11 12 01 04 05 02 15 07";
        int cnt1=0;
        int cnt2=0;
        int cnt3=0;
        Integer redNum = 9;
        Integer blueNum = 2;
        for (int j = 0; j < 1000; j++) {
            List<String> excludesRed = Arrays.asList(excludesRedStr01.split(" "));
            List<String> excludesBlue = Arrays.asList(excludesBlueStr01.split(" "));
            excludesRed = excludesRed.stream().map(e->{
                if (e.length()<=1){
                    e="0"+e;
                }
                return e;
            }).distinct().collect(Collectors.toList());
            excludesRed.removeIf(s -> StringUtils.isBlank(s));
            excludesBlue = excludesBlue.stream().map(e->{
                if (e.length()<=1){
                    e="0"+e;
                }
                return e;
            }).distinct().collect(Collectors.toList());
            excludesBlue.removeIf(s -> StringUtils.isBlank(s));
            String excludeRedStr = excludesRed.stream().collect(Collectors.joining(" "));
            String excludesBlueStr = excludesBlue.stream().collect(Collectors.joining(" "));
            String ballsString = ssqService.getDouble(excludeRedStr, excludesBlueStr,redNum,blueNum);
            String[] ballArray = ballsString.trim().split(" ");

            int[] redBalls = new int[100];
            int[] blueBalls = new int[100];

            for (int i = 0; i < redNum; i++) {
                redBalls[i] = Integer.parseInt(ballArray[i]);
            }

            for (int i = redNum; i < redNum+blueNum; i++) {
                blueBalls[i - redNum] = Integer.parseInt(ballArray[i]);
            }
            int prizeLevel = calculatePrize(redBalls, blueBalls,
                    new int[]{10,24,26,28,29,31}, new int[]{16});

            switch (prizeLevel){
                case 1:cnt1++;break;
                case 2:cnt2++;break;
                case 3:cnt3++;break;
            }

            System.out.println("中奖等级为：" + prizeLevel+ "  次数："+(j+1));
        }
        System.out.println(cnt1);

    }

    @Test
    public void getSsqDouble(){
        String excludesRedStr01="11 16 14 15 05 06 10 09 20 23 24 25 27 13 07 04 02 01 08";
        String excludesBlueStr01="10 14 08 03 06 09 11 12 01 04 05 02 15 07";
        int cnt1=0;
        int cnt2=0;
        int cnt3=0;
        Integer redNum = 10;
        Integer blueNum = 2;
        for (int j = 0; j < 30; j++) {
            List<String> excludesRed = Arrays.asList(excludesRedStr01.split(" "));
            List<String> excludesBlue = Arrays.asList(excludesBlueStr01.split(" "));
            excludesRed = excludesRed.stream().map(e->{
                if (e.length()<=1){
                    e="0"+e;
                }
                return e;
            }).distinct().collect(Collectors.toList());
            excludesRed.removeIf(s -> StringUtils.isBlank(s));
            excludesBlue = excludesBlue.stream().map(e->{
                if (e.length()<=1){
                    e="0"+e;
                }
                return e;
            }).distinct().collect(Collectors.toList());
            excludesBlue.removeIf(s -> StringUtils.isBlank(s));
            String excludeRedStr = excludesRed.stream().collect(Collectors.joining(" "));
            String excludesBlueStr = excludesBlue.stream().collect(Collectors.joining(" "));
            String ballsString = ssqService.getDouble(excludeRedStr, excludesBlueStr,redNum,blueNum);
            System.out.println("预测号码为：" + ballsString);
        }
        System.out.println(cnt1);

    }

    public static int calculatePrize(int[] redBalls, int[] blueBalls, int[] winningRedBalls, int[] winningBlueBalls) {
        int redMatch = 0;
        int blueMatch = 0;

        // 计算红色球的中奖数量
        for (int redBall : redBalls) {
            for (int winningRedBall : winningRedBalls) {
                if (redBall == winningRedBall) {
                    redMatch++;
                    break;
                }
            }
        }

        // 判断蓝色球是否中奖
        for (int blueBall : blueBalls) {
            for (int winningBlueBall : winningBlueBalls) {
                if (blueBall == winningBlueBall) {
                    blueMatch = 1;
                    break;
                }
            }
        }

        // 根据中奖情况返回奖金等级
        if (redMatch == 6 && blueMatch == 1) {
            return 1; // 一等奖
        } else if (redMatch == 6) {
            return 2; // 二等奖
        } else if (redMatch == 5 && blueMatch == 1) {
            return 3; // 三等奖
        } else if (redMatch == 5 || (redMatch == 4 && blueMatch == 1)) {
            return 4; // 四等奖
        } else if (redMatch == 4 || (redMatch == 3 && blueMatch == 1)) {
            return 5; // 五等奖
        } else if (blueMatch == 1) {
            return 6; // 六等奖（仅中蓝色球）
        } else {
            return 0; // 未中奖
        }
    }

    @Test
    public void testForEachSession(){
        //查出近30期的实际出奖号码 按期数存入数据库
        //根据红球的排除生成10+2的复式 测试每期中了多少
    }

    @Test
    public void testForCrawl(){
        String html041 = HttpUtil.get(Constant.redlqdmApiUrl);
        Document document041 = Jsoup.parse(html041);
        Elements blueElements041 = document041.getElementsByClass("nowissue");
        String text061 = blueElements041.get(6).text();
        String text071 = blueElements041.get(7).text();
        System.out.println(text061);
        System.out.println(text071);
    }

    @Test
    public void testForTransactional(){
        ssqService.deleteAndQuery("03007",0);
    }

    @Test
    public void testForUpdate(){
        ssqService.update();
    }

    @Autowired
    private SsqMapper ssqMapper;

    @Test
    public void testForRedBlue(){
        //获取所有的双色球号码
        List<Ssq> ssqs = ssqMapper.selectList(new LambdaQueryWrapper<>());
        //遍历双色球
        for (int i = 0; i < ssqs.size()-1; i++) {
            //取前一期和后一期
            Ssq ssq01 = ssqs.get(i);
            Ssq ssq02 = ssqs.get(i+1);
            Integer rule01 = LiHuoDing.rule01(ssq01);
        }
    }
}
