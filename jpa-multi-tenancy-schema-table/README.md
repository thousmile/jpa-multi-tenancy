# spring boot jpa 多租户 基于 数据表（Table）

hibernate 6.x 多租户注解 @TenantId 。 spring boot 3.x 才支持 hibernate 6.x

启动流程

````

一.创建一个 `molly_master` 的 数据库。


二.修改 `application-dev.yml` 中的数据库连接池地址


三. "REST API" 目录中 Test API.http 测试 api 接口

````