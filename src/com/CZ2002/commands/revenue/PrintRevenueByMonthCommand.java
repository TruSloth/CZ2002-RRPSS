package com.CZ2002.commands.revenue;

import com.CZ2002.entities.SalesRevenue;
import com.CZ2002.exceptions.InvalidSalesRevenueQueryException;
import com.CZ2002.interfaces.ICommand;
import com.CZ2002.interfaces.IDateFormattable;
import com.CZ2002.managers.SalesRevenueManager;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class implements {@link ICommand} to complete the 'print revenue by month' action.
 */
public class PrintRevenueByMonthCommand implements ICommand<ArrayList<SalesRevenue>, InvalidSalesRevenueQueryException>, IDateFormattable {
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

    public PrintRevenueByMonthCommand(SalesRevenueManager salesRevenueManager, Date startDate, Date endDate){
        this.salesRevenueManager = salesRevenueManager;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Completes the 'print revenue by month' action.
     * @return  the requested {@code Reservation} instance
     * @throws InvalidSalesRevenueQueryException for invaldi start and end date
     */
    @Override
    public ArrayList<SalesRevenue> execute() throws InvalidSalesRevenueQueryException {
        if  (startDate.compareTo(endDate) > 0) {
            throw new InvalidSalesRevenueQueryException("Invalid Start and End Date!");
        }
        else {
            return salesRevenueManager.getSalesRevenueByMonth(startDate, endDate);
        }
    }
}
