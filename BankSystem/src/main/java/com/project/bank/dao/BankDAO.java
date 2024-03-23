package com.project.bank.dao;


import com.project.bank.dao.exception.DAOException;
import com.project.bank.entity.Account;
import com.project.bank.entity.Bank;

import java.util.List;

/**
 * The BankDAO interface provides methods for interacting with the bank data in the database.
 */
public interface BankDAO {

    /**
     Creates a new bank with the specified name.
     @param nameBank The name of the bank.
     @throws DAOException If an error occurs while creating the bank.
     */
    void createBank(String nameBank) throws DAOException;

    /**
     Retrieves all banks.
     @return A list of all banks.
     @throws DAOException If an error occurs while retrieving the banks.
     */
    List<Bank> getAllBank()throws DAOException;

}
