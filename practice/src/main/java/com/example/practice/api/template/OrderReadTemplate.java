package com.example.practice.api.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.practice.api.dto.OmOd;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class OrderReadTemplate {
	@Autowired
	private R2dbcEntityTemplate r2dbcEntityTemplate;
	
	public Flux<OmOd> getOmOdList() {
		return r2dbcEntityTemplate
				.select(OmOd.class)
				.all();
	}
	
	public Mono<OmOd> getOmOd(String odNo) {
		return r2dbcEntityTemplate
				.select(OmOd.class)
				.matching(Query.query(Criteria.where("odNo").like(odNo)))
				.one();
	}
}
