package com.xaaef.tenancy.config;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@Slf4j
@Component
@AllArgsConstructor
public class CustomDataSources {

    // 默认租户的数据源
    private final DataSource dataSource;

    private final LiquibaseProperties liquibaseProperties;

    /**
     * 创建表
     *
     * @author WangChenChen
     * @date 2022/12/7 21:05
     */
    public void createTable(String tenantId) {
        log.info("tenantId: {} create table ...", tenantId);
        try {
            // 判断 schema 是否存在。不存在就创建
            var conn = dataSource.getConnection();
            var sql = String.format("create schema if not exists %s;", tenantId);
            conn.createStatement().execute(sql);

            // 设置默认的 schema
            conn.setSchema(tenantId);

            // 使用 Liquibase 创建表结构
            var conn1 = new JdbcConnection(conn);
            var liquibase = new Liquibase(liquibaseProperties.getChangeLog(), new ClassLoaderResourceAccessor(), conn1);
            liquibase.update(tenantId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
