package com.CZ2002.project_displays;
import com.CZ2002.project_boundaries.*;
import com.CZ2002.project_commands.order.*;
import com.CZ2002.project_entities.Order;
import com.CZ2002.project_enums.MenuView;
import com.CZ2002.project_exceptions.*;
import com.CZ2002.project_exceptions.order.InvalidAddItemOrderException;
import com.CZ2002.project_exceptions.order.InvalidCreateOrderException;
import com.CZ2002.project_exceptions.order.InvalidDeleteOrderException;
import com.CZ2002.project_exceptions.order.InvalidPrintOrderException;
import com.CZ2002.project_exceptions.order.InvalidSetMembership;
import com.CZ2002.project_interfaces.ICommand;
import com.CZ2002.project_utils.MenuBuilder;

import java.text.ParseException;
import java.util.Scanner;

/**
 * The interface that the user interacts with for {@link Order} instances.
 */
public class OrderConsole extends ConsoleDisplay{

    /**
     * Initalises this {@code ReservationConsole} with the reference to the {@link RestaurantManager}.
     *
     * @param restaurantManager  the reference to the {@code RestaurantManager} to be used
     * @param sc  the {@link Scanner} instance used by the boundary layer
     */
    public OrderConsole(RestaurantManager restaurantManager, Scanner sc){
        super.mainManager = restaurantManager;
        super.sc = sc;
    }

    /**
     * Formats and output information enclosed in given {@link Order} instances.
     * @param order the {@code Order} whose details are to be shown
     * @return The number of options used
     */
    private int displayOrder(Order order) {
        String title = "Bill";
        int longestWidth = 40;
        String[] optionHeaders = new String[order.ordered.size() + 4];
        String[] options = new String[order.ordered.size() + 4];
        optionHeaders[0] = "Table";
        options[0] = String.format("%d", order.getTable());


        for ( int i = 1 ;  i < order.ordered.size() + 1 ; i++ ){
            optionHeaders[i] = order.ordered.get(i - 1).getName();
            options[i] = String.format("%.2f", order.ordered.get(i - 1).getPrice());
        }

        optionHeaders[order.ordered.size()+1] = "Total Discount Applied: ";
        options[order.ordered.size()+1] =  String.format("%.2f", order.getDiscountTotal());
        optionHeaders[order.ordered.size()+2] = "Tax: ";
        options[order.ordered.size()+2] =  String.format("%.2f", order.getTax());
        optionHeaders[order.ordered.size()+3] = "Total Bill: ";
        options[order.ordered.size()+3] =  String.format("%.2f", order.getBill());
        System.out.println(MenuBuilder.buildMenu(title, optionHeaders, options, longestWidth));

        return options.length;
    }

    /**
     * Formats and outputs the possible actions that can be taken on this {@code ReservationConsole}.
     * @see  ConsoleDisplay
     */
    @Override
    public int displayConsoleOptions() {
        String[] options = new String[]{
                "Create Order",
                "Add Item to Order",
                "Remove Item from order",
                "Print Order",
                "Close Order",
                "Set Membership",
                "Back"
        };
        String title = "Order";
        System.out.println(MenuBuilder.buildMenu(title, options));
        return options.length;
    }

    /**
     * Formats and outputs the possible ways to update a {@link Order} instance.
     * @return  the number of options to be displayed
     */
    @Override
    public MenuView handleConsoleOptions() {
        int choice = sc.nextInt();
        MenuView view = MenuView.ORDERS;
        switch (choice) {
            case 1:
                // Create Order
                sc.nextLine();
                int pax;
                int serverId;

                System.out.printf("Pax: ");
                pax = sc.nextInt();

                System.out.printf("Server's ID: ");
                serverId = sc.nextInt();

                ICommand<Void, InvalidCreateOrderException> createOrderCommand  = new CreateOrderCommand(
                        mainManager.getSubManager("staffManager", StaffManager.class),
                        mainManager.getSubManager("orderManager", OrderManager.class),
                        mainManager.getSubManager("reservationManager", ReservationManager.class),
                        mainManager.getSubManager("tableManager", TableManager.class),
                        pax, serverId

                );

                try {
                    createOrderCommand.execute();
                } catch (InvalidCreateOrderException | ParseException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case 2:
                // Add item to Order
                sc.nextLine();
                int tableAdd;
                String addItem;
                System.out.print("Table to Add Item To Order: ");
                tableAdd = sc.nextInt();

                sc.nextLine(); // throw away \n in buffer
                System.out.printf("Item to Add To Order: ");
                addItem = sc.nextLine();

                ICommand<Void, InvalidAddItemOrderException> addItemOrder = new AddItemOrderCommand(
                        mainManager.getSubManager("menuManager", MenuManager.class),
                        mainManager.getSubManager("orderManager", OrderManager.class)
                        , tableAdd, addItem
                );

                try {
                    addItemOrder.execute();
                } catch (InvalidAddItemOrderException | ParseException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case 3:
                //  Remove item from order
                int tableRemove;
                String removeItem;
                System.out.printf("Table to Remove Item From Order: ");
                tableRemove = sc.nextInt();

                System.out.printf("Item to Remove From Order: ");
                sc.nextLine();
                removeItem = sc.nextLine();

                ICommand<Void , InvalidRemoveItemOrderException> removeItemOrder = new RemoveItemOrderCommand(
                        mainManager.getSubManager("menuManager", MenuManager.class),
                        mainManager.getSubManager("orderManager", OrderManager.class)
                        , tableRemove, removeItem
                );

                try {
                    removeItemOrder.execute();
                } catch (InvalidRemoveItemOrderException | ParseException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case 4:
                // Print Order for Table xx
                sc.nextLine();
                int tablePrint;
                System.out.print("Print Order For Table: ");
                tablePrint = sc.nextInt();

                ICommand<Order, InvalidPrintOrderException> printOrder = new PrintOrderCommand(
                        mainManager.getSubManager("orderManager", OrderManager.class)
                        , tablePrint
                );
                try {
                    Order order = printOrder.execute();
                    displayOrder(order);
                } catch (InvalidPrintOrderException | ParseException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case 5:
                // Delete Order
                sc.nextLine();
                int tableClose;
                System.out.print("Close Order For Table: ");
                tableClose = sc.nextInt();

                ICommand<Void , InvalidDeleteOrderException> deleteOrder = new DeleteOrderCommand(
                        mainManager.getSubManager("orderManager", OrderManager.class),
                        mainManager.getSubManager("tableManager", TableManager.class)
                        , mainManager.getSubManager("salesRevenueManager", SalesRevenueManager.class)
                        , tableClose          
                );

                try {
                    deleteOrder.execute();
                } catch (InvalidDeleteOrderException | ParseException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 6:
                int membership;
                System.out.println("1: Membership\n0: No Membership\nEnter Choice: ");
                sc.nextLine();
                membership= sc.nextInt();

                int tableSetMembership;
                System.out.println("Set Membership For Table: ");
                tableSetMembership = sc.nextInt();


                ICommand<Void, InvalidSetMembership> setMembership = new SetMembershipOrderCommand(
                        mainManager.getSubManager("orderManager", OrderManager.class),
                        membership, tableSetMembership
                );
                try {
                    setMembership.execute();
                } catch (InvalidSetMembership | ParseException e) {
                    System.out.println(e.getMessage());
                }

                break;

            case 7:
                view = MenuView.PREVIOUS_MENU;
                break;
            default:
                System.out.println("Invalid operation! Please enter again");
        }
        return view;
    }
}