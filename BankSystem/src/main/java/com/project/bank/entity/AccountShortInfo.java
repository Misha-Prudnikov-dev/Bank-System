package com.project.bank.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The AccountShortInfo represents short information about
 * bank account.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountShortInfo {

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
     * The account belongs to the bank
     */
    private String nameBank;

    /**
     * Currency of bank account
     */
    private String currency;

}
