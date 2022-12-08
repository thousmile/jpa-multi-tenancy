package com.xaaef.tenancy.service.impl;


import com.xaaef.tenancy.entity.Person;
import com.xaaef.tenancy.repository.PersonRepository;
import com.xaaef.tenancy.service.PersonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personReps;

    @Override
    public Person save(Person entity) {
        return personReps.save(entity);
    }


    @Override
    public List<Person> findAll() {
        return personReps.findAll();
    }


}
