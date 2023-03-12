package com.example.repository;

import com.example.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface PersonRepository {
    Mono<Person> getPersonById(Integer id);
    Flux<Person> findAll();
}
