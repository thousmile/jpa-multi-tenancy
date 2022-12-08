package com.xaaef.tenancy.config;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.xaaef.tenancy.util.TenantUtils.DEFAULT_TENANT_ID;


@Slf4j
@Component
@AllArgsConstructor
public class CustomDataSources {

    public final static String TENANT_ID_PREFIX = "molly_";

    // 默认租户的数据源
    private final DataSource dataSource;

    private final LiquibaseProperties liquibaseProperties;


    private final DataSourceProperties dataSourceProperties;


    /**
     * 创建新的 jdbc 连接。 用于生成表结构。用完就关闭
     *
     * @return
     * @author WangChenChen
     * @date 2022/12/8 12:44
     */
    private Connection getConnection(String tenantDbName) throws Exception {
        // 获取默认的数据名称
        var oldDbName = getOldDbName(dataSourceProperties.getUrl());
        // 替换连接池中的数据库名称
        var dataSourceUrl = dataSourceProperties.getUrl().replaceFirst(oldDbName, tenantDbName);
        //3.获取数据库连接对象
        return DriverManager.getConnection(dataSourceUrl,
                dataSourceProperties.getUsername(),
                dataSourceProperties.getPassword());
    }


    public static String getOldDbName(String url) {
        var startInx = url.lastIndexOf("?");
        var sub = url.substring(0, startInx);
        int startInx2 = sub.lastIndexOf("/") + 1;
        return sub.substring(startInx2);
    }


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
            // 判断数据库是否存在！不存在就创建
            String tenantDbName = TENANT_ID_PREFIX + tenantId;
            String sql = String.format("CREATE DATABASE IF NOT EXISTS %s CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;", tenantDbName);
            conn.createStatement().execute(sql);

            // 创建一次性的 jdbc 链接。只是用来生成表结构的。用完就关闭。
            var conn1 = new JdbcConnection(getConnection(tenantDbName));
            // 使用 Liquibase 创建表结构
            var liquibase = new Liquibase(liquibaseProperties.getChangeLog(), new ClassLoaderResourceAccessor(), conn1);
            liquibase.update(tenantId);
            // 关闭链接
            conn1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
