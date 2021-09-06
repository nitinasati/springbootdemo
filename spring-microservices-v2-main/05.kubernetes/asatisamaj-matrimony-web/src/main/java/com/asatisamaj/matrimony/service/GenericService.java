package com.asatisamaj.matrimony.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.asatisamaj.matrimony.domain.MatrimonyResponse;
import com.asatisamaj.matrimony.domain.MatrimonySearchCriteria;
import com.asatisamaj.matrimony.domain.MemberDetails;
import com.asatisamaj.matrimony.reposoitory.MemberDetailsRepository;
import com.asatisamaj.matrimony.utils.GenericSpecification;

@Service
public class GenericService {

    @Autowired
    private MemberDetailsRepository memberRepository;

    public MatrimonyResponse retriveSearchResult(MatrimonySearchCriteria matrimonySearchCriteria) {

        MatrimonyResponse matrimonyResponse = new MatrimonyResponse();
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

            matrimonyResponse.setMemberDetails(memberDetails);
            matrimonyResponse.setCurrentPage(pageTuts.getNumber());
            matrimonyResponse.setTotalPages(pageTuts.getTotalPages());
            matrimonyResponse.setTotalItems(pageTuts.getTotalElements());
            return matrimonyResponse;
        } catch (Exception e) {
            matrimonyResponse.setStatus("failure");
            return matrimonyResponse;
        }

    }

}
