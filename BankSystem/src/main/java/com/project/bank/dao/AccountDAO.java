package com.project.bank.dao;

import com.project.bank.dao.exception.DAOException;
import com.project.bank.entity.Account;
import com.project.bank.entity.AccountShortInfo;
import com.project.bank.entity.Transaction;
import com.project.bank.service.ServiceException;

import java.math.BigDecimal;
import java.util.List;

/**
 The AccountDAO interface provides methods for interacting with the account data in the database.
 */
public interface AccountDAO {

    /**
     Creates a new account with the specified currency, bank, and user.
     @param currencyId The ID of the currency.
     @param bankId The ID of the bank.
     @param userId The ID of the user.
     @throws DAOException If an error occurs while creating the account.
     */
    void createAccount(int currencyId, int bankId, int userId) throws DAOException;

    /**
     Deposits the specified amount into the account with the given account ID.
     @param amount The amount to deposit.
     @param accountId The ID of the account.
     @return The updated balance of the account.
     @throws DAOException If an error occurs while depositing the amount.
     */
    int deposit(BigDecimal amount, int accountId)throws DAOException;

    /**
     Transfers the specified amount from one account to another.
     @param amount The amount to transfer.
     @param fromAccountId The ID of the account to transfer from.
     @param toAccountId The ID of the account to transfer to.
     @param exchangeRate The exchange rate for the transfer.
     @return The transaction ID.
     @throws DAOException If an error occurs while transferring the amount.
     */
    int transfer(BigDecimal amount, int fromAccountId, int toAccountId, BigDecimal exchangeRate)throws DAOException;

    /**
     Withdraws the specified amount from the account with the given account ID.
     @param amount The amount to withdraw.
     @param accountId The ID of the account.
     @return The updated balance of the account.
     @throws DAOException If an error occurs while withdrawing the amount.
     */
    int withdrawal(BigDecimal amount, int accountId)throws DAOException;

    /**
     Calculates interest for all accounts based on the specified percentage.
     @param percentage The interest percentage.
     @throws DAOException If an error occurs while calculating the interest.
     */
    void calculateInterest(double percentage) throws DAOException;

    /**
     Retrieves the account with the specified account ID.
     @param idAccount The ID of the account.
     @return The account with the specified ID.
     @throws DAOException If an error occurs while retrieving the account.
     */
    Account getAccountById(int idAccount)throws DAOException;

    /**
     Retrieves all transactions associated with the account with the specified account ID.
     @param idAccount The ID of the account.
     @return A list of transactions associated with the account.
     @throws DAOException If an error occurs while retrieving the transactions.
     */
    List<Transaction> getAllTransactionByAccountId(int idAccount)throws DAOException;

    /**
     Retrieves the transaction with the specified transaction ID.
     @param transactionId The ID of the transaction.
     @return The transaction with the specified ID.
     @throws DAOException If an error occurs while retrieving the transaction.
     */
    Transaction getTransactionById(int transactionId)throws DAOException;

    /**
     Retrieves the detailed information of the account with the specified account ID.
     @param id The ID of the account.
     @return The detailed information of the account.
     @throws DAOException If an error occurs while retrieving the account information.
     */
    Account getAccountInfoDetailById(int id) throws DAOException;

    /**
     Retrieves all accounts associated with the specified user ID.
     @param userId The ID of the user.
     @return A list of accounts associated with the user.
     @throws DAOException If an error occurs while retrieving the accounts.
     */
    List<AccountShortInfo> getAllAccountByUserId(int userId) throws DAOException;
}
