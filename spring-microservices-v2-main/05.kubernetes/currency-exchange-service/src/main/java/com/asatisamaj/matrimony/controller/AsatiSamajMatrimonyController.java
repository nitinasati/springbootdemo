package com.asatisamaj.matrimony.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/asatisamaj/matrimony/getAllMemberList")
    public List<MemberDetails> getMemberList() {
        return memberRepository.findAll();
    }

    @GetMapping("/asatisamaj/matrimony/getMemberListByFilterPage")
    public ResponseEntity<Map<String, Object>> getMemberListByFilterPage(@RequestParam(required = false) String education,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {

        try {
            List<MemberDetails> memberDetails;
            Pageable paging = PageRequest.of(page, size);

            Page<MemberDetails> pageTuts;
            if (education == null)
                pageTuts = memberRepository.findAll(paging);
            else
                pageTuts = memberRepository.findByEducationContaining(education, paging);

            memberDetails = pageTuts.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("MemberDetails", memberDetails);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
