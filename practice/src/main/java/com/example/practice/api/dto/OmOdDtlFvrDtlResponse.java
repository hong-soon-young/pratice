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
public class OmOdDtlFvrDtlResponse implements Serializable  {
	private static final long serialVersionUID = 9206427824695736116L;
	
	private String odNo;
	private Integer odSeq;
	private Integer procSeq;
	private String odFvrNo;
}
