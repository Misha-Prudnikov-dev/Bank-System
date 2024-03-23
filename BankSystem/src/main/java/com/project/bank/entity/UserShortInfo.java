package com.project.bank.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This Class represents short information about
 * user.
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserShortInfo {

    /**
     * Account ID
     */
    private int accountId;

    /**
     * To Account ID
     */
    private int toAccountId;

    /**
     * User ID
     */
    private int userId;

    /**
     * First name user
     */
    private String firstName;

    /**
     * Last name user
     */
    private String lastName;

}
