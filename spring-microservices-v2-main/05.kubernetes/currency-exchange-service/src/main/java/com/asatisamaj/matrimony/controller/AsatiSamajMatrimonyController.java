package com.asatisamaj.matrimony.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.asatisamaj.matrimony.entities.MemberDetails;
import com.asatisamaj.matrimony.pojo.MatrimonySearchCriteria;
import com.asatisamaj.matrimony.repository.MemberDetailsRepository;

@RestController
public class AsatiSamajMatrimonyController {

    private Logger logger = LoggerFactory.getLogger(AsatiSamajMatrimonyController.class);

    @Autowired
    private MemberDetailsRepository memberRepository;

    @GetMapping("/asatisamaj/matrimony/getAllMemberList")
    public List<MemberDetails> getMemberList() {
        return memberRepository.findAll();
    }

    @PostMapping("/asatisamaj/matrimony/getMemberListByFilterPage")
    public ResponseEntity<Map<String, Object>> getMemberListByFilterPage(
            @RequestBody MatrimonySearchCriteria matrimonySearchCriteria) {

        try {
            List<MemberDetails> memberDetails;
            Pageable paging = PageRequest.of(matrimonySearchCriteria.getPage(), matrimonySearchCriteria.getSize());

            Page<MemberDetails> pageTuts;
            pageTuts = memberRepository.findByEducationContaining(matrimonySearchCriteria.getEducation(), paging);
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

}
