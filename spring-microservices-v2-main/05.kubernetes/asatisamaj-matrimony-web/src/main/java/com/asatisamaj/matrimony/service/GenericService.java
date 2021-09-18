package com.asatisamaj.matrimony.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.asatisamaj.matrimony.domain.MatrimonyResponse;
import com.asatisamaj.matrimony.domain.MatrimonySearchCriteria;
import com.asatisamaj.matrimony.domain.MembersDetail;
import com.asatisamaj.matrimony.reposoitory.MemberDetailsRepository;
import com.asatisamaj.matrimony.utils.GenericSpecification;

@Service
public class GenericService {

	@Autowired
	@Qualifier("restTemplate")
	private RestTemplate restTemplate;

	@Autowired
	private MemberDetailsRepository memberRepository;

	public ResponseEntity<Object> restAPICall() {
		final String uri = "https://countriesnow.space/api/v0.1/countries/states";
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("X-COM-PERSIST", "NO");
		headers.set("X-COM-LOCATION", "USA");
		
		Map<String, String> request = new HashMap<>();
		 request.put("country", "India");
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		ResponseEntity<Object> responseEntity = restTemplate.exchange(uri,HttpMethod.GET,entity, Object.class);
		
		return responseEntity;
	}

	public MatrimonyResponse retriveSearchResult(MatrimonySearchCriteria matrimonySearchCriteria) {

		MatrimonyResponse matrimonyResponse = new MatrimonyResponse();
		try {

			GenericSpecification<MembersDetail> genericSpecification = new GenericSpecification<>();
			matrimonySearchCriteria.getSearchCriteriaList().forEach(searchCriteria -> {
				genericSpecification.add(searchCriteria);
			});

			List<MembersDetail> membersDetail;
			Pageable paging = PageRequest.of(matrimonySearchCriteria.getPage(), matrimonySearchCriteria.getSize(),
					Sort.by(matrimonySearchCriteria.getSortColumn()));

			Page<MembersDetail> pageTuts;
			pageTuts = memberRepository.findAll(genericSpecification, paging);

			membersDetail = pageTuts.getContent();

			matrimonyResponse.setMemberDetails(membersDetail);
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
