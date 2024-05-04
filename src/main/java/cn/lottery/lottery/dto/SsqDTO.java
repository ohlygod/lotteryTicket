package cn.lottery.lottery.dto;

import cn.lottery.lottery.entity.Ssq;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SsqDTO {
    private List<Ssq> ssqList;
}
