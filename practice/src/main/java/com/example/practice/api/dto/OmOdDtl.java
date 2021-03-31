package com.example.practice.api.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("om_od_dtl")
public class OmOdDtl implements Serializable , Persistable<String> , Cloneable {
	private static final long serialVersionUID = -5200824753588401614L;
	
	@Id
	private String odNo;
	private int odSeq;
	private int procSeq;
	private String clmNo;
	private String odTypCd;
	private String odPrgsStepCd;
	private String mbNo;
	private Integer odQty;
	private Integer cnclQty;
	private Integer rtngQty;
	private Integer xchgQty;
	private Integer slPrc;
	private Integer dcAmt;
	private String pdNo;
	private String pdNm;
	private String prNo;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime odCmptDttm;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime purCfrmDttm;	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime regDttm;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime modDttm;
	@Transient
	private boolean isRegist;
	
	@Override
	@Transient
	public String getId() {
		return this.odNo;
	}
	
	@Override
	@Transient
	public boolean isNew() {
		return this.isRegist;
	}
	
	@Override
	@Transient
	public OmOdDtl clone() {
		OmOdDtl clone = null;
		try {
			clone = (OmOdDtl) super.clone();

		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		return clone;
	}
}
