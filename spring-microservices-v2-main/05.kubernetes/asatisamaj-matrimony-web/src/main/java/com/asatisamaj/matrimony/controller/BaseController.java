/**
 * 
 */
package com.asatisamaj.matrimony.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.asatisamaj.matrimony.domain.MatrimonySearchCriteria;
import com.asatisamaj.matrimony.domain.MemberDetails;
import com.asatisamaj.matrimony.domain.MemberDetailsDTO;
import com.asatisamaj.matrimony.domain.SearchCriteria;
import com.asatisamaj.matrimony.domain.User;
import com.asatisamaj.matrimony.domain.UserModel;
import com.asatisamaj.matrimony.pagination.DataTableRequest;
import com.asatisamaj.matrimony.pagination.DataTableResults;
import com.asatisamaj.matrimony.pagination.PaginationCriteria;
import com.asatisamaj.matrimony.reposoitory.GenericRepo;
import com.asatisamaj.matrimony.reposoitory.MemberDetailsRepository;
import com.asatisamaj.matrimony.reposoitory.UserRepository;
import com.asatisamaj.matrimony.utils.AppUtil;
import com.asatisamaj.matrimony.utils.GenericSpecification;
import com.asatisamaj.matrimony.utils.SearchOperation;
import com.google.gson.Gson;

/**
 * @author pavan.solapure
 *
 */
@Controller
public class BaseController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private GenericRepo genericRepo;

	@Autowired
	private MemberDetailsRepository memberRepository;

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	@GetMapping(value = "/")
	public ModelAndView home(@RequestParam(value = "name", defaultValue = "World") String name) {
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("userModel", new MemberDetailsDTO());
		List<UserModel> userList = genericRepo.getUserModel();
		mv.addObject("userlist", userList);
		return mv;
	}

	@GetMapping(value = "/users/mysql")
	public String listUsers(Model model) {
		return "users_mysql";
	}

	@GetMapping(value = "/users/paginated/mysql")
	@ResponseBody
	public String listUsersPaginated(HttpServletRequest request, HttpServletResponse response, Model model) {

		DataTableRequest<User> dataTableInRQ = new DataTableRequest<>(request);
		PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();

		String baseQuery = "SELECT USER_ID as id, USER_NAME as name, SALARY as salary, (SELECT COUNT(1) FROM MYUSERS) AS total_records  FROM MYUSERS";
		String paginatedQuery = AppUtil.buildPaginatedQuery(baseQuery, pagination);

		System.out.println(paginatedQuery);

		Query query = entityManager.createNativeQuery(paginatedQuery, UserModel.class);

		@SuppressWarnings("unchecked")
		List<UserModel> userList = query.getResultList();

		DataTableResults<UserModel> dataTableResult = new DataTableResults<UserModel>();
		dataTableResult.setDraw(dataTableInRQ.getDraw());
		dataTableResult.setListOfDataObjects(userList);
		if (!AppUtil.isObjectEmpty(userList)) {
			dataTableResult.setRecordsTotal(userList.get(0).getTotalRecords().toString());
			if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
				dataTableResult.setRecordsFiltered(userList.get(0).getTotalRecords().toString());
			} else {
				dataTableResult.setRecordsFiltered(Integer.toString(userList.size()));
			}
		}
		return new Gson().toJson(dataTableResult);
	}

	@PostMapping(value = "/users/paginated/getmemberlist")
	public ResponseEntity<Map<String, Object>> listUsersPaginatedGetMemberList(HttpServletRequest request,
			HttpServletResponse response, Model model) {

		try {
			DataTableRequest<MemberDetailsDTO> dataTableInRQ = new DataTableRequest<>(request);
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();

			MatrimonySearchCriteria matrimonySearchCriteria = new MatrimonySearchCriteria();

			matrimonySearchCriteria.setSortColumn(pagination.getOrderByColumn());
			matrimonySearchCriteria.setSortDirection(pagination.getOrderByDirection());
			matrimonySearchCriteria.setPage(pagination.getPageNumber() / pagination.getPageSize());
			matrimonySearchCriteria.setSize(pagination.getPageSize());
			GenericSpecification<MemberDetails> genericSpecification = new GenericSpecification<>();
			searchFields(dataTableInRQ, genericSpecification);

			Pageable paging = PageRequest.of(matrimonySearchCriteria.getPage(), matrimonySearchCriteria.getSize(),
					Sort.by(matrimonySearchCriteria.getSortDirection().equalsIgnoreCase("ASC") ? Direction.ASC
							: Direction.DESC, matrimonySearchCriteria.getSortColumn()));

			Page<MemberDetails> pageTuts;
			pageTuts = memberRepository.findAll(genericSpecification, paging);

			Map<String, Object> responseReturn = new HashMap<>();
			responseReturn.put("data", pageTuts.getContent());
			responseReturn.put("draw", Integer.parseInt(dataTableInRQ.getDraw()));
			responseReturn.put("recordsTotal", pageTuts.getTotalElements());
			responseReturn.put("recordsFiltered", pageTuts.getTotalElements());
			responseReturn.put("resultSortedBy", pageTuts.getPageable().getSort());
			responseReturn.put("status", "Success");
			return new ResponseEntity<>(responseReturn, HttpStatus.OK);
		} catch (Exception e) {
			Map<String, Object> responseReturn = new HashMap<>();
			responseReturn.put("status", "Error");
			responseReturn.put("statusMessage", e);
			e.printStackTrace();
			return new ResponseEntity<>(responseReturn, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/users/oracle")
	public String listUsersOracle(Model model) {
		return "users";
	}

	@GetMapping(value = "/users/paginated/orcl")
	@ResponseBody
	public String listUsersPaginatedForOracle(HttpServletRequest request, HttpServletResponse response, Model model) {

		DataTableRequest<User> dataTableInRQ = new DataTableRequest<User>(request);
		PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
		String baseQuery = "SELECT USER_ID as id, USER_NAME as name, SALARY as salary  FROM MYUSERS";
		String paginatedQuery = AppUtil.buildPaginatedQueryForOracle(baseQuery, pagination);

		System.out.println(paginatedQuery);

		Query query = entityManager.createNativeQuery(paginatedQuery, UserModel.class);

		@SuppressWarnings("unchecked")
		List<UserModel> userList = query.getResultList();

		DataTableResults<UserModel> dataTableResult = new DataTableResults<UserModel>();
		dataTableResult.setDraw(dataTableInRQ.getDraw());
		dataTableResult.setListOfDataObjects(userList);
		if (!AppUtil.isObjectEmpty(userList)) {
			dataTableResult.setRecordsTotal(userList.get(0).getTotalRecords().toString());
			if (dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
				dataTableResult.setRecordsFiltered(userList.get(0).getTotalRecords().toString());
			} else {
				dataTableResult.setRecordsFiltered(Integer.toString(userList.size()));
			}
		}
		return new Gson().toJson(dataTableResult);
	}

	@PostMapping(value = "/adduser")
	public String addUser(@ModelAttribute UserModel userModel, Model model) {
		if (null != userModel) {

			if (!AppUtil.isObjectEmpty(userModel.getId()) && !AppUtil.isObjectEmpty(userModel.getName())
					&& !AppUtil.isObjectEmpty(userModel.getSalary())) {

				User u = new User();
				u.setId(userModel.getId());
				u.setName(userModel.getName());
				u.setSalary(userModel.getSalary());
				userRepo.save(u);
			}
		}
		return "redirect:/";
	}

	@PostMapping(value = "/addmember")
	public String addMember(@ModelAttribute UserModel userModel, Model model) {
		if (null != userModel) {

			if (!AppUtil.isObjectEmpty(userModel.getId()) && !AppUtil.isObjectEmpty(userModel.getName())
					&& !AppUtil.isObjectEmpty(userModel.getSalary())) {

				User u = new User();
				u.setId(userModel.getId());
				u.setName(userModel.getName());
				u.setSalary(userModel.getSalary());
				userRepo.save(u);
			}
		}
		return "redirect:/";
	}

	/**
	 * @param dataTableInRQ
	 * @param genericSpecification
	 */
	private void searchFields(DataTableRequest<MemberDetailsDTO> dataTableInRQ,
			GenericSpecification<MemberDetails> genericSpecification) {
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
			genericSpecification.add(
					new SearchCriteria("occupation", dataTableInRQ.getSearchByOccupation(), SearchOperation.EQUAL));
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
