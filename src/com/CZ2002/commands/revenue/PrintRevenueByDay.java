package com.CZ2002.commands.revenue;

import com.CZ2002.boundaries.SalesRevenueManager;
import com.CZ2002.entities.SalesRevenue;
import com.CZ2002.interfaces.ICommand;
import com.CZ2002.interfaces.IDateFormatter;

import java.text.ParseException;
import java.util.Date;
import com.CZ2002.exceptions.InvalidSalesRevenueQueryException;

/**
 * This class implements {@link ICommand} to complete the 'print revenue by month' action.
 */
public class PrintRevenueByDay implements ICommand<SalesRevenue, InvalidSalesRevenueQueryException>, IDateFormatter {
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
    public SalesRevenue execute() throws InvalidSalesRevenueQueryException, ParseException {
        return salesRevenueManager.getSalesRevenueByDay(queryDate);
    }
}
