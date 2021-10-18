package com.asatisamaj.matrimony.service;


import com.asatisamaj.matrimony.domain.UserData;
import com.asatisamaj.matrimony.entities.UserEntity;
import com.asatisamaj.matrimony.exception.InvalidTokenException;
import com.asatisamaj.matrimony.exception.UnkownIdentifierException;
import com.asatisamaj.matrimony.exception.UserAlreadyExistException;

public interface UserService {

    void register(final UserData user) throws UserAlreadyExistException;
    boolean checkIfUserExist(final String email);
    void sendRegistrationConfirmationEmail(final UserEntity user);
    boolean verifyUser(final String token) throws InvalidTokenException;
    UserEntity getUserById(final String id) throws UnkownIdentifierException;
}
