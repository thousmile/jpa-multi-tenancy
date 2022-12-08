package com.xaaef.tenancy.runner;

import com.xaaef.tenancy.config.CustomDataSources;
import com.xaaef.tenancy.entity.Tenant;
import com.xaaef.tenancy.repository.TenantRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.xaaef.tenancy.util.TenantUtils.DEFAULT_TENANT_ID;

/**
 * TODO 初始化 租户的表结构
 *
 * @author WangChenChen
 * @date 2022/12/7 21:20
 */


@Slf4j
@Component
@AllArgsConstructor
public class TenantTableRunner implements ApplicationRunner {

    private final TenantRepository tenantReps;

    private final CustomDataSources customDataSources;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var all = tenantReps.findAll();
        if (!all.isEmpty()) {
            all.stream().filter(r -> !StringUtils.equals(r.getTenantId(), DEFAULT_TENANT_ID))
                    .forEach(tenant -> customDataSources.createTable(tenant.getTenantId()));
        }
    }


}
