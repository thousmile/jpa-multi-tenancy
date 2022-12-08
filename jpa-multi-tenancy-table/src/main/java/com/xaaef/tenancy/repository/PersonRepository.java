package com.xaaef.tenancy.repository;

import com.xaaef.tenancy.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("""
            select p from Person as p where p.age > ?1
            """)
    List<Person> findGtAge(Integer age);

}
