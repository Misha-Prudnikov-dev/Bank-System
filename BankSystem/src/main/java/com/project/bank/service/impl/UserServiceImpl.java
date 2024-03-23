package com.project.bank.service.impl;

import com.project.bank.dao.DAOFactory;
import com.project.bank.dao.UserDAO;
import com.project.bank.dao.exception.DAOException;
import com.project.bank.dao.exception.PasswordAlreadyExistsDAOException;
import com.project.bank.entity.User;
import com.project.bank.service.PasswordAlreadyExistsServiceException;
import com.project.bank.service.ServiceException;
import com.project.bank.service.UserService;

public class UserServiceImpl implements UserService {

    private static final DAOFactory DAO_FACTORY = DAOFactory.getInstance();
    private static final UserDAO USER_DAO = DAO_FACTORY.getUserDAO();
    @Override
    public User signIn(String email, String password) throws ServiceException {
        try {
            return USER_DAO.signIn(email,password);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User registration(User user, String userPassword) throws ServiceException {
        try {
            return USER_DAO.registration(user, userPassword);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean checkPasswordUsed(String email,String userPassword) throws ServiceException {
        try {
            return USER_DAO.checkPasswordUsed(email,userPassword);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getUserInfo(int id) throws ServiceException {
        return null;
    }
}
