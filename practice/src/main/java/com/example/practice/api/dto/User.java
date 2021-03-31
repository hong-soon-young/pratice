package com.example.practice.api.dto;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("User")
public class User implements Serializable {
	private static final long serialVersionUID = 8151274720872639947L;
	@Id
	private Integer seq;
	private String userId;
	private String userNm;
	private String birthDate;
}
