package com.example.practice.api.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("om_od")
public class OmOd implements Serializable , Persistable<String> {
	private static final long serialVersionUID = -5793348114310316331L;
		
	@Id
	private String odNo;
	private String mbNo;
	private String odrNm;
	private String orglOdNo;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime odCmptDttm;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime regDttm;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime modDttm;
	
	@With
	@Transient
	private List<OmOdDtl> omOdDtlList;
	
	@With
	@Transient
	private List<OmOdFvrDtl> omOdFvrDtlList;
		
	@Transient
	private boolean isRegist;
	@Transient
	private String clmNo;
	
	@Override
	@Transient
	public boolean isNew() {
		return isRegist;
	}

	@Override
	@Transient
	public String getId() {
		return this.odNo;
	}	
	
	@Transient
	public OmOd setOmOd() {
		OmOd omOd = new OmOd();
		omOd.setOdNo("20210330000001");
		omOd.setMbNo("1000000");
		omOd.setOdrNm("홍순영");
		omOd.setOdCmptDttm(LocalDateTime.now());
		omOd.setRegDttm(LocalDateTime.now());
		omOd.setModDttm(LocalDateTime.now());
		omOd.setRegist(true);
		
		List<OmOdDtl> omOdDtlList = new ArrayList<>();
		OmOdDtl omOdDtl = new OmOdDtl();
		BeanUtils.copyProperties(omOd, omOdDtl);
		omOdDtl.setOdSeq(1);
		omOdDtl.setProcSeq(1);
		omOdDtl.setOdTypCd("10");
		omOdDtl.setOdPrgsStepCd("01");
		omOdDtl.setOdQty(2);
		omOdDtl.setCnclQty(0);
		omOdDtl.setSlPrc(10000);
		omOdDtl.setDcAmt(2000);
		omOdDtl.setClmNo("0");
		
		omOdDtlList.add(omOdDtl);
		
		OmOdDtl omOdDtl1 = omOdDtl.clone();
		omOdDtl1.setOdSeq(2);
		omOdDtl.setOdQty(1);
		omOdDtl.setSlPrc(20000);
		omOdDtl.setDcAmt(3000);
		
		omOdDtlList.add(omOdDtl1);
		

		List<OmOdFvrDtl> omOdFvrDtlList = new ArrayList<>();
		OmOdFvrDtl omOdFvrDtl = new OmOdFvrDtl();
		BeanUtils.copyProperties(omOd, omOdFvrDtl);
		BeanUtils.copyProperties(omOdDtl, omOdFvrDtl);
		omOdFvrDtl.setOdFvrNo("20200330001");
		omOdFvrDtl.setOdFvrDvsCd("HAPN");
		omOdFvrDtl.setDcTnnoCd("1ST");
		omOdFvrDtl.setAplyQty(2);
		omOdFvrDtl.setCnclQty(0);
		omOdFvrDtl.setFvrAmt(1000);
		omOdDtl.setClmNo("0");
		
		omOdFvrDtlList.add(omOdFvrDtl);
		
		OmOdFvrDtl omOdFvrDtl1 = omOdFvrDtl.clone();
		omOdFvrDtl.setOdFvrNo("20200330002");
		omOdFvrDtl.setDcTnnoCd("5TH");
		omOdFvrDtl.setFvrAmt(1000);
		omOdFvrDtl.setCpnNo("2342352352");
		omOdFvrDtl.setCpnNm("쿠폰할인");
		
		omOdFvrDtlList.add(omOdFvrDtl1);
		
		omOd.setOmOdDtlList(omOdDtlList);
		omOd.setOmOdFvrDtlList(omOdFvrDtlList);
		
		return omOd;		
	}
}
