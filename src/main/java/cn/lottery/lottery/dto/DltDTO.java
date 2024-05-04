package cn.lottery.lottery.dto;

import cn.lottery.lottery.entity.Dlt;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DltDTO {
    private List<Dlt> dltList;
}
