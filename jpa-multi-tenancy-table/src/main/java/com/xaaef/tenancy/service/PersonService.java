package com.xaaef.tenancy.service;

import com.xaaef.tenancy.entity.Person;

import java.util.List;

public interface PersonService {

    Person save(Person entity);

    List<Person> findAll();

}
