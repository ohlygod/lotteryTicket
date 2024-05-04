package cn.lottery.lottery.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SsqExclude {

    private String id;
    private String excludeRedstr;
    private String excludeBluestr;
}
