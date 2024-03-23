package com.project.bank.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The Bank represents information about
 * bank.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Bank {

    /**
     * Id of bank
     */
    private int id;

    /**
     * Name of bank
     */
    private String name;

}
