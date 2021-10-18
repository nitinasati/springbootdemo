package com.asatisamaj.matrimony.reposoitory;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.asatisamaj.matrimony.entities.Group;

@Repository
public interface UserGroupRepository extends PagingAndSortingRepository<Group, Long> {
    Group findByCode(String code);
}

