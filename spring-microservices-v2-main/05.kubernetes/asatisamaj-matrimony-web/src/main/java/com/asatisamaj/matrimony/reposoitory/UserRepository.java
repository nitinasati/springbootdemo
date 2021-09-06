package com.asatisamaj.matrimony.reposoitory;


import org.springframework.data.jpa.repository.JpaRepository;

import com.asatisamaj.matrimony.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
