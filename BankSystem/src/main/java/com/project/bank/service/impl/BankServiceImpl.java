package com.project.bank.service.impl;

import com.project.bank.dao.BankDAO;
import com.project.bank.dao.DAOFactory;
import com.project.bank.dao.UserDAO;
import com.project.bank.dao.exception.DAOException;
import com.project.bank.entity.Bank;
import com.project.bank.service.BankService;
import com.project.bank.service.ServiceException;

import java.util.List;

public class BankServiceImpl implements BankService {

    private static final DAOFactory DAO_FACTORY = DAOFactory.getInstance();
    private static final BankDAO BANK_DAO = DAO_FACTORY.getBankDAO();
    @Override
    public void createBank(String nameBank) throws ServiceException {
        try {
            BANK_DAO.createBank(nameBank);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Bank> getAllBank() throws ServiceException {
        try {
            return BANK_DAO.getAllBank();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
