package com.example.repository;

import com.example.domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryImplTest {
    PersonRepositoryImpl repository;

    @BeforeEach
    void setup() {
        repository = new PersonRepositoryImpl();
    }

    @Test
    void getPersonById() {
        Mono<Person> personMono = repository.getPersonById(2);
        // not to do in code usually
        Person person = personMono.block();
        System.out.println(person.toString());
    }

    @Test
    void getPersonByIdNotFound() {
        Mono<Person> personMono = repository.getPersonById(8);
        // not to do in code usually
        Person person = personMono.block();
        System.out.println(person.toString());
    }

    @Test
    void getPersonByIdSubscribe() {
        Mono<Person> personMono = repository.getPersonById(4);
        // not blocking approach
        personMono.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    @Test
    void getPersonByIdMapFunction() {
        Mono<Person> personMono = repository.getPersonById(1);
        // not blocking approach
        personMono.map(person -> person.getFirstName())
                .subscribe(firstName -> System.out.println(firstName));
    }

    @Test
    void getPersonByIdMapFunctionMethodReference() {
        Mono<Person> personMono = repository.getPersonById(1);
        // not blocking approach
        personMono.map(Person::getFirstName)
                .subscribe(System.out::println);
    }

    @Test
    void findAllBlockFirst() {
        Flux<Person> personFlux = repository.findAll();
        Person person = personFlux.blockFirst();
        System.out.println("Block First " + person.toString());
    }

    @Test
    void findAllBlockLast() {
        Flux<Person> personFlux = repository.findAll();
        Person person = personFlux.blockLast();
        System.out.println("Block last " + person.toString());
    }

    @Test
    void findAllSubscribe() {
        Flux<Person> personFlux = repository.findAll();
        personFlux.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    @Test
    void findAllConvertToList() {
        Flux<Person> personFlux = repository.findAll();
        Mono<List<Person>> personListMono = personFlux.collectList();
        personListMono.subscribe(list -> {
            list.forEach(person -> System.out.println(person.toString()));
        });
    }

    @Test
    void findAllConvertToListReferenceMethod() {
        Flux<Person> personFlux = repository.findAll();
        personFlux.collectList().subscribe(list -> list.forEach(System.out::println));
    }

    // using filters
    @Test
    void findPersonByIdFromFlux() {
        final Integer id = 3;
        Flux<Person> personFlux = repository.findAll();
        Mono<Person> personMono = personFlux
                .filter(person -> person.getId().equals(id))
                .next();

        personMono.subscribe(person -> System.out.println(person));
    }

    @Test
    void findPersonByIdFromFluxMethodReference() {
        final Integer id = 3;
        // one line version
        repository.findAll()
                .filter(person -> person.getId().equals(id))
                .next()
                .subscribe(System.out::println);
    }


    // we expect an error ...
    @Test
    void findPersonByIdFromFluxMethodReferenceNotFound() {
        final Integer id = 8;
        // one line version
        repository.findAll()
                .filter(person -> person.getId().equals(id))
                .next()
                .subscribe(System.out::println);
    }


    // we throw exception ...
    @Test
    void findPersonByIdFromFluxMethodReferenceNotFoundErrorHandled() {
        final Integer id = 8;
        // one line version
        repository.findAll()
                .filter(person -> person.getId().equals(id))
                .single()
                .doOnError(throwable -> System.out.println("We didn't find with id " + id + " Exception is " + throwable.getMessage()))
                .onErrorReturn(Person.builder().id(id).firstName("John").lastName("Doe").build())
                .subscribe(System.out::println);
    }
}