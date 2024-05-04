package cn.lottery.lottery.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LotteryPredict {

    private String id;
    private String redPredictStr;
    private String bluePredictStr;
    private String timePredict;
    private String remark;
    private String type;
}
