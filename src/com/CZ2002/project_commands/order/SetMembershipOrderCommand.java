package com.CZ2002.project_commands.order;

import com.CZ2002.project_boundaries.OrderManager;
import com.CZ2002.project_exceptions.InvalidDeleteOrderException;
import com.CZ2002.project_exceptions.InvalidSetMembership;
import com.CZ2002.project_interfaces.ICommand;

import java.util.NoSuchElementException;

public class SetMembershipOrderCommand implements ICommand<Void , InvalidSetMembership> {
    OrderManager orderManager;
    int tableSetMembership;
    int membership;

    public SetMembershipOrderCommand(OrderManager orderManager , int membership , int tableSetMembership) {
        this.orderManager = orderManager;
        this.tableSetMembership = tableSetMembership;
        this.membership=membership;
    }

    /**
     * Setting membership for the table
     */
    @Override
    public Void execute() throws InvalidSetMembership {
        try {
            orderManager.setMembership( tableSetMembership , membership );
        } catch (NoSuchElementException e) {
            throw new InvalidSetMembership("Order not found for Table " + tableSetMembership);
        }

        return null;
    }
}
