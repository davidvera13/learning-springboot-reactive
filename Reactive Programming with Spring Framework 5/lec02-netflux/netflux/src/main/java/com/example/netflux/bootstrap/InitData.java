package com.example.netflux.bootstrap;

import com.example.netflux.domain.Movie;
import com.example.netflux.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class InitData implements CommandLineRunner {
    private final MovieRepository movieRepository;

    @Autowired
    public InitData(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        movieRepository.deleteAll()
                .thenMany(
                        Flux.just(
                                "Silence of the lambdas",
                                "Aeon Flux",
                                "Enter the mono<Void>",
                                "The fluxxinator",
                                "Back to the future",
                                "Meet the fluxes",
                                "Lord of the fluxes")
                                .map(Movie::new) // new Movie(title)
                                .flatMap(movieRepository::save))
                .subscribe(null, null, () -> movieRepository.findAll().subscribe(System.out::println));
    }
}
