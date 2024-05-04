package cn.lottery.lottery.mapper;

import cn.lottery.lottery.entity.Ssq;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SsqMapper extends BaseMapper<Ssq> {
    List<Ssq> selectRedByNumber(String number);

    List<Ssq> selectBlueByNumber(String number);
}
