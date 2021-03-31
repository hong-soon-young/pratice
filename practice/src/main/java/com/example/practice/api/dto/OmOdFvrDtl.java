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
@Table("om_od_fvr_dtl")
public class OmOdFvrDtl implements Serializable , Persistable<String> , Cloneable {
	private static final long serialVersionUID = 2028689674664550029L;
	
	@Id
	private String odFvrNo;
	private String odNo;
	private Integer odSeq;
	private Integer procSeq;
	private String clmNo;
	private String orglOdFvrNo;
	private String odFvrDvsCd;
	private String dcTnnoCd;
	private Integer aplyQty;
	private Integer cnclQty;
	private Integer fvrAmt;
	private String prNo;
	private String prNm;
	private String cpnNo;
	private String cpnNm;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime regDttm;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime modDttm;
	@Transient
	private boolean isRegist;
	
	@Override
	@Transient
	public String getId() {
		return this.odFvrNo;
	}
	
	@Override
	@Transient
	public boolean isNew() {
		return this.isRegist;
	}
	
	@Override
	@Transient
	public OmOdFvrDtl clone() {
		OmOdFvrDtl clone = null;
		try {
			clone = (OmOdFvrDtl) super.clone();

		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		return clone;
	}
}
