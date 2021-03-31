package com.example.practice.api.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OmOdDtlParam implements Serializable {

	private static final long serialVersionUID = 5575424746762956834L;
	
	private String odNo;
	private int odSeq;
	private int procSeq;
}
