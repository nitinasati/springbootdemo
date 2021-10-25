package com.asatisamaj.matrimony.reposoitory;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Repository;

import com.asatisamaj.matrimony.entities.UserEntity;

@Repository
@RedisHash
public interface UserRepository extends JpaRepository<UserEntity, Long > {
    UserEntity findByEmail(String email);
}
