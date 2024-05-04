package cn.lottery.lottery.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@Data
public class DriverPathConfig {

    @Value(value="${webdriver.path:}")
    private String driverPath;

    @PostConstruct
    public void init(){
        System.setProperty("webdriver.chrome.driver", driverPath);
    }
}
