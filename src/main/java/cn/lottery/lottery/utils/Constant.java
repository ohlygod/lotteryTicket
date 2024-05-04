package cn.lottery.lottery.utils;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;

import java.util.List;
import java.util.Map;

public class Constant {
    //双色球
    public static final String ssqApi = "https://datachart.500.com/ssq/history/newinc/history.php?start=3000&end=";
    //大乐透
    public static final String dltApi = "https://datachart.500.com/dlt/history/newinc/history.php?start=07000&end=";
    //最新一期号码
    public static String newestSsq;
    public static String newestDlt;

    public static Cache<String, List<String>> ssqRedCache = CacheUtil.newFIFOCache(1);
    public static Cache<String, List<String>> ssqBlueCache = CacheUtil.newFIFOCache(1);
    public static Cache<String, List<String>> dltRedCache = CacheUtil.newFIFOCache(1);
    public static Cache<String, List<String>> dltBlueCache = CacheUtil.newFIFOCache(1);

    public static final String shahao500ApiUrl =  "https://m.500.com/index.php?c=article&a=shahao&sid=7";

    public static final String ssqzjBlueApiUrl = "https://www.ssqzj.com/shdd/ssq/ajax.php?name=shalansan";

    //选倒数后两个
    public static final String ssqzjRed01ApiUrl = "https://www.ssqzj.com/shdd/ssq/ajax.php?name=shahongyi";

    //选倒数di3个
    public static final String ssqzjRed02ApiUrl = "https://www.ssqzj.com/shdd/ssq/ajax.php?name=shahonger";

    public static final String ssqzjRed03ApiUrl = "https://www.ssqzj.com/shdd/ssq/ajax.php?name=shahongsan";

    public static final String ssqzjRed04ApiUrl = "https://www.ssqzj.com/shdd/ssq/ajax.php?name=shahongsi";

    public static final String ssqzjRed05ApiUrl = "https://www.ssqzj.com/shdd/ssq/ajax.php?name=shahongwu";

    public static final String ssqzjRed06ApiUrl = "https://www.ssqzj.com/shdd/ssq/ajax.php?name=shahongliu";

    public static final String expertBlueApiUrl = "https://expert.78500.cn/ssq/lqssm/";

    public static final String expertRedApiUrl = "https://expert.78500.cn/ssq/hqsm/";

    public static final String pfmfdqRedApiUrl = "https://www.pfmfdq.com/zhunwukezhun.html";

    public static final String pfmfdqBlueApiUrl = "https://www.pfmfdq.com/zhundaonifahuang.html";

    public static final String hqsmApiUrl = "https://8.78500.cn/game/ssq_index.html";

    /*大乐透杀号*////////////////////

    public static final String dltlongtoushaApiUrl = "https://www.ssqzj.com/shdd/dlt/ajax.php?name=longtousha";

    public static final String dltfengweishaApiUrl = "https://www.ssqzj.com/shdd/dlt/ajax.php?name=fengweisha";

    public static final String shayimaApiUrl = "https://www.ssqzj.com/shdd/dlt/ajax.php?name=shayima";

    public static final String lqdmApiUrl = "https://expert.78500.cn/dlt/lqdm/";

    public static final String redlqdmApiUrl = "https://expert.78500.cn/dlt/hqsm/";

    public static final String zhuanjiashahao = "http://zx.500.com/dlt/zhuanjiashahao.php";

    public static final String lqsmApiUrl = "https://expert.78500.cn/dlt/lqsm/";

}
