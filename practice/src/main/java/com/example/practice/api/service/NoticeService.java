package com.example.practice.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.practice.api.annotation.LogTargetAnno;
import com.example.practice.api.dto.Comment;
import com.example.practice.api.dto.Notice;
import com.example.practice.api.repository.CommentRepository;
import com.example.practice.api.repository.NoticeRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class NoticeService {
	@Autowired
	private NoticeRepository noticeRepository;
	@Autowired
	private CommentRepository commentRepository;
	
	public Flux<Notice> getNoticeList() {
		return noticeRepository.findAll();
	}
	
	@LogTargetAnno
	public Mono<Notice> getNotice(int noitceSeq) {
		return noticeRepository.findByNoticeSeq(noitceSeq);
	}
	
	public Flux<Notice> getNoticeCommentList() {
		return noticeRepository.findAll().cache().flatMap(notice ->
				Mono.just(notice)
				.zipWith(commentRepository.findByNoticeSeq(notice.getNoticeSeq()).cache().collectList())
				.map(assemble -> assemble.getT1().withCommentList(assemble.getT2()))
				);
	}
		
	public Mono<Notice> getNoticeCommentListSingle(int noitceSeq) {		
		Mono<Notice> getNotice = noticeRepository.findByNoticeSeq(noitceSeq).cache();
		Flux<Comment> commentList = commentRepository.findByNoticeSeq(noitceSeq).cache();
		
		return getNotice.zipWith(commentList.collectList())
				.map(assemble -> assemble.getT1().withCommentList(assemble.getT2()));		
	}
}
