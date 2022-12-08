package com.xaaef.tenancy.config;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.xaaef.tenancy.util.TenantUtils.DEFAULT_TENANT_ID;


@Slf4j
@Component
@AllArgsConstructor
public class CustomDataSources {

    // 数据库 前缀。 易于区分数据库名称
    private final static String TENANT_ID_PREFIX = "molly_";

    private static final Map<String, DataSource> DATA_SOURCE_MAP = new ConcurrentHashMap<>();

    private final JdbcTemplate jdbcTemplate;

    // 默认租户的数据源
    private final DataSource masterDataSource;

    // 数据源配置
    private final DataSourceProperties dataSourceProperties;

    private final LiquibaseProperties liquibaseProperties;

    /**
     * 设置默认的数据源
     *
     * @author WangChenChen
     * @date 2022/12/7 21:05
     */
    @PostConstruct
    public void initDataSources() {
        // 设置默认的数据库名称
        DATA_SOURCE_MAP.put(DEFAULT_TENANT_ID, masterDataSource);
    }


    /**
     * 获取默认的数据源
     *
     * @author WangChenChen
     * @date 2022/12/7 21:05
     */
    public DataSource getMasterDataSource() {
        return masterDataSource;
    }


    /**
     * TODD 根据租户ID , 获取数据源
     *
     * @author WangChenChen
     * @date 2022/12/7 21:05
     */
    public DataSource getDataSource(String tenantId) {
        if (!DATA_SOURCE_MAP.containsKey(tenantId)) {
            var datasource = createDatasource(tenantId);
            if (datasource == null) {
                return getMasterDataSource();
            }
            DATA_SOURCE_MAP.put(tenantId, datasource);
        }
        return DATA_SOURCE_MAP.get(tenantId);
    }


    public static String getOldDbName(String url) {
        var startInx = url.lastIndexOf("?");
        var sub = url.substring(0, startInx);
        int startInx2 = sub.lastIndexOf("/") + 1;
        return sub.substring(startInx2);
    }


    /**
     * 根据数据库名称，创建数据源
     *
     * @author WangChenChen
     * @date 2022/12/7 21:05
     */
    public DataSource createDatasource(String dbName) {
        // 判断数据库是否存在！不存在就创建
        String tenantDbName = TENANT_ID_PREFIX + dbName;
        String sql = String.format("CREATE DATABASE IF NOT EXISTS %s CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;", tenantDbName);
        jdbcTemplate.execute(sql);
        // 获取默认的数据名称
        var oldDbName = getOldDbName(dataSourceProperties.getUrl());
        // 替换连接池中的数据库名称
        var dataSourceUrl = dataSourceProperties.getUrl().replaceFirst(oldDbName, tenantDbName);
        log.info("createDatasource Url: {} ", dataSourceUrl);
        dataSourceProperties.setUrl(dataSourceUrl);
        dataSourceProperties.setName(String.format("HikariPool[%s]", dbName));
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }


    /**
     * 创建表
     *
     * @author WangChenChen
     * @date 2022/12/7 21:05
     */
    public void createTable(String tenantId) {
        log.info("tenantId: {} create table ...", tenantId);
        var dataSource = getDataSource(tenantId);
        try {
            var conn = new JdbcConnection(dataSource.getConnection());
            var liquibase = new Liquibase(liquibaseProperties.getChangeLog(), new ClassLoaderResourceAccessor(), conn);
            liquibase.update(tenantId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
