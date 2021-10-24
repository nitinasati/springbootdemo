package com.asatisamaj.matrimony.service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.asatisamaj.matrimony.domain.MembersDetailDTO;
import com.asatisamaj.matrimony.domain.SearchCriteria;
import com.asatisamaj.matrimony.entities.MembersDetail;
import com.asatisamaj.matrimony.reposoitory.MemberDetailsRepository;
import com.asatisamaj.matrimony.utils.GenericSpecification;
import com.asatisamaj.matrimony.utils.SearchOperation;

@Service
public class MemberDetailServiceImpl implements MemberDetailService {

	@Autowired
	private MemberDetailsRepository memberRepository;
	
	@Override
	public Page<MembersDetail> getMemberDetails(GenericSpecification<MembersDetail> genericSpecification,
			Pageable paging) {
		return memberRepository.findAll(genericSpecification, paging);
	}

	@Override
	public void saveMember(MembersDetail membersDetail) {
		memberRepository.save(membersDetail);
	}

	@Override
	public Long findMaxId() {
		return memberRepository.findMaxMemberId();
	}
	/**
	 * @param reqMemberId
	 * @param membersDetailDTO
	 */
	@Override
	public MembersDetail getMembersDetail(String reqMemberId, Authentication authentication) {
		MembersDetail membersDetail = new MembersDetail();
		if (!reqMemberId.equalsIgnoreCase("NOTPROVIDED")) {

			GenericSpecification<MembersDetail> genericSpecification = new GenericSpecification<>();
			genericSpecification
					.add(new SearchCriteria("memberId", Long.parseLong(reqMemberId), SearchOperation.EQUAL));

			Pageable paging = PageRequest.of(0, 1, Sort.by(Direction.ASC, "memberId"));
			Page<MembersDetail> pageTuts;
			pageTuts = memberRepository.findAll(genericSpecification, paging);
			membersDetail = pageTuts.getContent().get(0);
		}
		return membersDetail;
	}

	@Override
	public void setAuditFields(MembersDetailDTO membersDetailDTO,boolean update) {

		long millis = System.currentTimeMillis();

		if (update) // update check
		{
			membersDetailDTO.setUpdateDate(new Date(millis));
			membersDetailDTO.setUpdateProgram("website-update");
			membersDetailDTO.setUpdateUser("update");
		} else {

			membersDetailDTO.setInsertDate(new Date(millis));
			membersDetailDTO.setInsertProgram("website-insert");
			membersDetailDTO.setInsertUser("insert");
			membersDetailDTO.setMemberId(findMaxId() + 1);
		}
	}
}
