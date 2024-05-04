package cn.lottery.lottery.handler;

import cn.lottery.lottery.dto.SsqDTO;
import cn.lottery.lottery.entity.Ssq;
import cn.lottery.lottery.entity.SsqExclude;
import cn.lottery.lottery.entity.SsqPredict;
import cn.lottery.lottery.service.LotteryService;
import cn.lottery.lottery.service.SsqExcludeService;
import cn.lottery.lottery.service.SsqPredictService;
import cn.lottery.lottery.service.SsqService;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CrawlLotteryInfo {

    @Autowired
    private SsqService ssqService;

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private SsqExcludeService ssqExcludeService;

    @Autowired
    private SsqPredictService ssqPredictService;

    @Scheduled(cron = "0 0 8 * * ?")
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

}
