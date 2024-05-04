package cn.lottery.lottery.dto;

import cn.lottery.lottery.entity.Dlt;
import cn.lottery.lottery.entity.Ssq;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewestDTO {
    private Ssq ssq;
    private Dlt dlt;
}
