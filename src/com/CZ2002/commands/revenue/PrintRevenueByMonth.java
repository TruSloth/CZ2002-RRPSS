package com.CZ2002.commands.revenue;

import com.CZ2002.boundaries.SalesRevenueManager;
import com.CZ2002.entities.SalesRevenue;
import com.CZ2002.interfaces.ICommand;
import com.CZ2002.interfaces.IDateFormatter;

import java.util.ArrayList;
import java.util.Date;
import com.CZ2002.exceptions.InvalidSalesRevenueQueryException;

/**
 * This class implements {@link ICommand} to complete the 'print revenue by month' action.
 */
public class PrintRevenueByMonth implements ICommand<ArrayList<SalesRevenue>, InvalidSalesRevenueQueryException>, IDateFormatter {
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
     * @return  the requested {@code Reservation} instance
     *
     */
    @Override
    public ArrayList<SalesRevenue> execute() {
        return salesRevenueManager.getSalesRevenueByMonth(startDate, endDate);
    }
}
