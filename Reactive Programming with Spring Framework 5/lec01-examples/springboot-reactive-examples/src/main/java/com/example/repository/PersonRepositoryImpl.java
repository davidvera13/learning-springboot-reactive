package com.example.repository;

import com.example.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class PersonRepositoryImpl implements PersonRepository{

    Person john = new Person(1, "John", "Wick");
    Person fiona = new Person(2, "Fiona", "Shrek");
    Person fox = new Person(3, "Fox", "Mulder");
    Person dana = new Person(4, "Dana", "Scully");

    @Override
    public Mono<Person> getPersonById(Integer id) {
        // return Mono.from(findAll().filter(person -> person.getId().equals(id)));
         return findAll().filter(person -> person.getId().equals(id)).next();
//        return findAll()
//                .filter(person -> person.getId().equals(id))
//                .single()
//                .doOnError(throwable -> System.out.println("We didn't find with id " + id + " Exception is " + throwable.getMessage()))
//                .onErrorReturn(Person.builder().id(id).firstName("John").lastName("Doe").build());
    }

    @Override
    public Flux<Person> findAll() {
        return Flux.just(john, fiona, fox, dana);
    }
}
