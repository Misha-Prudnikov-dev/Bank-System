package com.project.bank.service;

import com.project.bank.entity.User;

/**
 The UserService interface provides methods for managing user.
 */
public interface UserService {

    /**
     Signs in a user with the specified email and password.
     @param email The email of the user.
     @param password The password of the user.
     @return The signed-in user.
     @throws ServiceException If an error occurs while signing in.
     */
    User signIn(String email, String password) throws ServiceException;

    /**
     Registers a new user with the specified user object and password.
     @param user The user object containing user information.
     @param userPassword The password of the user.
     @return The registered user.
     @throws ServiceException If an error occurs while registering the user.
     */
    User registration(User user, String userPassword) throws ServiceException;

    /**
     Checks if the specified password has been used for the given email.
     @param email The email of the user.
     @param userPassword The password to check.
     @return True if the password has been used before, false otherwise.
     @throws ServiceException If an error occurs while checking the password.
     */
    boolean checkPasswordUsed(String email,String userPassword) throws ServiceException;

    /**
     Retrieves the user information for the user with the specified ID.
     @param id The ID of the user.
     @return The user information.
     @throws ServiceException If an error occurs while retrieving the user information.
     */
    User getUserInfo(int id) throws ServiceException;
}
