package com.asatisamaj.matrimony.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.asatisamaj.matrimony.entities.MemberDetails;
import com.asatisamaj.matrimony.pojo.MatrimonySearchCriteria;
import com.asatisamaj.matrimony.repository.MemberDetailsRepository;
import com.asatisamaj.matrimony.utils.GenericSpecification;

@RestController
public class AsatiSamajMatrimonyController {

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

            GenericSpecification<MemberDetails> genericSpecification = new GenericSpecification<>();
            matrimonySearchCriteria.getSearchCriteriaList().forEach(searchCriteria -> {
                genericSpecification.add(searchCriteria);
            });

            List<MemberDetails> memberDetails;
            Pageable paging = PageRequest.of(matrimonySearchCriteria.getPage(), matrimonySearchCriteria.getSize(),
                    Sort.by(matrimonySearchCriteria.getSortColumn()));

            Page<MemberDetails> pageTuts;
            pageTuts = memberRepository.findAll(genericSpecification, paging);

            memberDetails = pageTuts.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("MemberDetails", memberDetails);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            response.put("resultSortedBy", pageTuts.getPageable().getSort());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
