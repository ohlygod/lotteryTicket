package cn.lottery.lottery.controller;

import cn.lottery.lottery.dto.DltDTO;
import cn.lottery.lottery.dto.LotteryDTO;
import cn.lottery.lottery.dto.NewestDTO;
import cn.lottery.lottery.dto.SsqDTO;
import cn.lottery.lottery.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class LotteryController {

    @Autowired
    private LotteryService lotteryService;

    @GetMapping("getNewest")
    public NewestDTO getNewest() {
        return lotteryService.getNewest();
    }

    @GetMapping("getSsq")
    public SsqDTO getSsq() {
        return lotteryService.getSsq();
    }

    @GetMapping("getDlt")
    public DltDTO getDlt() {
        return lotteryService.getDlt();
    }

    @GetMapping("getLottery")
    public LotteryDTO getLottery() {
        return lotteryService.getLottery();
    }
}
