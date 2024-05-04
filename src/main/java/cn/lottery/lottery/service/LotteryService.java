package cn.lottery.lottery.service;

import cn.lottery.lottery.dto.DltDTO;
import cn.lottery.lottery.dto.LotteryDTO;
import cn.lottery.lottery.dto.NewestDTO;
import cn.lottery.lottery.dto.SsqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LotteryService {

    @Autowired
    private SsqService ssqService;
    @Autowired
    private DltService dltService;

    /**
     * 获取最新一期彩票号码
     */
    public NewestDTO getNewest() {
        ssqService.update();
        dltService.update();
        return new NewestDTO(ssqService.getNewest(),dltService.getNewest());
    }

    public SsqDTO getSsq() {
        return ssqService.getSsq();
    }

    public DltDTO getDlt() {
        return dltService.getDlt();
    }

    public LotteryDTO getLottery() {
        return new LotteryDTO(getSsq(),getDlt());
    }

    public SsqDTO getSpecialSsq(String excludeRed,String excludeBlue){
        return ssqService.getSpecialSsq(excludeRed,excludeBlue);
    }

    public Map getSsqList(String excludeRed, String excludeBlue){
        return ssqService.getSsqList(excludeRed,excludeBlue);
    }

    public DltDTO getSpecialDlt(String excludeRed,String excludeBlue){
        return dltService.getSpecialDlt(excludeRed,excludeBlue);
    }
}
