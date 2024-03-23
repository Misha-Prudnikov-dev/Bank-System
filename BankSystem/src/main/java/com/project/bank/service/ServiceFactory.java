package com.project.bank.service;

import com.project.bank.service.impl.AccountServiceImpl;
import com.project.bank.service.impl.BankServiceImpl;
import com.project.bank.service.impl.UserServiceImpl;

/**
 * The ServiceFactory class is responsible for creating instances of service interfaces.
 * It follows the Singleton design pattern to ensure only one instance of ServiceFactory exists.
 */
public class ServiceFactory {

    private static final ServiceFactory instance = new ServiceFactory();
    public static ServiceFactory getInstance() {
        return instance;
    }

    private ServiceFactory() {

    }

    private UserService userService = new UserServiceImpl();
    private AccountService accountService = new AccountServiceImpl();
    private BankService bankService = new BankServiceImpl();

    public UserService getUserService() {
        return userService;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public BankService getBankService() {
        return bankService;
    }
}


