package com.example.practice.api.config;



import org.reactivestreams.Publisher;
import org.springframework.core.annotation.Order;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.r2dbc.mapping.event.BeforeSaveCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.stereotype.Component;

import com.example.practice.api.dto.OmOd;

import reactor.core.publisher.Mono;

//@Component
//@Order(-1)
public class DefaultingEntityCallback<T> implements BeforeSaveCallback<OmOd> {

	@Override
	public Publisher<OmOd> onBeforeSave(OmOd entity, OutboundRow row, SqlIdentifier table) {
		//entity.setRegist(true);

//		entity.getOmOdDtlList().stream().forEach(omOdDtl -> {
//			omOdDtl.setRegist(true);
//		});
		
		return Mono.just(entity);
	}
	
}
