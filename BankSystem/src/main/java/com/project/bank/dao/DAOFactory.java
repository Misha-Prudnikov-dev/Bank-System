package com.project.bank.dao;


import com.project.bank.dao.impl.AccountDAOImpl;
import com.project.bank.dao.impl.BankDAOImpl;
import com.project.bank.dao.impl.UserDAOImpl;

/**
 * The DAOFactory class is responsible for creating instances of DAO interfaces.
 * It follows the Singleton design pattern to ensure only one instance of DAOFactory exists.
 */
public class DAOFactory {

    private static final DAOFactory instance = new DAOFactory();

    public static DAOFactory getInstance() {
        return instance;
    }

    private DAOFactory() {

    }

    private UserDAO userDAO = new UserDAOImpl();
    private AccountDAO accountDAO = new AccountDAOImpl();
    private BankDAO bankDAO = new BankDAOImpl();

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public BankDAO getBankDAO() {
        return bankDAO;
    }
}
