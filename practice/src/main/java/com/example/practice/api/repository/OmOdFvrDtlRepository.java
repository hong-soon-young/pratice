package com.example.practice.api.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.practice.api.dto.OmOdFvrDtl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface OmOdFvrDtlRepository extends ReactiveCrudRepository<OmOdFvrDtl , Integer> {
	 public Flux<OmOdFvrDtl> findAll();
	 public Flux<OmOdFvrDtl> findByOdNo(String odNo);
	 public Flux<OmOdFvrDtl> findByOdFvrNo(String odFvrNo);
	 public Flux<OmOdFvrDtl> findByOdNoAndOdSeqAndProcSeq(String odNo , int odSeq, int procSeq);
	 
	 public Mono<OmOdFvrDtl> save(OmOdFvrDtl omOdFvrDtl);
}
