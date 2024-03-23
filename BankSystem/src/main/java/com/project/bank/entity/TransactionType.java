package com.project.bank.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This Class represents information about
 * type transaction.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionType {

    /**
     * Id of type transaction
     */
    private int id;

    /**
     * Name of type transaction
     */
    private String name;

}
