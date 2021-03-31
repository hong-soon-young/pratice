package com.example.practice.api.config;



import java.time.LocalDateTime;

import org.reactivestreams.Publisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.r2dbc.mapping.event.BeforeSaveCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;

import com.example.practice.api.dto.OmOd;

import reactor.core.publisher.Mono;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DefaultingEntityCallback<T> implements BeforeSaveCallback<OmOd> {

	@Override
	public Publisher<OmOd> onBeforeSave(OmOd entity, OutboundRow row, SqlIdentifier table) {
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
