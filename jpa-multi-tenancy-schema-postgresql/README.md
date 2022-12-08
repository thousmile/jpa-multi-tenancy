# spring boot jpa 多租户 基于 模式（Schema）

## 创建表结构使用的是 liquibase ，创建租户时，自动生成 表结构

因为 mysql 数据库不支持 schema 所以只能使用 PostgreSQL 代替

启动流程

````

一.创建一个 `molly_master` 的 数据库。然后创建 `master` 的 schema 
    这是默认的 schema 如果，想修改名称。需要修改两个地方
    1. `application.yml`  spring.liquibase.default-schema 属性。这是默认生成表结构的 schema
    2. `TenantUtils` 类中的 DEFAULT_TENANT_ID 常量


二.修改 `application-dev.yml` 中的数据库连接池地址


三. "REST API" 目录中 Test API.http 测试 api 接口

````