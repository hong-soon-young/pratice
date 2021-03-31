package com.example.practice.api.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.practice.api.dto.OmOd;
import com.example.practice.api.service.OrderService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class OrderHandler {
	@Autowired
	private OrderService orderService;

	public Mono<ServerResponse> getAllOrderList(ServerRequest request) {
		Flux<OmOd> getAllOrderList = orderService.getAllOrderList();

		return ServerResponse.ok().body(getAllOrderList, OmOd.class)
				.onErrorResume((e) -> ServerResponse.notFound().build());		
	}

	public Mono<ServerResponse> getOrderListOne(ServerRequest request) {
		Mono<OmOd> getOrderListOne = orderService.getOrderListOne(request.pathVariable("odNo"));
		return ServerResponse.ok().body(getOrderListOne, OmOd.class);
	}

	public Mono<ServerResponse> getOrderListOneMono(ServerRequest request) {
		Mono<OmOd> getOrderListOne = orderService.getOrderListOne(request.pathVariable("odNo"));
		return ServerResponse.ok().body(getOrderListOne, OmOd.class);
	}
	
	public Mono<ServerResponse> registOrder(ServerRequest request) {
		OmOd omod = new OmOd().setOmOd();
		Mono<OmOd> registOrder = orderService.registOrder(omod);
		return ServerResponse.ok().body(registOrder, OmOd.class);
	}
	
	public Mono<ServerResponse> cancelOrder(ServerRequest request) {
		OmOd omod = new OmOd().setOmOd();
		omod.setOdNo("20210330936569");
		
		Mono<OmOd> cancelOrder = orderService.cancelOrder(omod);
		return ServerResponse.ok().body(cancelOrder, OmOd.class);
	}

}
