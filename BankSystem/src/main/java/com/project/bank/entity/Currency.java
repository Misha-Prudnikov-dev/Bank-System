package com.project.bank.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Currency represents information about
 * currency.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Currency {

    /**
     * Id of currency
     */
    private int id;

    /**
     * Nane of currency
     */
    private String name;

}
