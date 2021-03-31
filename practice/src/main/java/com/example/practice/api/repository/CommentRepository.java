package com.example.practice.api.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.practice.api.dto.Comment;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CommentRepository extends ReactiveCrudRepository<Comment , Integer> {
	public Flux<Comment> findAll();
	public Mono<Comment> findByCommentSeq(Integer commentSeq);
	public Flux<Comment> findByNoticeSeq(Integer noticeSeq);	
}
