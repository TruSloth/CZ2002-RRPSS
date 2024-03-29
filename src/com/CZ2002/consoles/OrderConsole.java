package com.CZ2002.consoles;
import com.CZ2002.commands.order.*;
import com.CZ2002.entities.Order;
import com.CZ2002.enums.MenuView;
import com.CZ2002.exceptions.*;
import com.CZ2002.interfaces.ICommand;
import com.CZ2002.managers.*;
import com.CZ2002.utils.MenuBuilder;

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
        String[] optionHeaders = new String[order.getOrdered().size() + 5];
        String[] options = new String[order.getOrdered().size() + 5];
        optionHeaders[0] = "Table";
        options[0] = String.format("%d", order.getTable());
        optionHeaders[1] = "Staff";
        options[1] = order.getServer().getName();

        for ( int i = 1 ;  i < order.getOrdered().size() + 1 ; i++ ){
            optionHeaders[i + 1] = order.getOrdered().get(i - 1).getName();
            options[i + 1] = String.format("%.2f", order.getOrdered().get(i - 1).getPrice());
        }

        optionHeaders[order.getOrdered().size()+2] = "Total Discount Applied: ";
        options[order.getOrdered().size()+2] =  String.format("%.2f", order.getDiscountTotal());
        optionHeaders[order.getOrdered().size()+3] = "Tax: ";
        options[order.getOrdered().size()+3] =  String.format("%.2f", order.getTax());
        optionHeaders[order.getOrdered().size()+4] = "Total Bill: ";
        options[order.getOrdered().size()+4] =  String.format("%.2f", order.getBill());
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
                "View Order",
                "Print Order Invoice",
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

                ICommand<Order, InvalidCreateOrderException> createOrderCommand  = new CreateOrderCommand(
                        mainManager.getSubManager("staffManager", StaffManager.class),
                        mainManager.getSubManager("orderManager", OrderManager.class),
                        mainManager.getSubManager("reservationManager", ReservationManager.class),
                        mainManager.getSubManager("tableManager", TableManager.class),
                        pax, serverId

                );

                try {
                    Order order = createOrderCommand.execute();
                    System.out.println("New Order for Table " + order.getTable() + " Created");

                } catch (InvalidCreateOrderException | ParseException | InvalidSalesRevenueQueryException e) {
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
                    System.out.println("Item Successfully Added");
                } catch (InvalidAddItemOrderException | ParseException | InvalidSalesRevenueQueryException e) {
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
                    System.out.println("Item Successfully Removed");
                } catch (InvalidRemoveItemOrderException | ParseException | InvalidSalesRevenueQueryException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case 4:
                // Print Order for Table xx
                sc.nextLine();
                int tablePrint;
                System.out.print("View Order For Table: ");
                tablePrint = sc.nextInt();

                ICommand<Order, InvalidPrintOrderException> printOrder = new PrintOrderCommand(
                        mainManager.getSubManager("orderManager", OrderManager.class)
                        , tablePrint
                );
                try {
                    Order order = printOrder.execute();
                    displayOrder(order);
                } catch (InvalidPrintOrderException | ParseException | InvalidSalesRevenueQueryException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case 5:
                // Delete Order
                sc.nextLine();
                int tableClose;
                System.out.print("Print Order Invoice For Table: ");
                tableClose = sc.nextInt();

                ICommand<Order , InvalidDeleteOrderException> deleteOrder = new DeleteOrderCommand(
                        mainManager.getSubManager("orderManager", OrderManager.class),
                        mainManager.getSubManager("tableManager", TableManager.class)
                        , mainManager.getSubManager("salesRevenueManager", SalesRevenueManager.class)
                        , tableClose          
                );

                try {
                    Order order = deleteOrder.execute();
                    displayOrder(order);
                    System.out.printf("Order for Table %d Paid\n", tableClose);
                } catch (InvalidDeleteOrderException | ParseException | InvalidSalesRevenueQueryException e) {
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
                } catch (InvalidSetMembership | ParseException | InvalidSalesRevenueQueryException e) {
                    System.out.println(e.getMessage());
                }

                break;

            case 7:
                view = MenuView.PREVIOUS_MENU;
                break;
            default:
                System.out.println("Invalid Operation! Please Enter Again");
        }
        return view;
    }
}