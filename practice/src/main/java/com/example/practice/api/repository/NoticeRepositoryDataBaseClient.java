package com.example.practice.api.repository;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import com.example.practice.api.dto.Notice;

import reactor.core.publisher.Flux;

@Repository
public class NoticeRepositoryDataBaseClient {
	private final DatabaseClient databaseClient;
	
	public NoticeRepositoryDataBaseClient(DatabaseClient databaseClient) {
		this.databaseClient = databaseClient;
	}
	
	public Flux<Notice> findAll() {
		return databaseClient
	}
}
