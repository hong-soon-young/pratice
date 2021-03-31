package com.example.practice.api.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.practice.api.dto.OmOd;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface OmOdRepository extends ReactiveCrudRepository<OmOd , String> {
	
	 public Flux<OmOd> findAll();
	 public Mono<OmOd> findByOdNo(String odNo);
	 public Mono<OmOd> save(OmOd omOd);
}
