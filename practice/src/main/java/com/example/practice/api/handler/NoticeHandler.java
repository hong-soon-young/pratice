package com.example.practice.api.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.practice.api.dto.Comment;
import com.example.practice.api.dto.Notice;
import com.example.practice.api.repository.CommentRepository;
import com.example.practice.api.repository.NoticeRepository;
import com.example.practice.api.service.NoticeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
public class NoticeHandler {
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private NoticeRepository noticeRepository;
	@Autowired
	private CommentRepository commentRepository;

	public Mono<ServerResponse> getNoticeList(ServerRequest request) {
		Flux<Notice> noticeList = noticeService.getNoticeList();

		return ServerResponse.ok().body(noticeList, Notice.class)
				.onErrorResume((e) -> ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> getNotice(ServerRequest request) {
		Mono<Notice> notice = noticeService.getNotice(Integer.parseInt(request.pathVariable("noticeSeq")));
		return ServerResponse.ok().body(notice, Notice.class);
	}

	public Mono<ServerResponse> getNoticeCommentList(ServerRequest request) {
		Flux<Notice> notice = noticeService.getNoticeCommentList();
		return ServerResponse.ok().body(notice, Notice.class);
	}

	public Mono<ServerResponse> getNoticeCommentListHandle(ServerRequest request) {
		Flux<Notice> noticeFlux = noticeRepository.findAll().cache().publishOn(Schedulers.parallel());
		Flux<List<Comment>> commentListFlux = noticeFlux.map(Notice::getNoticeSeq)
				.flatMap(noticeSeq -> commentRepository.findByNoticeSeq(noticeSeq).collectList());
		
		return Flux.zip(noticeFlux, commentListFlux, Notice::withCommentList).collectList().flatMap(ServerResponse.ok()::bodyValue);
	}

	public Mono<ServerResponse> getNoticeCommentListSingle(ServerRequest request) {
		Mono<Notice> notice = noticeService
				.getNoticeCommentListSingle(Integer.parseInt(request.pathVariable("noticeSeq")));
		return ServerResponse.ok().body(notice, Notice.class);
	}
}
