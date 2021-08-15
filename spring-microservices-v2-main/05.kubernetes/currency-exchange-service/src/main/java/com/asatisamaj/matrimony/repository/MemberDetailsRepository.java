package com.asatisamaj.matrimony.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.asatisamaj.matrimony.entities.MemberDetails;

public interface MemberDetailsRepository 
	extends JpaRepository<MemberDetails, Long> {
    
    Page<MemberDetails> findBySamajArea(String samajArea, Pageable pageable);
    Page<MemberDetails> findByEducationContaining(String title, Pageable pageable);
}
