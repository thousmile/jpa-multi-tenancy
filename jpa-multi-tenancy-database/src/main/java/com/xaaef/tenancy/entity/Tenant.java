package com.xaaef.tenancy.entity;

import javax.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tenant implements java.io.Serializable {

    @Id
    private String tenantId;

    private String name;

    private LocalDateTime createTime;

}
