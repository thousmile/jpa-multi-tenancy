package com.xaaef.tenancy.config;

import com.xaaef.tenancy.util.TenantUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.hibernate.cfg.AvailableSettings.MULTI_TENANT;
import static org.hibernate.cfg.AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER;


/**
 * <p>
 * </p>
 *
 * @author WangChenChen
 * @version 1.1
 * @date 2022/11/25 10:58
 */


@Slf4j
@Component
@AllArgsConstructor
public class CustomTenantResolver implements CurrentTenantIdentifierResolver,
        HibernatePropertiesCustomizer {


    @Override
    public String resolveCurrentTenantIdentifier() {
        return TenantUtils.getTenantId();
    }


    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }


    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(MULTI_TENANT_IDENTIFIER_RESOLVER, this);
    }


}
