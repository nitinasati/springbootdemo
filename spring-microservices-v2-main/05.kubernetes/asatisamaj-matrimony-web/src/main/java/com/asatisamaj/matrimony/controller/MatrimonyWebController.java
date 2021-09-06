package com.asatisamaj.matrimony.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.asatisamaj.matrimony.domain.MatrimonySearchCriteria;
import com.asatisamaj.matrimony.domain.MemberDetails;
import com.asatisamaj.matrimony.reposoitory.MemberDetailsRepository;
import com.asatisamaj.matrimony.utils.GenericSpecification;

@Controller
public class MatrimonyWebController {

    @Autowired
    private MemberDetailsRepository memberRepository;
    
    
    @PostMapping("/matrimony/web/getmemberdetails")
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
            response.put("memberDetails", memberDetails);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            response.put("resultSortedBy", pageTuts.getPageable().getSort());
            response.put("status", "Success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Error");
            response.put("statusMessage", e);
            e.printStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
