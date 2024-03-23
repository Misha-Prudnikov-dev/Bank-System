package com.project.bank.service.impl;

import com.project.bank.dao.AccountDAO;
import com.project.bank.dao.DAOFactory;
import com.project.bank.dao.exception.DAOException;
import com.project.bank.entity.Account;
import com.project.bank.entity.AccountShortInfo;
import com.project.bank.entity.Transaction;
import com.project.bank.service.AccountService;
import com.project.bank.service.ServiceException;

import java.math.BigDecimal;
import java.util.List;

public class AccountServiceImpl implements AccountService {

    private static final DAOFactory DAO_FACTORY = DAOFactory.getInstance();
    private static final AccountDAO ACCOUNT_DAO = DAO_FACTORY.getAccountDAO();

    @Override
    public void createAccount(int currencyId, int bankId, int userId) throws ServiceException {
        try {
            ACCOUNT_DAO.createAccount(currencyId,bankId,userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
    @Override
    public int deposit(BigDecimal amount, int accountId) throws ServiceException {
        try {
            return ACCOUNT_DAO.deposit(amount,accountId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
    @Override
    public int transfer(BigDecimal amount, int fromAccountId, int toAccountId, BigDecimal exchangeRate) throws ServiceException {
        try {
            return ACCOUNT_DAO.transfer(amount,fromAccountId,toAccountId,exchangeRate);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
    @Override
    public int withdrawal(BigDecimal amount, int accountId) throws ServiceException {
        try {
            return ACCOUNT_DAO.withdrawal(amount,accountId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void calculateInterest(double percentage) throws ServiceException {
        try {
            ACCOUNT_DAO.calculateInterest(percentage);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Account getAccountById(int idAccount) throws ServiceException {
        try {
            return ACCOUNT_DAO.getAccountById(idAccount);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Transaction> getAllTransactionByAccountId(int idAccount) throws ServiceException {
        try {
            return ACCOUNT_DAO.getAllTransactionByAccountId(idAccount);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Transaction getTransactionById(int transactionId) throws ServiceException {
        try {
            return ACCOUNT_DAO.getTransactionById(transactionId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
    @Override
    public Account getAccountInfoDetailById(int id) throws ServiceException {
        try {
            return ACCOUNT_DAO.getAccountInfoDetailById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
    @Override
    public List<AccountShortInfo> getAllAccountByUserId(int userId) throws ServiceException {
        try {
            return ACCOUNT_DAO.getAllAccountByUserId(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
