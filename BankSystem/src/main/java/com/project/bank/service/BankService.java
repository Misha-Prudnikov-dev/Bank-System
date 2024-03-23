package com.project.bank.service;

import com.project.bank.entity.Bank;

import java.util.List;

/**
 The BankService interface provides methods for managing banks.
 */
public interface BankService {

    /**
     Creates a new bank with the specified name.
     @param nameBank The name of the bank.
     @throws ServiceException If an error occurs while creating the bank.
     */
    void createBank(String nameBank) throws ServiceException;

    /**
     Retrieves a list of all banks.
     @return A list of banks.
     @throws ServiceException If an error occurs while retrieving the banks.
     */
    List<Bank> getAllBank()throws ServiceException;
}
