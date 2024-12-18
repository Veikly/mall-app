package com.cdu.config;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
/**
 * @ProjectName: cdu-app
 * @Titile: MyBatisConfig
 * @Author: Administrator
 * @Description: mybatis的配置类
 */
@Configuration
@MapperScan("com.cdu.mapper") //扫描指定的包，目的是为包下的接口生成代理类
public class MyBatisConfig {
}
