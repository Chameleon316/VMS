package org.lw.vms.config;

/*
  @version 1.0
 * @auther Yongqi Wang
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Mybatis 和 Spring 安全相关的配置类。
 */
@Configuration // 标识这是一个 Spring 配置类
@MapperScan("org.lw.vms.mapper") // 扫描指定包下的 Mybatis Mapper 接口，使其能够被 Spring 管理
@EnableTransactionManagement // 启用 Spring 的声明式事务管理，使得 @Transactional 注解生效
public class MybatisConfig {
}
