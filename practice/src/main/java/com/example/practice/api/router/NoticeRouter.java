package com.example.practice.api.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.DelegatingWebFluxConfiguration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.practice.api.handler.NoticeHandler;

@Configuration
public class NoticeRouter extends DelegatingWebFluxConfiguration {
	  @Bean
	  public RouterFunction<ServerResponse> noticeRoute(NoticeHandler noticeHandler) {
		  return RouterFunctions.route().nest(RequestPredicates.path("/api/noticeRouter"),
					builder -> builder.GET("/getNoticeList", noticeHandler::getNoticeList)
					.GET("/getNotice/{noticeSeq}", noticeHandler::getNotice)
					.GET("/getNoticeCommentList", noticeHandler::getNoticeCommentList)
					.GET("/getNoticeCommentListHandle", noticeHandler::getNoticeCommentListHandle)
					.GET("/getNoticeCommentListSingle/{noticeSeq}", noticeHandler::getNoticeCommentListSingle)
				  ).build();
	  }
}

