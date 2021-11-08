package com.CZ2002.project_commands.revenue.reservations;

import com.CZ2002.project_boundaries.SalesRevenueManager;
import com.CZ2002.project_interfaces.ICommand;
import com.CZ2002.project_interfaces.IDateFormatter;

import java.text.ParseException;
import java.util.Date;
import java.util.NoSuchElementException;

import com.CZ2002.project_exceptions.InvalidSalesRevenueQueryException;

/**
 * This class implements {@link ICommand} to complete the 'print revenue by month' action.
 */
public class PrintRevenueByDay implements ICommand<Void, InvalidSalesRevenueQueryException>, IDateFormatter {
    private SalesRevenueManager salesRevenueManager;
    private Date queryDate;

    /**
     * Constructor that accepts the necessary parameters for {@code execute} to successfully complete.
     *
     * @param salesRevenueManager  the reference to the Restaurant's {@link SalesRevenueManager}
     * @param queryD  the Date representing the query date to query for revenue
     */

    public PrintRevenueByDay(SalesRevenueManager salesRevenueManager, Date queryD) {
        this.salesRevenueManager = salesRevenueManager;
        this.queryDate = queryD;
    }

    /**
     * Completes the 'print revenue by day' action.
     *
     * @return  the requested {@code Reservation} instance
     * @throws InvalidSalesRevenueQueryException  if an error is reached while parsing
     */
    @Override
    public Void execute() throws InvalidSalesRevenueQueryException, ParseException {
        try {
            salesRevenueManager.printByDay(queryDate);
        } catch (NoSuchElementException e){
            throw new InvalidSalesRevenueQueryException("The requested period is invalid.");
        }
        return null;
    }
}
