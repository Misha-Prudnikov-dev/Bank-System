package com.project.bank.dao.exception;

/**
 * This Class represents Exception for dao.
 * If password already exists.
 */
public class PasswordAlreadyExistsDAOException extends Exception {

    private static final long serialVersionUID = 1L;

    public PasswordAlreadyExistsDAOException() {
        super();
    }

    public PasswordAlreadyExistsDAOException(String message) {
        super(message);
    }

    public PasswordAlreadyExistsDAOException(Exception e) {
        super(e);
    }

    public PasswordAlreadyExistsDAOException(String message, Exception e) {
        super(message, e);
    }
}
