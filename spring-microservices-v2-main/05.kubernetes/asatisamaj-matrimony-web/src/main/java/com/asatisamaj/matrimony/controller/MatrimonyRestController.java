package com.asatisamaj.matrimony.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asatisamaj.matrimony.domain.DropDown;
import com.asatisamaj.matrimony.domain.DropDownValues;
import com.asatisamaj.matrimony.domain.MatrimonySearchCriteria;
import com.asatisamaj.matrimony.entities.MembersDetail;
import com.asatisamaj.matrimony.reposoitory.MemberDetailsRepository;
import com.asatisamaj.matrimony.service.GenericService;
import com.asatisamaj.matrimony.utils.GenericSpecification;

@RestController
@RequestMapping("/matrimony/rest/api")
public class MatrimonyRestController {

	@Autowired
	private MemberDetailsRepository memberRepository;

	@Autowired
	private GenericService genericService;


	@PostMapping("/getmemberdetails")
	public ResponseEntity<Map<String, Object>> getMemberListByFilterPage(
			@RequestBody MatrimonySearchCriteria matrimonySearchCriteria) {

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
			Map<String, Object> response = new HashMap<>();
			response.put("memberDetails", membersDetail);
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

	@GetMapping(value = "/gettodo")
	public ResponseEntity<Object> getToDo() throws IOException {
		return genericService.restAPICall();

	}

	@GetMapping(value = "/getdropdownsamajarea")
	public ResponseEntity<Map<String, Object>> listDropDownSamajArea(Authentication authentication) {
		Map<String, Object> response = new HashMap<>();
		List<DropDown> dropDownValues = new ArrayList<>();
		DropDownValues dropDownSamajArea = new DropDownValues();
		dropDownValues.add(new DropDown("--Select SamajArea--", ""));

		dropDownSamajArea.getGetSamajArea().forEach(samajArea -> {
			dropDownValues.add(new DropDown(samajArea, samajArea));
		});
		response.put("samajAreaDropDown", dropDownValues);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/getdropdowneducation")
	public ResponseEntity<Map<String, Object>> listDropDownEducation() {
		Map<String, Object> response = new HashMap<>();
		List<DropDown> dropDownValues = new ArrayList<>();
		DropDownValues dropDownEducation = new DropDownValues();
		dropDownValues.add(new DropDown("--Select Education--", ""));

		dropDownEducation.getGetEducation().forEach(education -> {
			dropDownValues.add(new DropDown(education, education));
		});
		response.put("educationDropDown", dropDownValues);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/getdropdowneducationdetails")
	public ResponseEntity<Map<String, Object>> listDropDownEducationDetails() {
		Map<String, Object> response = new HashMap<>();
		List<DropDown> dropDownValues = new ArrayList<>();
		DropDownValues dropDownEducationDetails = new DropDownValues();
		dropDownValues.add(new DropDown("--Select Education Details--", ""));

		dropDownEducationDetails.getGetEducationDetails().forEach(education -> {
			dropDownValues.add(new DropDown(education, education));
		});
		response.put("educationDetailsDropDown", dropDownValues);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/getdropdownoccupation")
	public ResponseEntity<Map<String, Object>> listDropDownOccupation() {
		Map<String, Object> response = new HashMap<>();
		List<DropDown> dropDownValues = new ArrayList<>();
		DropDownValues dropDownOccupation = new DropDownValues();
		dropDownValues.add(new DropDown("--Select Occupation--", ""));

		dropDownOccupation.getGetOccupation().forEach(occupation -> {
			dropDownValues.add(new DropDown(occupation, occupation));
		});
		response.put("occupationDropDown", dropDownValues);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/getdropdownagerange")
	public ResponseEntity<Map<String, Object>> listDropDownAgeRange() {
		Map<String, Object> response = new HashMap<>();
		List<DropDown> dropDownValues = new ArrayList<>();
		DropDownValues dropDownAgeRange = new DropDownValues();
		dropDownValues.add(new DropDown("--Select Age Range--", ""));

		dropDownAgeRange.getGetAgeRange().forEach(agerange -> {
			dropDownValues.add(new DropDown(agerange, agerange));
		});
		response.put("ageRangeDropDown", dropDownValues);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	


}
