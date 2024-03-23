package com.project.bank.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * This Class represents complete information about
 * user.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    /**
     * User ID
     */
    private int id;

    /**
     * First name user
     */
    private String firstName;

    /**
     * Last name user
     */
    private String lastName;

    /**
     * Email user
     */
    private String email;

    /**
     * Date created user
     */
    private Date created;

    /**
     * user has list of accounts
     */
    private List<Account> accounts;

}
