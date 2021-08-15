package com.asatisamaj.matrimony.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.asatisamaj.matrimony.entities.CurrencyExchange;
import com.asatisamaj.matrimony.entities.MemberDetails;
import com.asatisamaj.matrimony.repository.CurrencyExchangeRepository;
import com.asatisamaj.matrimony.repository.MemberDetailsRepository;

@RestController
public class AsatiSamajMatrimonyController {

    private Logger logger = LoggerFactory.getLogger(AsatiSamajMatrimonyController.class);

    @Autowired
    private CurrencyExchangeRepository repository;

    @Autowired
    private MemberDetailsRepository memberRepository;

    @Autowired
    private Environment environment;

    @GetMapping("/asatisamaj/matrimony/getMemberList")
    public List<MemberDetails> getMemberList() {
        return memberRepository.findAll();
    }

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {

        logger.info("retrieveExchangeValue called with {} to {}", from, to);

        CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);

        if (currencyExchange == null) {
            throw new RuntimeException("Unable to Find data for " + from + " to " + to);
        }

        String port = environment.getProperty("local.server.port");

        // CHANGE-KUBERNETES
        String host = environment.getProperty("HOSTNAME");
        String version = "v14";

        currencyExchange.setEnvironment(port + " " + version + " " + host);

        return currencyExchange;

    }

}
