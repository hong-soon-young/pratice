package com.example.practice.api.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OmOdDtlFvrDtlRequest implements Serializable  {
	private static final long serialVersionUID = -8352104843694135953L;
	
	private String odNo;
	private String odTypCd;
	private String dcTnnoCd;
}
