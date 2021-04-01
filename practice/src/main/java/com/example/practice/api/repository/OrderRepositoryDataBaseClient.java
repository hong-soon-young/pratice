package com.example.practice.api.repository;

import java.util.function.BiFunction;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import com.example.practice.api.dto.OmOdDtlFvrDtlRequest;
import com.example.practice.api.dto.OmOdDtlFvrDtlResponse;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import reactor.core.publisher.Flux;

@Repository
public class OrderRepositoryDataBaseClient {
	private final DatabaseClient databaseClient;
	
	public OrderRepositoryDataBaseClient(DatabaseClient databaseClient) {
		this.databaseClient = databaseClient;
		
	}	
	
	public Flux<OmOdDtlFvrDtlResponse> findOmOdDtlFvrDtl(OmOdDtlFvrDtlRequest omOdDtlFvrDtlRequest) {		
		BiFunction<Row, RowMetadata, OmOdDtlFvrDtlResponse> MAPPING_FUNCTION = (row, rowMetaData) -> OmOdDtlFvrDtlResponse.builder()
	            .odNo(row.get("od_no", String.class))
	            .odSeq(row.get("od_seq", Integer.class))
	            .procSeq(row.get("proc_seq", Integer.class))
	            .odFvrNo(row.get("od_fvr_no", String.class))
	            .build();
				
		return databaseClient.sql(getConditionOdTypCdDcTnnoCdQuery())
				.bind("odNo", omOdDtlFvrDtlRequest.getOdNo())
				.bind("odTypCd", omOdDtlFvrDtlRequest.getOdTypCd())
				.bind("dcTnnoCd", omOdDtlFvrDtlRequest.getDcTnnoCd())
				.map(MAPPING_FUNCTION)
				.all();
	}
	
	public String getConditionOdTypCdDcTnnoCdQuery() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("select ood.od_no,ood.od_seq,ood.proc_seq,oofd.od_fvr_no");
		sb.append(" from om_od_dtl ood,om_od_fvr_dtl oofd");
		sb.append(" where ood.od_no = oofd.od_no");
		sb.append(" and ood.od_seq = oofd.od_seq");
		sb.append(" and ood.proc_seq = oofd.proc_seq");
		sb.append(" and ood.od_qty - ood.cncl_qty > 0");
		sb.append(" and ood.od_no = :odNo");
		sb.append(" and ood.od_typ_cd = :odTypCd");
		sb.append(" and oofd.dc_tnno_cd = :dcTnnoCd");
		
		return sb.toString();			
	}
}
