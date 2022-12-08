package com.xaaef.tenancy.service.impl;

import com.xaaef.tenancy.config.CustomDataSources;
import com.xaaef.tenancy.entity.Tenant;
import com.xaaef.tenancy.repository.TenantRepository;
import com.xaaef.tenancy.service.TenantService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantReps;

    private final CustomDataSources customDataSources;


    @Override
    public Tenant save(Tenant entity) {
        if (tenantReps.existsById(entity.getTenantId())) {
            var err = String.format("租户ID %s 已经存在了！", entity.getTenantId());
            throw new RuntimeException(err);
        }
        var result = tenantReps.save(entity);
        // 租户创建表结构
        customDataSources.createTable(entity.getTenantId());
        return result;
    }


    @Override
    public List<Tenant> findAll() {
        return tenantReps.findAll();
    }


}
