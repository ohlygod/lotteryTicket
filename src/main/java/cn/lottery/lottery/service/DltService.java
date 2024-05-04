package cn.lottery.lottery.service;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpUtil;
import cn.lottery.lottery.dto.DltDTO;
import cn.lottery.lottery.entity.Dlt;
import cn.lottery.lottery.entity.LotteryPredict;
import cn.lottery.lottery.mapper.DltMapper;
import cn.lottery.lottery.utils.Constant;
import cn.lottery.lottery.utils.Rule;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DltService {

    @Autowired
    private DltMapper dltMapper;

    /**
     * 更新大乐透
     */
    public void update() {
        String html = HttpUtil.get(Constant.dltApi);
        Document document = Jsoup.parse(html);
        Elements elements = document.select("tr[class=t_tr1]");
        elements.remove(0);
        Constant.newestDlt = elements.get(0).select("td[class=t_tr1]").get(0).text();
        for (Element element : elements) {
            String id = element.select("td[class=t_tr1]").get(0).text();
            if (dltMapper.selectById(id) == null) {
                String red1 = element.select("td[class=cfont2]").get(0).text();
                String red2 = element.select("td[class=cfont2]").get(1).text();
                String red3 = element.select("td[class=cfont2]").get(2).text();
                String red4 = element.select("td[class=cfont2]").get(3).text();
                String red5 = element.select("td[class=cfont2]").get(4).text();
                String blue1 = element.select("td[class=cfont4]").get(0).text();
                String blue2 = element.select("td[class=cfont4]").get(1).text();
                dltMapper.insert(new Dlt(id, red1, red2, red3, red4, red5, blue1, blue2));
            } else {
                break;
            }
        }
    }

    /**
     * 大乐透最新一期号码
     */
    public Dlt getNewest() {
        return dltMapper.selectById(Constant.newestDlt);
    }

    /**
     * 大乐透红球排序
     */
    public List<String> sortDltRed() {
        List<String> redList = Constant.dltRedCache.get(Constant.newestSsq);
        if (redList == null) {
            redList = new ArrayList<>();
            Map<String, Integer> map = new HashMap<>();
            for (int i = 1; i <= 35; i++) {
                String number = "";
                if (i < 10) {
                    number = "0" + i;
                } else {
                    number = String.valueOf(i);
                }
                map.put(number, dltMapper.selectRedByNumber(number).size());
            }
            for (Map.Entry<String, Integer> entry : MapUtil.sortByValue(map, false).entrySet()) {
                redList.add(entry.getKey());
            }
            Constant.dltRedCache.put(Constant.newestSsq, redList);
        }
        return redList;
    }

    /**
     * 大乐透蓝球排序
     */
    public List<String> sortDltBlue() {
        List<String> blueList = Constant.dltBlueCache.get(Constant.newestSsq);
        if (blueList == null) {
            blueList = new ArrayList<>();
            Map<String, Integer> map = new HashMap<>();
            for (int i = 1; i <= 12; i++) {
                String number = "";
                if (i < 10) {
                    number = "0" + i;
                } else {
                    number = String.valueOf(i);
                }
                map.put(number, dltMapper.selectBlueByNumber(number).size());
            }
            for (Map.Entry<String, Integer> entry : MapUtil.sortByValue(map, false).entrySet()) {
                blueList.add(entry.getKey());
            }
            Constant.dltBlueCache.put(Constant.newestSsq, blueList);
        }
        return blueList;
    }

    public DltDTO getDlt() {
        List<Dlt> dltList = new ArrayList<>();
        List<String> redList = Rule.getListByRule("dlt", "red", sortDltRed());
        List<String> blueList = Rule.getListByRule("dlt", "blue", sortDltBlue());
        for (int i = 0; i < 5; i++) {
            int number;
            List<String> list = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                number = RandomUtil.randomInt(0, redList.size() - 1);
                while (list.contains(redList.get(number))) {
                    number = RandomUtil.randomInt(0, redList.size() - 1);
                }
                list.add(redList.get(number));
            }
            List<String> tempList = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                number = RandomUtil.randomInt(0, blueList.size() - 1);
                while (tempList.contains(blueList.get(number))) {
                    number = RandomUtil.randomInt(0, blueList.size() - 1);
                }
                tempList.add(blueList.get(number));
            }
            list = ListUtil.sortByPinyin(list);
            tempList = ListUtil.sortByPinyin(tempList);
            dltList.add(new Dlt("", list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), tempList.get(0),tempList.get(1)));
        }
        return new DltDTO(dltList);
    }

    public DltDTO getSpecialDlt(String red, String blue) {
        List<Dlt> dltList = new ArrayList<>();
        List<String> excludeReds= Arrays.stream(red.split(" ")).collect(Collectors.toList());
        List<String> excludeBlues = Arrays.stream(blue.split(" ")).collect(Collectors.toList());
        List<String> redList = Rule.getListByRule("dlt", "red", sortDltRed());
        List<String> blueList = Rule.getListByRule("dlt", "blue", sortDltBlue());
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
            int number;
            List<String> list = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                number = RandomUtil.randomInt(0, redList.size() - 1);
                while (list.contains(redList.get(number))) {
                    number = RandomUtil.randomInt(0, redList.size() - 1);
                }
                list.add(redList.get(number));
            }
            List<String> tempList = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                number = RandomUtil.randomInt(0, blueList.size() - 1);
                while (tempList.contains(blueList.get(number))) {
                    number = RandomUtil.randomInt(0, blueList.size() - 1);
                }
                tempList.add(blueList.get(number));
            }
            list = ListUtil.sortByPinyin(list);
            tempList = ListUtil.sortByPinyin(tempList);
            dltList.add(new Dlt("", list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), tempList.get(0),tempList.get(1)));
        }
        return new DltDTO(dltList);
    }

    public LotteryPredict generateDoubleDlt(String red, String blue){
        //生成复式大乐透 （分别按照机选和正态分布生成）10注红球 3注蓝球
        List<String> excludeReds= Arrays.stream(red.split(" ")).collect(Collectors.toList());
        List<String> excludeBlues = Arrays.stream(blue.split(" ")).collect(Collectors.toList());
        List<String> redList = Rule.getListByRule("dlt", "red", sortDltRed());
        List<String> blueList = Rule.getListByRule("dlt", "blue", sortDltBlue());
        LotteryPredict lotteryPredict = new LotteryPredict();
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
            int number;
            List<String> list = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                number = RandomUtil.randomInt(0, redList.size() - 1);
                while (list.contains(redList.get(number))) {
                    number = RandomUtil.randomInt(0, redList.size() - 1);
                }
                list.add(redList.get(number));
            }
            List<String> tempList = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                number = RandomUtil.randomInt(0, blueList.size() - 1);
                while (tempList.contains(blueList.get(number))) {
                    number = RandomUtil.randomInt(0, blueList.size() - 1);
                }
                tempList.add(blueList.get(number));
            }
            list = ListUtil.sortByPinyin(list);
            tempList = ListUtil.sortByPinyin(tempList);
            lotteryPredict.setRedPredictStr(list.stream().collect(Collectors.joining(" ")));
            lotteryPredict.setBluePredictStr(tempList.stream().collect(Collectors.joining(" ")));
        }
        return lotteryPredict;
    }

    public String getExcludedTickets(List<String> excludesRed, List<String> excludesBlue) {
        String html = HttpUtil.get(Constant.dltlongtoushaApiUrl);
        Document document = Jsoup.parse("<table><tbody>"+html+"</tbody></table>");
        String id = document.selectXpath("//tbody/tr[last()-5]/td[position()=1]").text();
//        Elements redElements = document.selectXpath("//tbody/tr[last()-5]/td[position()>=4 and position()<=7]//span");
//        for (Element element : redElements) {
//            String red = element.text().replaceAll("&nbsp;","");
//            excludesRed.add(red);
//        }

//        String html01 = HttpUtil.get(Constant.dltfengweishaApiUrl);
//        Document document01 = Jsoup.parse("<table><tbody>"+html01+"</tbody></table>");
//        Elements redElements01 = document01.selectXpath("//tbody/tr[last()-5]//td[position()>=4 and position()<=8]//span");
//        for (Element element : redElements01) {
//            String red = element.text().trim().replaceAll("&nbsp;","");
//            excludesRed.add(red);
//        }

        String html02 = HttpUtil.get(Constant.shayimaApiUrl);
        Document document02 = Jsoup.parse("<table><tbody>"+html02+"</tbody></table>");
        Elements redElements02 = document02.selectXpath("//tbody/tr[last()-5]//td[position()=5 or position()=8]//span");
        for (Element element : redElements02) {
            String red = element.text().trim().replaceAll("&nbsp;","");
            excludesRed.add(red);
        }


        String html04 = HttpUtil.get(Constant.lqdmApiUrl);
        Document document04 = Jsoup.parse(html04);
        Elements blueElements04 = document04.getElementsByClass("nowissue");
        String text06 = blueElements04.get(6).text();
        String text07 = blueElements04.get(7).text();
        excludesBlue.add(text06);
        excludesBlue.add(text07);

        String html041 = HttpUtil.get(Constant.redlqdmApiUrl);
        Document document041 = Jsoup.parse(html041);
        Elements blueElements041 = document041.getElementsByClass("nowissue");
        String text061 = blueElements041.get(6).text();
        String text071 = blueElements041.get(7).text();
        excludesBlue.add(text061);
        excludesBlue.add(text071);



        String html05 = HttpUtil.get(Constant.zhuanjiashahao);
        Document document05 = Jsoup.parse(html05);
        Elements blueElements05 = document05.getElementsByClass("nub-ball nb4");
        Element element = blueElements05.get(1);
        Element element1 = blueElements05.get(2);
        excludesBlue.add(element.text().trim());
        excludesBlue.add(element1.text().trim());

        String html06 = HttpUtil.get(Constant.lqsmApiUrl);
        Document document06 = Jsoup.parse(html06);
        Elements blueElements06 = document06.getElementsByClass("nowissue");
        excludesBlue.add(blueElements06.get(1).text());
        excludesBlue.add(blueElements06.get(2).text());
        excludesBlue.add(blueElements06.get(5).text());
        excludesBlue.add(blueElements06.get(8).text());
        return id;
    }
}
