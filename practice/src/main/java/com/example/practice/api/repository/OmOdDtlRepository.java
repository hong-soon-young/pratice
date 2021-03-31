package com.example.practice.api.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.practice.api.dto.OmOdDtl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface OmOdDtlRepository extends ReactiveCrudRepository<OmOdDtl , String> {
	
	 public Flux<OmOdDtl> findAll(); 
	 public Flux<OmOdDtl> findByOdNo(String odNo);
	 public Flux<OmOdDtl> findByOdNoAndOdSeqAndProcSeq(String odNo, int odSeq, int procSeq);
	 public Flux<OmOdDtl> findByOdNoAndOdTypCd(String odNo, String odTypCd);
	 
	 public Mono<OmOdDtl> save(OmOdDtl omOdDtl);
}
