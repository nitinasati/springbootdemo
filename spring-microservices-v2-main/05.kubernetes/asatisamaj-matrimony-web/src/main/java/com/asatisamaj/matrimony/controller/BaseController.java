/**
 * 
 */
package com.asatisamaj.matrimony.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.asatisamaj.matrimony.domain.MatrimonySearchCriteria;
import com.asatisamaj.matrimony.domain.MemberStatus;
import com.asatisamaj.matrimony.domain.MembersDetailDTO;
import com.asatisamaj.matrimony.domain.SearchCriteria;
import com.asatisamaj.matrimony.domain.UserGroupType;
import com.asatisamaj.matrimony.entities.MembersDetail;
import com.asatisamaj.matrimony.pagination.DataTableRequest;
import com.asatisamaj.matrimony.pagination.PaginationCriteria;
import com.asatisamaj.matrimony.service.MemberDetailService;
import com.asatisamaj.matrimony.utils.GenericSpecification;
import com.asatisamaj.matrimony.utils.SearchOperation;

/**
 * @author pavan.solapure
 *
 */
@Controller
@RequestMapping("/matrimony")
public class BaseController {

	private static final Logger LOGGER = LogManager.getLogger(BaseController.class);

	@Autowired
	public MemberDetailService memberDetailService;

	private GrantedAuthority adminAuthority = new SimpleGrantedAuthority(UserGroupType.ADMIN.toString());

	@GetMapping(value = "/listmembers")
	public String listUsers(Model model) {
		return "list_members";
	}

	@GetMapping(value = "/addupdatemember")
	public ModelAndView addMember(@RequestParam(value = "memberId", defaultValue = "NOTPROVIDED") String reqMemberId,
			Authentication authentication) {
		ModelAndView mv = new ModelAndView("index");
		MembersDetailDTO membersDetailDTO = new MembersDetailDTO();
		if (!reqMemberId.equalsIgnoreCase("NOTPROVIDED") && (null == authentication
				|| !authentication.getAuthorities().contains(adminAuthority))) {
			mv.setViewName("list_members");
			return mv;
		}
		MembersDetail membersDetail = memberDetailService.getMembersDetail(reqMemberId, authentication);
		BeanUtils.copyProperties(membersDetail, membersDetailDTO);
		mv.addObject("membersDetailDTO", membersDetailDTO);
		mv.setViewName("addmember");
		return mv;
	}

	@GetMapping(value = "/error")
	public String showError(@RequestParam(value = "name", defaultValue = "World") String name) {
		return "error";
	}

	@PostMapping(value = "/addupdatemember")
	public ModelAndView addMember(@Valid @ModelAttribute MembersDetailDTO membersDetailDTO,
			Authentication authentication, Model model, BindingResult bindingResult) {
		ModelAndView mv = new ModelAndView("index");
		if (null == authentication || !authentication.getAuthorities().contains(adminAuthority)) {
			membersDetailDTO.setStatus(MemberStatus.PENDING.toString());
		}
		MembersDetail membersDetail = new MembersDetail();
		boolean update = false;
		if (!bindingResult.hasErrors()) {
			
			if(membersDetailDTO.getMemberId() != null)
			{
				update = true;
				MembersDetail membersDetailTemp = memberDetailService.getMembersDetail(membersDetailDTO.getMemberId().toString(), authentication);
				membersDetailDTO.setImagePath(membersDetailTemp.getImagePath());
				membersDetailDTO.setInsertDate(membersDetailTemp.getInsertDate());
				membersDetailDTO.setInsertProgram(membersDetailTemp.getInsertProgram());
				membersDetailDTO.setInsertUser(membersDetailTemp.getInsertUser());
			}
				
			memberDetailService.setAuditFields(membersDetailDTO,update);

			BeanUtils.copyProperties(membersDetailDTO, membersDetail);
			memberDetailService.saveMember(membersDetail);

			mv.addObject("membersDetailDTO", membersDetailDTO);
			mv.setViewName("addmemberconfirmation");
			return mv;
		}
		mv.setViewName("addmember");
		return mv;
	}

	@PostMapping(value = "/getmemberlist")
	public ResponseEntity<Map<String, Object>> listUsersPaginatedGetMemberList(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication, Model model) {

		try {
			LOGGER.info("List member request is received ...");
			DataTableRequest<MembersDetail> dataTableInRQ = new DataTableRequest<>(request);
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();

			MatrimonySearchCriteria matrimonySearchCriteria = new MatrimonySearchCriteria();
			matrimonySearchCriteria.setSortColumn(pagination.getOrderByColumn());
			matrimonySearchCriteria.setSortDirection(pagination.getOrderByDirection());
			matrimonySearchCriteria.setPage(pagination.getPageNumber() / pagination.getPageSize());
			matrimonySearchCriteria.setSize(pagination.getPageSize());
			GenericSpecification<MembersDetail> genericSpecification = new GenericSpecification<>();
			searchFields(dataTableInRQ, genericSpecification, authentication);

			Pageable paging = PageRequest.of(matrimonySearchCriteria.getPage(), matrimonySearchCriteria.getSize(),
					Sort.by(matrimonySearchCriteria.getSortDirection().equalsIgnoreCase("ASC") ? Direction.ASC
							: Direction.DESC, matrimonySearchCriteria.getSortColumn()));

			Page<MembersDetail> pageTuts;
			pageTuts = memberDetailService.getMemberDetails(genericSpecification, paging);

			Map<String, Object> responseReturn = new HashMap<>();
			responseReturn.put("data", pageTuts.getContent());
			responseReturn.put("draw", Integer.parseInt(dataTableInRQ.getDraw()));
			responseReturn.put("recordsTotal", pageTuts.getTotalElements());
			responseReturn.put("recordsFiltered", pageTuts.getTotalElements());
			responseReturn.put("resultSortedBy", pageTuts.getPageable().getSort());
			responseReturn.put("status", "Success");
			LOGGER.info("Returning List member request ...");
			return new ResponseEntity<>(responseReturn, HttpStatus.OK);
		} catch (Exception e) {
			Map<String, Object> responseReturn = new HashMap<>();
			responseReturn.put("status", "Error");
			responseReturn.put("statusMessage", e);
			e.printStackTrace();
			LOGGER.error("Error while returning list of members ... ", e);
			return new ResponseEntity<>(responseReturn, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * @param dataTableInRQ
	 * @param genericSpecification
	 */
	private void searchFields(DataTableRequest<MembersDetail> dataTableInRQ,
			GenericSpecification<MembersDetail> genericSpecification, Authentication authentication) {
		dataTableInRQ.getColumns().forEach(column -> {
			if (!column.getSearch().isBlank()) {
				if (column.getName().equalsIgnoreCase("memberId")) {
					genericSpecification.add(new SearchCriteria(column.getName(), Long.parseLong(column.getSearch()),
							SearchOperation.EQUAL));
				} else {
					genericSpecification
							.add(new SearchCriteria(column.getName(), column.getSearch(), SearchOperation.MATCH));
				}

			}
		});

		if (null != authentication && authentication.getAuthorities().contains(adminAuthority)) {
			if (null != dataTableInRQ.getSearchByStatus() && !dataTableInRQ.getSearchByStatus().isBlank()) {
				genericSpecification
						.add(new SearchCriteria("status", dataTableInRQ.getSearchByStatus(), SearchOperation.EQUAL));
			}
		} else {
			genericSpecification
					.add(new SearchCriteria("status", MemberStatus.ACTIVE.toString(), SearchOperation.EQUAL));
		}

		if (null != dataTableInRQ.getSearchByMemberId() && !dataTableInRQ.getSearchByMemberId().isBlank()) {
			genericSpecification.add(new SearchCriteria("memberId", Long.parseLong(dataTableInRQ.getSearchByMemberId()),
					SearchOperation.EQUAL));
		}
		if (null != dataTableInRQ.getSearchByGender() && !dataTableInRQ.getSearchByGender().isBlank()) {
			genericSpecification
					.add(new SearchCriteria("gender", dataTableInRQ.getSearchByGender(), SearchOperation.EQUAL));
		}
		if (null != dataTableInRQ.getSearchBySamajArea() && !dataTableInRQ.getSearchBySamajArea().isBlank()) {
			genericSpecification
					.add(new SearchCriteria("samajArea", dataTableInRQ.getSearchBySamajArea(), SearchOperation.MATCH));
		}
		if (null != dataTableInRQ.getSearchByEducation() && !dataTableInRQ.getSearchByEducation().isBlank()) {
			genericSpecification
					.add(new SearchCriteria("education", dataTableInRQ.getSearchByEducation(), SearchOperation.EQUAL));
		}
		if (null != dataTableInRQ.getSearchByEducationDetails()
				&& !dataTableInRQ.getSearchByEducationDetails().isBlank()) {
			genericSpecification.add(new SearchCriteria("educationDetails", dataTableInRQ.getSearchByEducationDetails(),
					SearchOperation.EQUAL));
		}
		if (null != dataTableInRQ.getSearchByOccupation() && !dataTableInRQ.getSearchByOccupation().isBlank()) {
			genericSpecification.add(new SearchCriteria("occupationDetails", dataTableInRQ.getSearchByOccupation(),
					SearchOperation.EQUAL));
		}
		if (null != dataTableInRQ.getSearchByAgeRange() && !dataTableInRQ.getSearchByAgeRange().isBlank()) {

			if (dataTableInRQ.getSearchByAgeRange().equalsIgnoreCase("21 to 25 Years")) {
				genericSpecification.add(new SearchCriteria("age", 25, SearchOperation.LESS_THAN_EQUAL));
				genericSpecification.add(new SearchCriteria("age", 21, SearchOperation.GREATER_THAN_EQUAL));
			} else if (dataTableInRQ.getSearchByAgeRange().equalsIgnoreCase("26 to 30 Years")) {
				genericSpecification.add(new SearchCriteria("age", 30, SearchOperation.LESS_THAN_EQUAL));
				genericSpecification.add(new SearchCriteria("age", 26, SearchOperation.GREATER_THAN_EQUAL));
			} else if (dataTableInRQ.getSearchByAgeRange().equalsIgnoreCase("31 to 35 Years")) {
				genericSpecification.add(new SearchCriteria("age", 35, SearchOperation.LESS_THAN_EQUAL));
				genericSpecification.add(new SearchCriteria("age", 31, SearchOperation.GREATER_THAN_EQUAL));
			} else if (dataTableInRQ.getSearchByAgeRange().equalsIgnoreCase("36 to 40 Years")) {
				genericSpecification.add(new SearchCriteria("age", 40, SearchOperation.LESS_THAN_EQUAL));
				genericSpecification.add(new SearchCriteria("age", 36, SearchOperation.GREATER_THAN_EQUAL));
			} else if (dataTableInRQ.getSearchByAgeRange().equalsIgnoreCase("40+ Years")) {
				genericSpecification.add(new SearchCriteria("age", 40, SearchOperation.GREATER_THAN));
			}
		}
	}

}
