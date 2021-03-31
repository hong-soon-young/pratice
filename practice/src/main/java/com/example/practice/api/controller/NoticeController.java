package com.example.practice.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.practice.api.annotation.UserSettingTargetAnno;
import com.example.practice.api.dto.Notice;
import com.example.practice.api.dto.User;
import com.example.practice.api.service.NoticeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/notice/")
public class NoticeController {
	@Autowired
	private NoticeService noticeService;
	
	@GetMapping(value="getNoticeList")
	public Flux<Notice> getNoticeList(@UserSettingTargetAnno User user) {
		return noticeService.getNoticeList();
	}
	
	@GetMapping(value="getNotice/{noticeSeq}")
	public Mono<Notice> getNotice(@PathVariable int noticeSeq) {
		return noticeService.getNotice(noticeSeq);
	}
	
	@GetMapping(value="getNoticeList1")
	public Flux<Notice> getNoticeList1() {
		return null;
	}
}
