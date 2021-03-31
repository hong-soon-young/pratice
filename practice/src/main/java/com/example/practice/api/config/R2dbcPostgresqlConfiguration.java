package com.example.practice.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;

//@Configuration
//@EnableR2dbcRepositories
public class R2dbcPostgresqlConfiguration extends AbstractR2dbcConfiguration 
{
	@Value("${spring.r2dbc.url}")
	private String url;
	
	@Bean
	public ConnectionFactory connectionFactory() {
		return ConnectionFactories.get(url);
	}
	
	@Bean
    public ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }
}
