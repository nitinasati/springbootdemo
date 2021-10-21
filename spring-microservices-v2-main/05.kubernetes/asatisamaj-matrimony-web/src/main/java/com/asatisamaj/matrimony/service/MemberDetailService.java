package com.asatisamaj.matrimony.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.asatisamaj.matrimony.entities.MembersDetail;
import com.asatisamaj.matrimony.utils.GenericSpecification;

public interface MemberDetailService {

	public Page<MembersDetail> getMemberDetails(GenericSpecification<MembersDetail> genericSpecification,
			Pageable paging);

	public void saveMember(MembersDetail membersDetail);

	public Long findMaxId();
}
