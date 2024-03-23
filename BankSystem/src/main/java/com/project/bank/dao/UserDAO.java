package com.project.bank.dao;


import com.project.bank.dao.exception.DAOException;
import com.project.bank.dao.exception.PasswordAlreadyExistsDAOException;
import com.project.bank.entity.User;

/**
 * The UserDAO interface provides methods for interacting with the user data in the database.
 */
public interface UserDAO {

    /**
     Signs in a user with the specified email and password.
     @param email The email of the user.
     @param password The password of the user.
     @return The signed-in user.
     @throws DAOException If an error occurs during the sign-in process.
     */
    User signIn(String email, String password) throws DAOException;

    /**
     Registers a new user with the provided user object and password.
     @param user The user object containing registration information.
     @param userPassword The password for the new user.
     @return The registered user.
     @throws DAOException If an error occurs during the registration process.
     */
    User registration(User user, String userPassword) throws DAOException;

    /**
     Checks if the given password has been used by the user with the specified email.
     @param email The email of the user.
     @param userPassword The password to check.
     @return true if the password has been used by the user, false otherwise.
     @throws DAOException If an error occurs while checking the password.
     */
    boolean checkPasswordUsed(String email,String userPassword) throws DAOException;

    /**
     Retrieves the user information for the user with the specified ID.
     @param id The ID of the user.
     @return The user information.
     @throws DAOException If an error occurs while retrieving the user information.
     */
    User getUserInfo(int id) throws DAOException;
}
