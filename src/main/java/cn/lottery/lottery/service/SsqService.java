package cn.lottery.lottery.service;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpUtil;
import cn.lottery.lottery.dto.SsqDTO;
import cn.lottery.lottery.entity.Ssq;
import cn.lottery.lottery.mapper.SsqMapper;
import cn.lottery.lottery.utils.Constant;
import cn.lottery.lottery.utils.Rule;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SsqService {

    @Autowired
    private SsqMapper ssqMapper;


    @Transactional
    public void deleteAndQuery(String id,Integer retryCnt){
        try {
            this.deleteById(id);
        } catch (IOException e) {
            if (retryCnt<3){
                log.info("出现异常 尝试重试。。。。。。第" +(retryCnt+1)+ "次" );
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                this.deleteAndQuery(id,retryCnt+1);
            }else {
                e.printStackTrace();
            }
            throw new RuntimeException("shit");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void deleteById(String id) throws IOException {
        ssqMapper.deleteById(id);
        throw new IOException("shit");
    }

    /**
     * 更新双色球
     */
    public void update() {
        String html = HttpUtil.get(Constant.ssqApi);
        Document document = Jsoup.parse(html);
        Elements elements = document.select("tr[class=t_tr1]");
        Constant.newestSsq = elements.get(0).select("td").get(0).text(); //最新一期号码
        for (Element element : elements) {
            String id = element.select("td").get(0).text();
            if (ssqMapper.selectById(id) == null) {
                String red1 = element.select("td[class=t_cfont2]").get(0).text();
                String red2 = element.select("td[class=t_cfont2]").get(1).text();
                String red3 = element.select("td[class=t_cfont2]").get(2).text();
                String red4 = element.select("td[class=t_cfont2]").get(3).text();
                String red5 = element.select("td[class=t_cfont2]").get(4).text();
                String red6 = element.select("td[class=t_cfont2]").get(5).text();
                String blue1 = element.select("td[class=t_cfont4]").get(0).text();
                ssqMapper.insert(new Ssq(id, red1, red2, red3, red4, red5, red6, blue1));
            } else {
                break;
            }
        }
    }

//    public void getExcludedTickets(String api,String redXpath,String blueXpath,List<String> excludeReds,List<String> excludeBlues){
//        //500 red colors
//        String html = HttpUtil.get(api);
//        Document document = Jsoup.parse(html);
//        Elements redElements = document.selectXpath(redXpath);
//        List<String> excludesRed = new ArrayList<>();
//        List<String> excludesBlue = new ArrayList<>();
//        for (Element element : redElements) {
//            String red = element.text();
//            excludesRed.add(red);
//        }
//        //500 blue colors
//        Elements blueElements = document.selectXpath(blueXpath);
//        for (Element element : blueElements) {
//            String blue = element.text();
//            excludesBlue.add(blue);
//        }
//    }


    public String getExcludedTickets(List<String> excludesRed,List<String> excludesBlue){
        //500 red colors
        String html0 = HttpUtil.get(Constant.shahao500ApiUrl);
        Document document0 = Jsoup.parse(html0);
        String id = document0.selectXpath("//div[@class='sh-title']").text();
        Elements redElements0 = document0.selectXpath("//dd[@class='ui-border-b']//span[@class='sh-w40']//em");

        for (Element element : redElements0) {
            String red = element.text();
            excludesRed.add(red);
        }
        //500 blue colors
        Elements blueElements0 = document0.selectXpath("//dd[@class='mt6 ui-border-tb']//span[@class='sh-w40']//em");
        for (Element element : blueElements0) {
            String blue = element.text();
            excludesBlue.add(blue);
        }

        String html = HttpUtil.get(Constant.ssqzjBlueApiUrl);
        Document document = Jsoup.parse("<table><tbody>"+html+"</tbody></table>");
        Elements blueElements = document.selectXpath("//tbody/tr[last()-5]/td[position()=4 or position()=6 or position()=7 or position()=8]//span");
        for (Element element : blueElements) {
            String blue = element.text().replaceAll("&nbsp;","");
            excludesBlue.add(blue);
        }

        String html01 = HttpUtil.get(Constant.ssqzjRed01ApiUrl);
        Document document01 = Jsoup.parse("<table><tbody>"+html01+"</tbody></table>");
        Elements redElements01 = document01.selectXpath("//tbody/tr[last()-5]//td[position()=4 or position() =5]//span");
        for (Element element : redElements01) {
            String red = element.text().trim().replaceAll("&nbsp;","");
            excludesRed.add(red);
        }

        String html02 = HttpUtil.get(Constant.ssqzjRed02ApiUrl);
        Document document02 = Jsoup.parse("<table><tbody>"+html02+"</tbody></table>");
        Elements redElements02 = document02.selectXpath("//tbody/tr[last()-5]//td[position()=6 or position() =7]//span");
        for (Element element : redElements02) {
            String red = element.text().trim().replaceAll("&nbsp;","");
            excludesRed.add(red);
        }

        String html03 = HttpUtil.get(Constant.ssqzjRed03ApiUrl);
        Document document03 = Jsoup.parse("<table><tbody>"+html03+"</tbody></table>");
        Elements redElements03 = document03.selectXpath("//tbody/tr[last()-5]//td[position()=5]//span");
        for (Element element : redElements03) {
            String red = element.text().trim().replaceAll("&nbsp;","");
            excludesRed.add(red);
        }

        String html04 = HttpUtil.get(Constant.ssqzjRed04ApiUrl);
        Document document04 = Jsoup.parse("<table><tbody>"+html04+"</tbody></table>");
        Elements redElements04 = document04.selectXpath("//tbody/tr[last()-5]//td[position()=5]//span");
        for (Element element : redElements04) {
            String red = element.text().trim().replaceAll("&nbsp;","");
            excludesRed.add(red);
        }

        String html05 = HttpUtil.get(Constant.ssqzjRed05ApiUrl);
        Document document05 = Jsoup.parse("<table><tbody>"+html05+"</tbody></table>");
        Elements redElements05 = document05.selectXpath("//tbody/tr[last()-5]//td[position()=3]//span");
        for (Element element : redElements05) {
            String red = element.text().trim().replaceAll("&nbsp;","");
            excludesRed.add(red);
        }

        String html06 = HttpUtil.get(Constant.ssqzjRed06ApiUrl);
        Document document06 = Jsoup.parse("<table><tbody>"+html06+"</tbody></table>");
        Elements redElements06 = document06.selectXpath("//tbody/tr[last()-5]//td[position()=5]//span");
        for (Element element : redElements06) {
            String red = element.text().trim().replaceAll("&nbsp;","");
            excludesRed.add(red);
        }

        String html07 = HttpUtil.get(Constant.expertBlueApiUrl);
        Document document07 = Jsoup.parse(html07);
        Elements blueElements07 = document07.select("tbody.tRhover tr:last-child td:nth-last-child(2)");
        for (Element element : blueElements07) {
            String[] blues = element.text().trim().split(",");
            for (String blue : blues) {
                excludesBlue.add(blue);
            }
        }

        String html08 = HttpUtil.get(Constant.expertRedApiUrl);
        Document document08 = Jsoup.parse(html08);
        Elements redElements08 = document08.select("td.nowissue");
        excludesRed.add(redElements08.get(7).text());
        excludesRed.add(redElements08.get(9).text());

        String html09 = HttpUtil.get(Constant.pfmfdqRedApiUrl);
        Document document09 = Jsoup.parse(html09);
        if (document09!=null){
            String red = document09.selectXpath("//div[@class='grid-ball grid-ball--powerball-secondary--selected']").text();
            excludesRed.add(red);
        }

        String html10 = HttpUtil.get(Constant.pfmfdqBlueApiUrl);
        Document document10 = Jsoup.parse(html10);
        if (document10!=null){
            Elements blueElements08 = document10.selectXpath("//div[@class='grid-ball grid-ball--powerball-primary--selected']");
            excludesBlue.add(blueElements08.get(0).text().trim());
        }

        String html11 = HttpUtil.get(Constant.hqsmApiUrl);
        Document document11 = Jsoup.parse(html11);
        if (document11!=null){
            Elements redEles = document11.getElementsByClass("item").get(6).select("p:eq(3)");
            for (Element element : redEles) {
                excludesRed.add(element.text().trim().split("：")[1]);
            }
        }


        return id;
    }

    /**
     * 获取双色球最新一期号码
     */
    public Ssq getNewest() {
        return ssqMapper.selectById(Constant.newestSsq);
    }

    /**
     * 双色球红球排序
     */
    public List<String> sortSsqRed() {
        List<String> redList = Constant.ssqRedCache.get(Constant.newestSsq);
        if (redList == null) {
            redList = new ArrayList<>();
            Map<String, Integer> map = new HashMap<>();
            for (int i = 1; i <= 33; i++) {
                String number;
                if (i < 10) {
                    number = "0" + i;
                } else {
                    number = String.valueOf(i);
                }
                map.put(number, ssqMapper.selectRedByNumber(number).size());
            }
            for (Map.Entry<String, Integer> entry : MapUtil.sortByValue(map, false).entrySet()) {
                redList.add(entry.getKey());
            }
            Constant.ssqRedCache.put(Constant.newestSsq, redList);
        }
        return redList;
    }

    /**
     * 双色球蓝球排序
     */
    public List<String> sortSsqBlue() {
        List<String> blueList = Constant.ssqBlueCache.get(Constant.newestSsq);
        if (blueList == null) {
            blueList = new ArrayList<>();
            Map<String, Integer> map = new HashMap<>();
            for (int i = 1; i <= 16; i++) {
                String number;
                if (i < 10) {
                    number = "0" + i;
                } else {
                    number = String.valueOf(i);
                }
                map.put(number, ssqMapper.selectBlueByNumber(number).size());
            }
            for (Map.Entry<String, Integer> entry : MapUtil.sortByValue(map, false).entrySet()) {
                blueList.add(entry.getKey());
            }
            Constant.ssqBlueCache.put(Constant.newestSsq, blueList);
        }
        return blueList;
    }

    public SsqDTO getSsq() {
        List<Ssq> ssqList = new ArrayList<>();
        List<String> redList = Rule.getListByRule("ssq", "red", sortSsqRed());
        List<String> blueList = Rule.getListByRule("ssq", "blue", sortSsqBlue());
        List<String> tempBlueList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int number;
            List<String> list = new ArrayList<>();
            for (int j = 0; j < 6; j++) {
                number = RandomUtil.randomInt(0, redList.size() - 1);
                while (list.contains(redList.get(number))) {
                    number = RandomUtil.randomInt(0, redList.size() - 1);
                }
                list.add(redList.get(number));
            }
            number = RandomUtil.randomInt(0, blueList.size() - 1);
            while (tempBlueList.contains(blueList.get(number))) {
                number = RandomUtil.randomInt(0, blueList.size() - 1);
            }
            tempBlueList.add(blueList.get(number));
            list = ListUtil.sortByPinyin(list);
            ssqList.add(new Ssq("", list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5), blueList.get(number)));
        }
        return new SsqDTO(ssqList);
    }

    public SsqDTO getSpecialSsq(String red,String blue){
        List<Ssq> ssqList = new ArrayList<>();
        List<String> excludeReds= Arrays.stream(red.split(" ")).collect(Collectors.toList());
        List<String> excludeBlues = Arrays.stream(blue.split(" ")).collect(Collectors.toList());
        List<String> redList = Rule.getListByRule("ssq", "red", sortSsqRed());
        List<String> blueList = Rule.getListByRule("ssq", "blue", sortSsqBlue());

        for (String s : excludeReds) {
            if (redList.contains(s)){
                redList.remove(s);
            }
        }
        for (String s : excludeBlues) {
            if (blueList.contains(s)){
                blueList.remove(s);
            }
        }
        for (int i = 0; i < 5; i++) {
            List<String> tempBlueList = new ArrayList<>();
            int number;
            List<String> list = new ArrayList<>();
            for (int j = 0; j < 6; j++) {
                number = RandomUtil.randomInt(0, redList.size());
                while (list.contains(redList.get(number))) {
                    number = RandomUtil.randomInt(0, redList.size() );
                }
                list.add(redList.get(number));
            }
            number = RandomUtil.randomInt(0, blueList.size() );
            while (tempBlueList.contains(blueList.get(number))) {
                number = RandomUtil.randomInt(0, blueList.size());
            }
            tempBlueList.add(blueList.get(number));
            list = ListUtil.sortByPinyin(list);
            ssqList.add(new Ssq("", list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5),tempBlueList.get(0) ));
        }
        return new SsqDTO(ssqList);
    }

    public Map<String,List<String>> getSsqList(String red,String blue){
        Map<String, List<String>> map = new HashMap<>();
        List<Ssq> ssqList = new ArrayList<>();
        List<String> excludeReds= Arrays.stream(red.split(" ")).collect(Collectors.toList());
        List<String> excludeBlues = Arrays.stream(blue.split(" ")).collect(Collectors.toList());
        List<String> redList = Rule.getListByRule("ssq", "red", sortSsqRed());
        List<String> blueList = Rule.getListByRule("ssq", "blue", sortSsqBlue());
        List<String> tempBlueList = new ArrayList<>();
        for (String s : excludeReds) {
            if (redList.contains(s)){
                redList.remove(s);
            }
        }
        for (String s : excludeBlues) {
            if (blueList.contains(s)){
                blueList.remove(s);
            }
        }

        int number;
        List<String> list = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            number = RandomUtil.randomInt(0, redList.size());
            while (list.contains(redList.get(number))) {
                number = RandomUtil.randomInt(0, redList.size() );
            }
            list.add(redList.get(number));
        }
        number = RandomUtil.randomInt(0, blueList.size() );
        while (tempBlueList.contains(blueList.get(number))) {
            number = RandomUtil.randomInt(0, blueList.size());
        }
        tempBlueList.add(blueList.get(number));
        list = ListUtil.sortByPinyin(list);

        map.put("redList",redList);
        map.put("blueList",blueList);

        return map;
    }

    public String getDouble(String red,String blue,Integer redNum,Integer blueNum){
        Map<String, List<String>> map = new HashMap<>();
        List<Ssq> ssqList = new ArrayList<>();
        List<String> excludeReds= Arrays.stream(red.split(" ")).collect(Collectors.toList());
        List<String> excludeBlues = Arrays.stream(blue.split(" ")).collect(Collectors.toList());
        List<String> redList = Rule.getListByRule("ssq", "red", sortSsqRed());
        List<String> blueList = Rule.getListByRule("ssq", "blue", sortSsqBlue());
        List<String> tempBlueList = new ArrayList<>();
        for (String s : excludeReds) {
            if (redList.contains(s)){
                redList.remove(s);
            }
        }
        for (String s : excludeBlues) {
            if (blueList.contains(s)){
                blueList.remove(s);
            }
        }

        int number;
        List<String> list = new ArrayList<>();
        for (int j = 0; j < redNum; j++) {
            number = RandomUtil.randomInt(0, redList.size());
            while (list.contains(redList.get(number))) {
                number = RandomUtil.randomInt(0, redList.size() );
            }
            list.add(redList.get(number));
        }
        number = RandomUtil.randomInt(0, blueList.size() );
        for (int i = 0; i <blueNum ; i++) {
            while (tempBlueList.contains(blueList.get(number))) {
                number = RandomUtil.randomInt(0, blueList.size());
            }
            tempBlueList.add(blueList.get(number));
        }
        String redBall = list.stream().collect(Collectors.joining(" "));
        String blueBall = tempBlueList.stream().collect(Collectors.joining(" "));

        return redBall+" "+blueBall;
    }
}