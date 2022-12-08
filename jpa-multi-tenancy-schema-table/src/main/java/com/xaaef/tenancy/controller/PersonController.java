package com.xaaef.tenancy.controller;

import com.xaaef.tenancy.entity.Person;
import com.xaaef.tenancy.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/people")
public class PersonController {

    private final PersonService personService;

    @PostMapping
    public Person save(@RequestBody Person person) {
        return personService.save(person);
    }


    @GetMapping
    private List<Person> all() {
        return personService.findAll();
    }


}
