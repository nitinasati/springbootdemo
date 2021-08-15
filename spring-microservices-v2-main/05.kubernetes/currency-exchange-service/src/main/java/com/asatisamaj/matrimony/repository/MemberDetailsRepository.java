package com.asatisamaj.matrimony.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asatisamaj.matrimony.entities.MemberDetails;

public interface MemberDetailsRepository 
	extends JpaRepository<MemberDetails, Long> {
}
