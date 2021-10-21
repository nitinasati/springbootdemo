package com.asatisamaj.matrimony.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.asatisamaj.matrimony.entities.MembersDetail;
import com.asatisamaj.matrimony.reposoitory.MemberDetailsRepository;
import com.asatisamaj.matrimony.utils.GenericSpecification;

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
}
