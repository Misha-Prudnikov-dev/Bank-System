package com.project.bank;

import com.project.bank.service.AccountService;
import com.project.bank.service.ServiceException;
import com.project.bank.service.ServiceFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 The InterestCalculator class is responsible for scheduling and executing the calculation of interest on accounts.
 */
public class InterestCalculator {

    private static final double percentage = 0.025;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    ServiceFactory serviceFactory = ServiceFactory.getInstance();
    AccountService accountService = serviceFactory.getAccountService();

    /**
     Starts the interest calculation process by scheduling it to run at fixed intervals.
     */
    public void startInterestCalculation() {
        scheduler.scheduleAtFixedRate(this::calculateInterest, 0, 30, TimeUnit.DAYS);
    }

    /**
     Calculates and applies interest to all eligible accounts.
     */
    private void calculateInterest() {
        try {
            accountService.calculateInterest(percentage);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }


}
