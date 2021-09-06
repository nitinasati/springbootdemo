package com.asatisamaj.matrimony.service;

import com.asatisamaj.matrimony.domain.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
