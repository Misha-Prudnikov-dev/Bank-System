package com.project.bank.service;

/**
 * This Class represents Exception for service.
 * If password already exists.
 */
public class PasswordAlreadyExistsServiceException extends Exception {

    private static final long serialVersionUID = 1L;

    public PasswordAlreadyExistsServiceException() {
        super();
    }

    public PasswordAlreadyExistsServiceException(String message) {
        super(message);
    }

    public PasswordAlreadyExistsServiceException(Exception e) {
        super(e);
    }

    public PasswordAlreadyExistsServiceException(String message, Exception e) {
        super(message, e);
    }
}
