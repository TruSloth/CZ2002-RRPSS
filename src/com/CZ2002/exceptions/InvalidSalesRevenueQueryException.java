package com.CZ2002.exceptions;
/**
 * The {@code InvalidSalesRevenueQueryException} is thrown to indicate that the date entered is invalid
 */
public class InvalidSalesRevenueQueryException extends Exception {
    public InvalidSalesRevenueQueryException() {
        super();
    }

    public InvalidSalesRevenueQueryException(String message) {
        super(message);
    }
}
