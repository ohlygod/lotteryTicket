package cn.lottery.lottery.service;

import cn.lottery.lottery.annotation.WithDataSource;
import cn.lottery.lottery.entity.SsqExclude;
import cn.lottery.lottery.mapper.SsqExcludeMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SsqExcludeService extends ServiceImpl<SsqExcludeMapper, SsqExclude> {

}
