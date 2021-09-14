package com.asatisamaj.matrimony.reposoitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.asatisamaj.matrimony.domain.MembersDetail;

public interface MemberDetailsRepository 
	extends JpaRepository<MembersDetail, Long>, JpaSpecificationExecutor<MembersDetail> {
}
