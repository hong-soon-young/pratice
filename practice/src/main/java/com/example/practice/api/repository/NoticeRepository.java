package com.example.practice.api.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.practice.api.dto.Notice;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface NoticeRepository extends ReactiveCrudRepository<Notice , Integer> {
	public Flux<Notice> findAll();
	public Mono<Notice> findByNoticeSeq(Integer noticeSeq);
}
