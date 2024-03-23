package com.project.bank.dao.connection;


/**
 * This Class represents RuntimeException for
 * Connection Pool.
 */

public class ConnectionPoolRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ConnectionPoolRuntimeException() {
        super();
    }

    public ConnectionPoolRuntimeException(String message) {
        super(message);
    }

    public ConnectionPoolRuntimeException(Exception e) {

    }

    public ConnectionPoolRuntimeException(String message, Exception e) {
        super(message, e);
    }

}