package com.project.bank.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * This Class represents information about
 * transaction.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transaction {

    /**
     * Id of transaction
     */
    private int id;

    /**
     * The amount which the transaction was made
     */
    private BigDecimal amount;

    /**
     * Date created of transaction
     */
    private LocalDateTime dateTransaction;

    /**
     * Type of transaction
     */
    private TransactionType transactionType;

    /**
     * Currency which the transaction was made
     */
    private Currency currency;

    /**
     *
     */
    private UserShortInfo toUserShortInfo;

}
