package com.example.practice.api.config;



import java.time.LocalDateTime;

import org.reactivestreams.Publisher;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.stereotype.Component;

import com.example.practice.api.dto.OmOd;

import reactor.core.publisher.Mono;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DefaultingConverCallback<T> implements BeforeConvertCallback<OmOd> {

	@Override
	public Publisher<OmOd> onBeforeConvert(OmOd entity, SqlIdentifier table) {
		entity.setModDttm(LocalDateTime.now());
		
		entity.getOmOdDtlList().stream().forEach(omOdDtl -> {
			omOdDtl.setModDttm(LocalDateTime.now());
		});
		
		entity.getOmOdFvrDtlList().stream().forEach(omOdFvrDtl -> {
			omOdFvrDtl.setModDttm(LocalDateTime.now());
		});
		
		return Mono.just(entity);
	}
}
