package com.xaaef.tenancy;

import com.xaaef.tenancy.entity.Person;
import com.xaaef.tenancy.entity.Tenant;
import com.xaaef.tenancy.repository.PersonRepository;
import com.xaaef.tenancy.repository.TenantRepository;
import com.xaaef.tenancy.util.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@SpringBootTest
class JpaMultiTenancyTests {

    @Autowired
    private TenantRepository tenantReps;

    @Autowired
    private PersonRepository personReps;

    @Test
    public void test1() {
        var tenants = tenantReps.saveAll(
                List.of(
                        Tenant.builder().tenantId("huawei").name("华为").createTime(LocalDateTime.now()).build(),
                        Tenant.builder().tenantId("microsoft").name("微软").createTime(LocalDateTime.now()).build(),
                        Tenant.builder().tenantId("pingan").name("平安").createTime(LocalDateTime.now()).build()
                )
        );
        System.out.println(JsonUtils.toFormatJson(tenants));

    }


    @Test
    public void test2() {
        var gtAge = personReps.findGtAge(66);
        System.out.println(JsonUtils.toFormatJson(gtAge));
    }


    @Test
    public void test3() {
        var matcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatcher::exact);
        var example = Example.of(
                Tenant.builder()
                        .tenantId("microsoft")
                        .name("华为")
                        .build(),
                matcher);
        List<Tenant> all = tenantReps.findAll(example);
    }




}
