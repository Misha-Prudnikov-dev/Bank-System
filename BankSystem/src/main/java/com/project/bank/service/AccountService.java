package com.project.bank.service;

import com.project.bank.dao.exception.DAOException;
import com.project.bank.entity.Account;
import com.project.bank.entity.AccountShortInfo;
import com.project.bank.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;
/**
 The AccountService interface provides methods for managing user accounts and transactions.
 */

public interface AccountService {

    /**
     Creates a new account with the specified currency, bank, and user.
     @param currencyId The ID of the currency for the account.
     @param bankId The ID of the bank for the account.
     @param userId The ID of the user for the account.
     @throws ServiceException If an error occurs while creating the account.
     */
    void createAccount(int currencyId, int bankId, int userId) throws ServiceException;

    /**
     Deposits the specified amount into the account.
     @param amount The amount to deposit.
     @param accountId The ID of the account.
     @return The new balance of the account.
     @throws ServiceException If an error occurs while depositing the amount.
     */
    int deposit(BigDecimal amount, int accountId)throws ServiceException;

    /**
     Transfers the specified amount from one account to another with the given exchange rate.
     @param amount The amount to transfer.
     @param fromAccountId The ID of the account to transfer from.
     @param toAccountId The ID of the account to transfer to.
     @param exchangeRate The exchange rate for the transfer.
     @return The new balance of the "from" account.
     @throws ServiceException If an error occurs while transferring the amount.
     */
    int transfer(BigDecimal amount, int fromAccountId, int toAccountId, BigDecimal exchangeRate)throws ServiceException;

    /**
     Withdraws the specified amount from the account.
     @param amount The amount to withdraw.
     @param accountId The ID of the account.
     @return The new balance of the account.
     @throws ServiceException If an error occurs while withdrawing the amount.
     */
    int withdrawal(BigDecimal amount, int accountId)throws ServiceException;

    /**
     Calculates and applies interest to all eligible accounts with the specified percentage.
     @param percentage The interest percentage.
     @throws ServiceException If an error occurs while calculating interest.
     */
    void calculateInterest(double percentage)throws ServiceException;

    /**
     Retrieves the account information for the account with the specified ID.
     @param idAccount The ID of the account.
     @return The account information.
     @throws ServiceException If an error occurs while retrieving the account information.
     */
    Account getAccountById(int idAccount)throws ServiceException;

    /**
     Retrieves all transactions associated with the account with the specified ID.
     @param idAccount The ID of the account.
     @return A list of transactions.
     @throws ServiceException If an error occurs while retrieving the transactions.
     */
    List<Transaction> getAllTransactionByAccountId(int idAccount)throws ServiceException;

    /**
     Retrieves the transaction with the specified ID.
     @param transactionId The ID of the transaction.
     @return The transaction.
     @throws ServiceException If an error occurs while retrieving the transaction.
     */
    Transaction getTransactionById(int transactionId)throws ServiceException;

    /**
     Retrieves detailed account information for the account with the specified ID.
     @param id The ID of the account.
     @return The detailed account information.
     @throws ServiceException If an error occurs while retrieving the account information.
     */
    Account getAccountInfoDetailById(int id) throws ServiceException;

    /**
     Retrieves all accounts associated with the user with the specified ID.
     @param userId The ID of the user.
     @return A list of account short information.
     @throws ServiceException If an error occurs while retrieving the accounts.
     */
    List<AccountShortInfo> getAllAccountByUserId(int userId) throws ServiceException;

}
