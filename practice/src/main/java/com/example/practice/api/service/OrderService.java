package com.example.practice.api.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.practice.api.component.OrderCommon;
import com.example.practice.api.dto.OmOd;
import com.example.practice.api.dto.OmOdDtl;
import com.example.practice.api.dto.OmOdDtlFvrDtlRequest;
import com.example.practice.api.dto.OmOdDtlFvrDtlResponse;
import com.example.practice.api.dto.OmOdFvrDtl;
import com.example.practice.api.repository.OrderRepositoryDataBaseClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class OrderService {	
	@Autowired
	private OrderCommon orderCommon;
	@Autowired
	private OrderRepositoryDataBaseClient orderRepositoryDataBaseClient;
	
	//주문 전체 조회
	public Flux<OmOd> getAllOrderList() {
		return getAllOrderListMain();
	}	
	
	//주문 단건 조회
	public Mono<OmOd> getOrderListOne(String odNo) {
		return orderCommon.getOmOd(odNo)
								   .flatMap(this::getOrderDetailListByOmOdMono)
								   .flatMap(this::getOrderFavorDetailListByOmOdMono);
	}
	
	//주문 생성
	@Transactional(propagation = Propagation.REQUIRED , rollbackFor = Exception.class)
	public Mono<OmOd> registOrder(OmOd omOd) {
		return registOmOd(omOd)
				.flatMap(this::registOmOdDtl)
				.flatMap(this::registOmOdFvrDtl);
	}
		
	//주문취소
	@Transactional(propagation = Propagation.REQUIRED , rollbackFor = Exception.class)
	public Mono<OmOd> cancelOrder(OmOd omOd) {
		return cancelOmOd(omOd)
				.flatMap(this::cancelOmOdDtl)
				.flatMap(this::cancelOmOdFvrDtl);
	}
	
	//datebaseclient 조회
	public Flux<OmOd> getOrderListClient(OmOdDtlFvrDtlRequest omOdDtlFvrDtlRequest) {
		Flux<OmOdDtlFvrDtlResponse> omOdDtlFvrDtlResponse = orderRepositoryDataBaseClient.findOmOdDtlFvrDtl(omOdDtlFvrDtlRequest);
		return getOmOdCertainClientQuery(omOdDtlFvrDtlResponse);
	}
	
	public Flux<OmOd> getAllOrderListMain() {
		Flux<OmOd> orderDetailListByOmOdFlux = getOrderDetailListByOmOdFlux();		
		return getOrderFavorDetailListByOmOdFlux(orderDetailListByOmOdFlux);
	}
	

	public Mono<OmOd> getOrderDetailListByOdNoMono(OmOd omOd) {
		return Mono.just(omOd)
				   .zipWith(orderCommon.getOrderDetailListFindByOdNo(omOd.getOdNo()).collectList())
				   .map(assemble -> assemble.getT1().withOmOdDtlList(assemble.getT2()));
	}
	
	public Mono<OmOd> getOrderDetailListByOmOdMono(OmOd omOd) {
		return Mono.just(omOd)
				   .zipWith(orderCommon.getOrderDetailListFindByOdNo(omOd.getOdNo()).collectList())
				   .map(assemble -> assemble.getT1().withOmOdDtlList(assemble.getT2()));
	}
	
	public Mono<OmOd> getOrderFavorDetailListByOmOdMono(OmOd omOd) {
		Flux<OmOdFvrDtl> omOdFvrDtlFlux= Flux.fromIterable(omOd.getOmOdDtlList())				
											 .flatMap(omOdDtl -> orderCommon.getOrderFavorDetailListObject(omOdDtl));
		
		return Mono.just(omOd)
				   .zipWith(omOdFvrDtlFlux.collectList())
				   .map(assemble -> assemble.getT1().withOmOdFvrDtlList(assemble.getT2()));
	}	
	
	public Flux<OmOd> getOrderDetailListByOmOdFlux() {
		Flux<OmOd> omOdList = orderCommon.getOmOdList();
		Flux<List<OmOdDtl>> omOdDtlList = omOdList.map(OmOd::getOdNo)
												  .flatMap(omOd -> orderCommon.getOrderDetailListFindByOdNo(omOd).collectList());

		return Flux.zip(omOdList, omOdDtlList , (t1 , t2) -> t1.withOmOdDtlList(t2));
	}
	
	public Flux<OmOd> getOrderFavorDetailListByOmOdFlux(Flux<OmOd> omOdFlux) {
		Flux<List<OmOdFvrDtl>> omOdFvrDtlFlux= omOdFlux.map(OmOd::getOmOdDtlList)
													   .flatMap(oodl -> 
													 		Flux.fromIterable(oodl).flatMap(omOdDtl -> orderCommon.getOrderFavorDetailListObject(omOdDtl))
													   .collectList());
		
		return Flux.zip(omOdFlux, omOdFvrDtlFlux , (t1 , t2) ->  t1.withOmOdFvrDtlList(t2));
	}
	
	public Flux<OmOd> getOmOdCertainClientQuery(Flux<OmOdDtlFvrDtlResponse> omOdDtlFvrDtlResponse) {
		Flux<OmOd> omOd = omOdDtlFvrDtlResponse.groupBy(OmOdDtlFvrDtlResponse::getOdNo)
											   .flatMap(oo -> orderCommon.getOmOd(oo.key()));

		Flux<OmOdDtl> omOdDtl = omOdDtlFvrDtlResponse.flatMap(oofd -> orderCommon.getOrderDetailFindByOdNoOdSeqOdProcSeq(setRequestOmOdDtl(oofd)))
													 .distinct();
		
		Flux<OmOdFvrDtl> omOdFvrDtl = omOdDtlFvrDtlResponse.groupBy(OmOdDtlFvrDtlResponse::getOdFvrNo)
														   .flatMap(oofd -> orderCommon.getOrderFavorDetailListByOdFvrNo(oofd.key()));
		
		return omOd.zipWith(omOdDtl.collectList())
				   .map(assemble -> assemble.getT1().withOmOdDtlList(assemble.getT2()))
				   .zipWith(omOdFvrDtl.collectList())
				   .map(assemble -> assemble.getT1().withOmOdFvrDtlList(assemble.getT2()));
	}
	
	public Mono<OmOd> registOmOd(OmOd omOd) {
		omOd.setOdNo(orderCommon.getOdNo());
		return orderCommon.registOmOd(omOd);
	}
	
	public Mono<OmOd> registOmOdDtl(OmOd omOd) {
		Flux<OmOdDtl> omOdDtl = Flux.fromIterable(omOd.getOmOdDtlList())
									 .flatMap(oodl -> {
											oodl.setOdNo(omOd.getOdNo());
											return orderCommon.registOmOdDtl(oodl);
									  });

	
		return Mono.just(omOd)
				   .zipWith(omOdDtl.collectList())
				   .map(assemble -> assemble.getT1().withOmOdDtlList(assemble.getT2()));
	}
	
	public Mono<OmOd> registOmOdFvrDtl(OmOd omOd) {
		Flux<OmOdFvrDtl> omOdFvrDtl = Flux.fromIterable(omOd.getOmOdFvrDtlList())
										  .flatMap(oofd -> {
											  oofd.setOdFvrNo(orderCommon.getOdFvrNo());
											  oofd.setOdNo(omOd.getOdNo());
											  return orderCommon.registOmOdFvrDtl(oofd);
										  });
	
		return Mono.just(omOd)
				   .zipWith(omOdFvrDtl.collectList())
				   .map(assemble -> assemble.getT1().withOmOdFvrDtlList(assemble.getT2()));
	}
	
	public Mono<OmOd> cancelOmOd(OmOd omOd) {
		Mono<OmOd> orgOmOd = orderCommon.getOmOd(omOd.getOdNo());
		orgOmOd.flatMap(oom -> orderCommon.registOmOd(oom));
		
		return Mono.just(omOd);
	}
	
	public Mono<OmOd> cancelOmOdDtl(OmOd omOd) {
		List<OmOdDtl> clmTargetOmOdDtlList = omOd.getOmOdDtlList();
		omOd.setClmNo(orderCommon.getClmNo(omOd.getOdNo()));
		
		Flux<OmOdDtl> cudTargetOmOdDtlList = Flux.fromIterable(clmTargetOmOdDtlList)
							.flatMap(clmDl -> 
								orderCommon.getOrderDetailFindByOdNoOdSeqOdProcSeq(clmDl).flatMap(orgDl -> {
									OmOdDtl omOdDtlClone = orgDl.clone();
									BeanUtils.copyProperties(omOdDtlClone, orgDl, "odQty");
									clmDl.setRegist(true);
									clmDl.setOdTypCd("20");
									clmDl.setProcSeq(clmDl.getProcSeq() + 1);
									
									orgDl.setCnclQty(clmDl.getOdQty());
									orgDl.setClmNo(omOd.getClmNo());
																			
									return Mono.just(orgDl);
								}));
		
		Flux.concat(cudTargetOmOdDtlList, Flux.just(clmTargetOmOdDtlList));
		Flux<OmOdDtl> omOdDtl = cudTargetOmOdDtlList.flatMap(oodl -> {
			return orderCommon.registOmOdDtl(oodl);
		});
				
		return Mono.just(omOd)
				   .zipWith(omOdDtl.collectList())
				   .map(assemble -> assemble.getT1().withOmOdDtlList(assemble.getT2()));
	}
	
	public Mono<OmOd> cancelOmOdFvrDtl(OmOd omOd) {
		List<OmOdFvrDtl> clmTargetOmOdFvrDtlList = omOd.getOmOdFvrDtlList();
		
		Flux<OmOdFvrDtl> cudTargetOmOdFvrDtlList = Flux.fromIterable(clmTargetOmOdFvrDtlList)
				.flatMap(clmFvr -> 
					orderCommon.getOrderFavorDetailListFvlObject(clmFvr).flatMap(orgFvr -> {
						OmOdFvrDtl omOdFvrDtlClone = orgFvr.clone();
						BeanUtils.copyProperties(omOdFvrDtlClone, clmFvr, "aplyQty");
						clmFvr.setRegist(true);
						clmFvr.setOdFvrDvsCd("CNCL");
						clmFvr.setProcSeq(orderCommon.getProcSeq(omOd.getOmOdDtlList(), clmFvr));
						orgFvr.setCnclQty(clmFvr.getAplyQty());
						orgFvr.setClmNo(omOd.getClmNo());
																
						clmTargetOmOdFvrDtlList.add(orgFvr);
						return Mono.just(orgFvr);
					}));
		
		Flux.concat(cudTargetOmOdFvrDtlList, Flux.just(clmTargetOmOdFvrDtlList));

		Flux<OmOdFvrDtl> omOdFvrDtl = cudTargetOmOdFvrDtlList.flatMap(oofd -> {
			return orderCommon.registOmOdFvrDtl(oofd);
		});
				
		return Mono.just(omOd)
				   .zipWith(omOdFvrDtl.collectList())
				   .map(assemble -> assemble.getT1().withOmOdFvrDtlList(assemble.getT2()));
	}
	
	public OmOdDtl setRequestOmOdDtl(OmOdDtlFvrDtlResponse omOdDtlFvrDtlResponse) {
		OmOdDtl omOdDtl = new OmOdDtl();
		BeanUtils.copyProperties(omOdDtlFvrDtlResponse, omOdDtl);
		
		return omOdDtl;
	}
}
