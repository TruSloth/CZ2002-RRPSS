package com.CZ2002.project_exceptions;

public class InvalidSalesRevenueQueryException extends Exception {
    public InvalidSalesRevenueQueryException() {
        super();
    }

    public InvalidSalesRevenueQueryException(String message) {
        super(message);
    }
}
