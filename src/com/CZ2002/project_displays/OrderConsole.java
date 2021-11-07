package com.CZ2002.project_displays;
import com.CZ2002.project_boundaries.MenuManager;
import com.CZ2002.project_boundaries.SalesRevenueManager;
import com.CZ2002.project_boundaries.StaffManager;
import com.CZ2002.project_commands.*;
import com.CZ2002.project_boundaries.OrderManager;
import com.CZ2002.project_enums.MenuView;
import com.CZ2002.project_exceptions.*;
import com.CZ2002.project_interfaces.ICommand;
import com.CZ2002.project_utils.MenuBuilder;

import java.awt.*;
import java.util.Scanner;

/**
 * A boundary class that takes in inputs from user
 */
public class OrderConsole extends ConsoleDisplay{
    private ICommand<Void, InvalidCreateOrderException> CreateOrderCommand;
    private ICommand<Void, InvalidAddItemOrderException> AddItemOrderCommand;
    private ICommand<Void, InvalidRemoveItemOrderException> RemoveItemOrderCommand;
    private ICommand<Void, InvalidPrintOrderException> PrintOrderCommand;
    private ICommand<Void, InvalidDeleteOrderException> DeleteOrderCommand;

    @Override
    public int displayConsoleOptions() {
        String[] options = new String[]{
                "Create Order",
                "Add Item to Order",
                "Remove Item from order",
                "Print Order",
                "Close Order",
                "Back"
        };
        String title = "Order";
        System.out.println(MenuBuilder.buildMenu(title, options));
        return options.length;
    }

    @Override
    public MenuView handleConsoleOptions() {
        int choice;
        MenuView view = MenuView.ORDERS;
        Scanner input_console = new Scanner(System.in);
        choice = input_console.nextInt();
        switch (choice) {
            case 1:
                // Create Order
                int table_createOrder, pax;
                int serverId;
                System.out.printf("Table: ");
                Scanner input_table_createOrder = new Scanner(System.in);
                table_createOrder = input_table_createOrder.nextInt();

                System.out.printf("Pax: ");
                Scanner input_pax = new Scanner(System.in);
                pax = input_pax.nextInt();

                System.out.printf("Server's ID: ");
                Scanner input_server = new Scanner(System.in);
                serverId = input_server.nextInt();

                CreateOrderCommand createNewOrder = new CreateOrderCommand(
                        mainManager.getSubManager("staffManager", StaffManager.class),
                        mainManager.getSubManager("orderManager", OrderManager.class),
                        table_createOrder, pax, serverId
                );

            case 2:
                int tableAdd;
                String addItem;
                System.out.print("Add item to order for which table: ");
                Scanner input_table_add = new Scanner(System.in);
                tableAdd = input_table_add.nextInt();

                System.out.printf("What item to add to order: ");
                Scanner input_add = new Scanner(System.in);
                addItem = input_add.nextLine();
                System.out.println("Adding Item to Order for Table " + tableAdd);

                AddItemOrderCommand addItemOrder = new AddItemOrderCommand(
                        mainManager.getSubManager("menuManager", MenuManager.class),
                        mainManager.getSubManager("orderManager", OrderManager.class)
                        , tableAdd, addItem
                );

            case 3:
                int tableRemove;
                String removeItem;
                System.out.printf("Remove item from order for which table: ");
                Scanner input_table_remove = new Scanner(System.in);
                tableRemove = input_table_remove.nextInt();

                System.out.printf("Which item to remove from order: ");
                Scanner input_remove = new Scanner(System.in);
                removeItem = input_remove.nextLine();
                System.out.println("Removing Item from Order for Table " + tableRemove);

                addItemOrder = new AddItemOrderCommand(
                        mainManager.getSubManager("menuManager", MenuManager.class),
                        mainManager.getSubManager("orderManager", OrderManager.class)
                        , tableRemove, removeItem
                );

            case 4:
                int tablePrint;
                System.out.print("Print order for which table: ");
                Scanner input_table_print = new Scanner(System.in);
                tablePrint = input_table_print.nextInt();

                PrintOrderCommand printOrder = new PrintOrderCommand(
                        mainManager.getSubManager("orderManager", OrderManager.class)
                        , tablePrint
                );
                break;
            case 5:
                int tableClose;
                System.out.print("Close order for which table: ");
                Scanner input_table_close = new Scanner(System.in);
                tableClose = input_table_close.nextInt();

                DeleteOrderCommand deleteOrder = new DeleteOrderCommand(
                        mainManager.getSubManager("orderManager", OrderManager.class)
                        , mainManager.getSubManager("salesRevenueManager", SalesRevenueManager.class)
                        , tableClose
                );
                System.out.println("Table " + tableClose + " is now vacant");
                break;
            case 6:
                view = MenuView.PREVIOUS_MENU;
            default:
                System.out.println("Invalid operation! Please enter again");
        }
        return view;
    }
}