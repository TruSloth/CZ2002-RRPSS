package com.CZ2002.project_commands.order;

import com.CZ2002.project_boundaries.OrderManager;
import com.CZ2002.project_exceptions.order.InvalidAddItemOrderException;
import com.CZ2002.project_exceptions.order.InvalidSetMembership;
import com.CZ2002.project_interfaces.ICommand;

import java.util.NoSuchElementException;

/**
 * This class implements {@link ICommand} to complete the 'Set Membership' action.
 */
public class SetMembershipOrderCommand implements ICommand<Void , InvalidSetMembership> {
    OrderManager orderManager;
    int tableSetMembership;
    int membership;

    /**
     * Constructor that accepts the necessary parameters for {@code execute} to successfully complete.
     * @param orderManager The OrderManager object that controls Order objects
     * @param membership The boolean value to which if customer is a member
     * @param tableSetMembership The table Number which Membership is to be set
     */
    public SetMembershipOrderCommand(OrderManager orderManager , int membership , int tableSetMembership) {
        this.orderManager = orderManager;
        this.tableSetMembership = tableSetMembership;
        this.membership=membership;
    }

    /**
     * Completes the 'Set Membership' action.
     *
     * @return Void
     * @throws InvalidSetMembership if the table could not be located
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
