package com.xaaef.tenancy.entity;

import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.TenantId;

import java.util.Set;


@Entity
@Table(name = "pms_person")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer age;

    /**
     * hibernate 6.x 多租户注解
     *
     * @author WangChenChen
     * @date 2022/12/8 13:57
     */
    @TenantId
    private String tenantId;

    @ElementCollection
    private Set<Integer> grade;

    @ElementCollection
    private Set<String> hobby;

}
