package com.example.practice.api.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.DelegatingWebFluxConfiguration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.practice.api.handler.OrderHandler;

@Configuration
public class OrderRouter extends DelegatingWebFluxConfiguration {
	  @Bean
	  public RouterFunction<ServerResponse> orderRouteRead(OrderHandler orderHandler) {
		  return RouterFunctions.route().nest(RequestPredicates.path("/api/orderRouter"),
					builder -> builder.GET("/getAllOrderList", orderHandler::getAllOrderList)
					.GET("/getOrderListOne/{odNo}", orderHandler::getOrderListOne)
					.GET("/getOrderListOneMono/{odNo}/{odTypCd}", orderHandler::getOrderListOneMono)
					.POST("/registOrder", orderHandler::registOrder)
					.POST("/cancelOrder", orderHandler::cancelOrder)
					.GET("/getOrderListClient/{odNo}/{odTypCd}/{dcTnnoCd}", orderHandler::getOrderListClient)
				  ).build();
	  }
}

