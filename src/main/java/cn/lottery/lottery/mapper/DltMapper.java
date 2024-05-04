package cn.lottery.lottery.mapper;

import cn.lottery.lottery.entity.Dlt;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DltMapper extends BaseMapper<Dlt> {
    List<Dlt> selectRedByNumber(String number);

    List<Dlt> selectBlueByNumber(String number);
}
