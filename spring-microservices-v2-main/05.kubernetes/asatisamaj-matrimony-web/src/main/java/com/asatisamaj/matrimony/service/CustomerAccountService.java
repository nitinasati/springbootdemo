package com.asatisamaj.matrimony.service;

import com.asatisamaj.matrimony.exception.InvalidTokenException;
import com.asatisamaj.matrimony.exception.UnkownIdentifierException;

public interface CustomerAccountService {

    void forgottenPassword(final String userName) throws UnkownIdentifierException;
    void updatePassword(final String password, final String token) throws InvalidTokenException, UnkownIdentifierException;
    boolean loginDisabled(final String username);
}
