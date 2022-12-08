package com.xaaef.tenancy.repository;

import com.xaaef.tenancy.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TenantRepository extends JpaRepository<Tenant, String> {


}
