package cn.lottery.lottery.aspect;

import cn.lottery.lottery.annotation.WithDataSource;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
// 指定优先级高于@Transactional的默认优先级
// 从而保证先切换数据源再进行事务操作
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class DataSourceAspect {

    @Around("@annotation(withDataSource)")
    public Object switchDataSource(ProceedingJoinPoint pjp, WithDataSource withDataSource) throws Throwable {

        // 1.获取 @WithDataSource 注解中指定的数据源
        String routingKey = withDataSource.value();
        // 2.设置数据源上下文
        DynamicDataSourceContextHolder.push(routingKey);
        // 3.使用设定好的数据源处理业务
        try {
            return pjp.proceed();
        } finally {
            // 4.清空数据源上下文
            DynamicDataSourceContextHolder.clear();
        }
    }
}
