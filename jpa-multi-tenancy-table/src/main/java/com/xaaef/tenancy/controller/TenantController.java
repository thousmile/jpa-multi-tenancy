package com.xaaef.tenancy.controller;

import com.xaaef.tenancy.entity.Tenant;
import com.xaaef.tenancy.repository.TenantRepository;
import com.xaaef.tenancy.service.TenantService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/tenant")
public class TenantController {

    private final TenantService tenantService;


    @PostMapping
    public Tenant save(@RequestBody Tenant tenant) {
        if (StringUtils.isBlank(tenant.getTenantId())) {
            throw new RuntimeException("租户ID 必须填写！");
        }
        if (tenant.getCreateTime() == null) {
            tenant.setCreateTime(LocalDateTime.now());
        }
        return tenantService.save(tenant);
    }


    @GetMapping
    private List<Tenant> all() {
        return tenantService.findAll();
    }


}
