package com.project.bank.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * The Account represents complete information about
 * bank account.
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account {

    /**
     * Id of bank account
     */
    private int id;

    /**
     * Balance of bank account
     */
    private BigDecimal balance;

    /**
     * Date created of bank account
     */
    private Date createdDate;

    /**
     * Currency of bank account
     */
    private Currency currency;

    /**
     * List transactions of bank account
     */
    private List<Transaction> transactions;

    /**
     * The account belongs to the bank
     */
    private Bank bank;

    /**
     * The account belongs to the user
     */
    private User user;

}