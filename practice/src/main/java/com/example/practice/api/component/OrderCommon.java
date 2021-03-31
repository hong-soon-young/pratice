package com.example.practice.api.component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.practice.api.dto.OmOd;
import com.example.practice.api.dto.OmOdDtl;
import com.example.practice.api.dto.OmOdFvrDtl;
import com.example.practice.api.repository.OmOdDtlRepository;
import com.example.practice.api.repository.OmOdFvrDtlRepository;
import com.example.practice.api.repository.OmOdRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class OrderCommon {
	@Autowired
	private OmOdRepository omOdRepository;	
	@Autowired
	private OmOdDtlRepository omOdDtlRepository;
	@Autowired
	private OmOdFvrDtlRepository omOdFvrDtlRepository;
		
	public Mono<OmOd> getOmOd(String odNo) { 
		return omOdRepository.findByOdNo(odNo);
	}
	
	public Flux<OmOd> getOmOdList() {
		return omOdRepository.findAll();
	}
	
	public Mono<List<OmOd>> getOmOdListMono() {
		return omOdRepository.findAll().collectList();
	}
	
	public Mono<OmOd> registOmOd(OmOd omOd) {
		return omOdRepository.save(omOd);
	}
	
	public Flux<OmOdDtl> getOrderDetailListFindByOdNo(String odNo) {
		return omOdDtlRepository.findByOdNo(odNo);
	}
		
	public Flux<OmOdDtl> getOrderDetailListByOdNoAndOdTypCd(String odNo, String odTypCd) {
		return omOdDtlRepository.findByOdNoAndOdTypCd(odNo, odTypCd);
	}
	
	public Flux<OmOdDtl> getOrderDetailFindByOdNoOdSeqOdProcSeq(OmOdDtl omOdDtl) {
		return omOdDtlRepository.findByOdNoAndOdSeqAndProcSeq(omOdDtl.getOdNo(), omOdDtl.getOdSeq(), omOdDtl.getProcSeq());
	}
	
	public Mono<OmOdDtl> registOmOdDtl(OmOdDtl omOdDtl) {
		return omOdDtlRepository.save(omOdDtl);
	}
	
	public Flux<OmOdFvrDtl> getOrderFavorDetailListByOdNo(String odNo) {
		return omOdFvrDtlRepository.findByOdNo(odNo);
	}
		
	public Flux<OmOdFvrDtl> getOrderFavorDetailListByOdNoOdSeqOdProcSeq(String odNo , int odSeq , int procSeq) {
		return omOdFvrDtlRepository.findByOdNoAndOdSeqAndProcSeq(odNo, odSeq, procSeq);
	}
	
	public Flux<OmOdFvrDtl> getOrderFavorDetailListObject(OmOdDtl omOdDtl) {
		return omOdFvrDtlRepository.findByOdNoAndOdSeqAndProcSeq(omOdDtl.getOdNo(), omOdDtl.getOdSeq() , omOdDtl.getProcSeq());
	}
	
	public Flux<OmOdFvrDtl> getOrderFavorDetailListFvlObject(OmOdFvrDtl omOdFvrDtl) {
		return omOdFvrDtlRepository.findByOdNoAndOdSeqAndProcSeq(omOdFvrDtl.getOdNo(), omOdFvrDtl.getOdSeq(), omOdFvrDtl.getProcSeq());
	}	
		
	public Mono<OmOdFvrDtl> registOmOdFvrDtl(OmOdFvrDtl omOdFvrDtl) {
		return omOdFvrDtlRepository.save(omOdFvrDtl);
	}
	
	public String getOdNo() {
		String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		return today.concat(random(7));
	}
	
	public String getOdFvrNo() {
		String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		return today.concat(random(4));
	}
	
	public String random(int length) {
		Random rd = new Random();
		String returnValue = "";
	    
        for(int i = 0; i < 6; i++) {
        	returnValue += rd.nextInt(10);
        }
        
        return returnValue;
	}
	
	public boolean isEmptyOmOd(OmOd omOd) { 
		return true;
	}
	
	public String getClmNo(String odNo) {
		return String.valueOf(Integer.parseInt(getOrderDetailListFindByOdNo(odNo).map(OmOdDtl::getClmNo)
				.reduce((t1 , t2) -> {
					return t1.compareTo(t2) > 0 || t1.equals(t2) ? t1 : t2;	
				}).toString() + 1));		
	}
	
	public int getProcSeq(List<OmOdDtl> omOdDtlList, OmOdFvrDtl omOdFvrDtl) {
		return omOdDtlList.stream()
				.filter(omOdDtl -> StringUtils.equals(omOdDtl.getOdNo(), omOdFvrDtl.getOdNo())
										&& omOdDtl.getOdSeq() == omOdFvrDtl.getOdSeq())
				.max((omOdDtl1 , omOdDtl2) -> Integer.compare(omOdDtl1.getProcSeq(), omOdDtl2.getProcSeq())).get().getProcSeq();			
	}
}
