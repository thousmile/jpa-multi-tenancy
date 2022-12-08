# spring boot jpa 多租户 基于 模式（Schema）

## 创建表结构使用的是 liquibase ，创建租户时，自动生成 表结构

因为 mysql 数据库不支持 schema 所以只能使用 数据库 代替

启动流程

````

一.创建一个 `molly_master` 的 数据库。
    这是默认的 数据库 如果，想修改名称。需要修改两个地方
    1. `CustomDataSources` 类中的 TENANT_ID_PREFIX 常量。这是默认生成数据库的前缀名称。用于区分项目
    2. `TenantUtils` 类中的 DEFAULT_TENANT_ID 常量。这是默认租户的数据库


二.修改 `application-dev.yml` 中的数据库连接池地址


三. "REST API" 目录中 Test API.http 测试 api 接口

````