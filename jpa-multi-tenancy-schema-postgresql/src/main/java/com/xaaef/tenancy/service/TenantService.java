package com.xaaef.tenancy.service;

import com.xaaef.tenancy.entity.Tenant;

import java.util.List;

public interface TenantService {


    Tenant save(Tenant entity);


    List<Tenant> findAll();


}
