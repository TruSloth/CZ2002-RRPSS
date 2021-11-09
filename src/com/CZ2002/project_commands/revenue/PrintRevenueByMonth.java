package com.CZ2002.project_commands.revenue;

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
public class PrintRevenueByMonth implements ICommand<Void, InvalidSalesRevenueQueryException>, IDateFormatter {
    private SalesRevenueManager salesRevenueManager;
    private Date startDate;
    private Date endDate;

    /**
     * Constructor that accepts the necessary parameters for {@code execute} to successfully complete.
     *
     * @param salesRevenueManager  the reference to the Restaurant's {@link SalesRevenueManager}
     * @param startDate  the Date representing the start date to query for revenue
     * @param endDate  the Date representing the end date to query for reenue
     */

    public PrintRevenueByMonth(SalesRevenueManager salesRevenueManager, Date startDate, Date endDate){
        this.salesRevenueManager = salesRevenueManager;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Completes the 'print revenue by month' action.
     *
     * @return  the requested {@code Reservation} instance
     * @throws InvalidSalesRevenueQueryException  if an error is reached while parsing
     */
    @Override
    public Void execute() throws InvalidSalesRevenueQueryException {
        try {
            salesRevenueManager.printByMonth(startDate, endDate);
        } catch (NoSuchElementException | ParseException e){
            throw new InvalidSalesRevenueQueryException("The requested period is invalid.");
        }
        return null;
    }
}
