package com.CZ2002.project_displays;
import com.CZ2002.project_boundaries.*;
import com.CZ2002.project_commands.order.*;
import com.CZ2002.project_enums.MenuView;
import com.CZ2002.project_exceptions.*;
import com.CZ2002.project_exceptions.order.InvalidAddItemOrderException;
import com.CZ2002.project_exceptions.order.InvalidDeleteOrderException;
import com.CZ2002.project_exceptions.order.InvalidPrintOrderException;
import com.CZ2002.project_exceptions.order.InvalidSetMembership;
import com.CZ2002.project_interfaces.ICommand;
import com.CZ2002.project_utils.MenuBuilder;

import java.text.ParseException;
import java.util.Scanner;

/**
 * A boundary class that takes in inputs from user
 */
public class OrderConsole extends ConsoleDisplay{
    public OrderConsole(RestaurantManager restaurantManager, Scanner sc){
        super.mainManager = restaurantManager;
        super.sc = sc;
    }

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

                ICommand<Void,InvalidStaffException> createOrderCommand  = new CreateOrderCommand(
                        mainManager.getSubManager("staffManager", StaffManager.class),
                        mainManager.getSubManager("orderManager", OrderManager.class),
                        mainManager.getSubManager("reservationManager", ReservationManager.class),
                        mainManager.getSubManager("tableManager", TableManager.class),
                        pax, serverId

                );

                try {
                    createOrderCommand.execute();
                } catch (InvalidStaffException | ParseException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case 2:
                // Add item to Order
                sc.nextLine();
                int tableAdd;
                String addItem;
                System.out.print("Add item to order for which table: ");
                tableAdd = sc.nextInt();

                sc.nextLine(); // throw away \n in buffer
                System.out.printf("What item to add to order: ");
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
                sc.nextLine();
                int tableRemove;
                String removeItem;
                System.out.printf("Remove item from order for which table: ");
                tableRemove = sc.nextInt();

                System.out.printf("Which item to remove from order: ");
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
                System.out.print("Print Order for Which Table: ");
                tablePrint = sc.nextInt();

                ICommand<Void , InvalidPrintOrderException> printOrder = new PrintOrderCommand(
                        mainManager.getSubManager("orderManager", OrderManager.class)
                        , tablePrint
                );
                try {
                    printOrder.execute();
                } catch (InvalidPrintOrderException | ParseException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case 5:
                // Delete Order
                sc.nextLine();
                int tableClose;
                System.out.print("Close Order for Which Table: ");
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
                System.out.println("Set Membership for which table");
                tableSetMembership = sc.nextInt();
                ICommand<Void , InvalidSetMembership>  setMembership = new SetMembershipOrderCommand(
                        mainManager.getSubManager("orderManager", OrderManager.class) ,
                         membership , tableSetMembership
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