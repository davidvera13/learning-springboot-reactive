package com.example.repository;

import com.example.domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

class PersonRepositoryImplStepVerifierTest {
    PersonRepositoryImpl repository;

    @BeforeEach
    void setup() {
        repository = new PersonRepositoryImpl();
    }
    
    @Test
    void getPersonByIdSubscribe() {
        Mono<Person> personMono = repository.getPersonById(4);
        StepVerifier.create(personMono)
                .expectNextCount(1)
                .verifyComplete();
        personMono.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    @Test
    void getPersonByIdSubscribeFailedVerifier() {
        Mono<Person> personMono = repository.getPersonById(4);
        StepVerifier.create(personMono)
                .expectNextCount(2)
                .verifyComplete();
        personMono.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    @Test
    void findAllSubscribe() {
        Flux<Person> personFlux = repository.findAll();

        StepVerifier.create(personFlux).expectNextCount(4).verifyComplete();
        personFlux.subscribe(person -> {
            System.out.println(person.toString());
        });
    }
}