package com.asatisamaj.matrimony.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asatisamaj.matrimony.entities.CurrencyExchange;

public interface CurrencyExchangeRepository 
	extends JpaRepository<CurrencyExchange, Long> {
	CurrencyExchange findByFromAndTo(String from, String to);
}
