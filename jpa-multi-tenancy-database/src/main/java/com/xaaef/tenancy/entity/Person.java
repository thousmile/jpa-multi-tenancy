package com.xaaef.tenancy.entity;

import javax.persistence.*;
import lombok.*;

import java.util.Map;
import java.util.Set;


@Entity
@Table
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

    @ElementCollection
    private Set<Integer> grade;

    @ElementCollection
    private Set<String> hobby;

}
