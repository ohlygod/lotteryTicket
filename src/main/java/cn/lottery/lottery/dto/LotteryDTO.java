package cn.lottery.lottery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LotteryDTO {
    private SsqDTO ssq;
    private DltDTO dlt;
}
