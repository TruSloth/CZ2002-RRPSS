package com.CZ2002.project_commands.revenue;

import com.CZ2002.project_entities.SalesRevenue;
import com.CZ2002.project_exceptions.InvalidSalesRevenueQueryException;
import com.CZ2002.project_interfaces.ICommand;
import com.CZ2002.project_interfaces.IDateFormattable;
import com.CZ2002.project_managers.SalesRevenueManager;

import java.text.ParseException;
import java.util.Date;

/**
 * This class implements {@link ICommand} to complete the 'print revenue by month' action.
 */
public class PrintRevenueByDayCommand implements ICommand<SalesRevenue, InvalidSalesRevenueQueryException>, IDateFormattable {
    private SalesRevenueManager salesRevenueManager;
    private Date queryDate;

    /**
     * Constructor that accepts the necessary parameters for {@code execute} to successfully complete.
     *
     * @param salesRevenueManager  the reference to the Restaurant's {@link SalesRevenueManager}
     * @param queryD  the Date representing the query date to query for revenue
     */

    public PrintRevenueByDayCommand(SalesRevenueManager salesRevenueManager, Date queryD) {
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