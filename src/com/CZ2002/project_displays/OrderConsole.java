package com.CZ2002.project_displays;
import com.CZ2002.project_commands.*;
import com.CZ2002.project_boundaries.OrderManager;
import com.CZ2002.project_utils.MenuBuilder;

import java.util.Scanner;

/**
 * A boundary class that takes in inputs from user
 */
public class OrderConsole {
    /**
     * OrderConsole to print out the display console
     * Only takes in input and produce output
     * Does not process any logics
     * Input are passed into Commands for logic processing
     */
    public void orderConsole (){
        int choice = 0;
        MenuBuilder orderMenu = new MenuBuilder();
        String[] options = new String[6];
        options[0] = "Create Order";
        options[1] = "Add Item to Order";
        options[2] = "Remove Item from order";
        options[3] = "Print Order";
        options[4] = "Close Order";
        options[5] = "Quit";
        do {

            System.out.println(orderMenu.buildMenu("Order console", options));
            Scanner input_console = new Scanner(System.in);
            choice = input_console.nextInt();

            switch (choice) {
                case 1:
                    int table_createOrder, pax;
                    int server_id;
                    Scanner input_table_createOrder = new Scanner(System.in);
                    table_createOrder = input_table_createOrder.nextInt();
                    Scanner input_pax = new Scanner(System.in);
                    pax = input_pax.nextInt();

                    Scanner input_server = new Scanner(System.in);
                    server_id = input_server.nextInt();

                    // getSubManager ( String variable name for class retrieved , from which file)
                    createNewOrder = new CreateOrderCommand(
                            restaurantManager.getSubManager("staffManager", StaffManager.class),
                            restaurantManager.getSubManager("orderManager", OrderManager.class),
                            table_createOrder, pax, server_id
                    );

                case 2:
                    int table_add;
                    String addItem;
                    System.out.print("Add item to order for which table: ");
                    Scanner input_table_add = new Scanner(System.in);
                    table_add = input_table_add.nextInt();

                    Scanner input_add = new Scanner(System.in);
                    addItem = input_add.nextLine();
                    System.out.println("Adding Item to Order for Table " + table_add);

                    addItemOrder = new AddItemOrderCommand(
                            restaurantManager.getSubManager("menuManager", MenuManager.class),
                            restaurantManager.getSubManager("orderManager", OrderManager.class)
                            , table_add, addItem
                    );

                case 3:
                    int table_remove;
                    String removeItem;
                    System.out.print("Remove item from order for which table: ");
                    Scanner input_table_remove = new Scanner(System.in);
                    table_remove = input_table_remove.nextInt();

                    Scanner input_remove = new Scanner(System.in);
                    removeItem = input_remove.nextLine();
                    System.out.println("Removing Item from Order for Table " + table_remove);

                    addItemOrder = new RemoveItemOrderCommand(
                            restaurantManager.getSubManager("menuManager", MenuManager.class),
                            restaurantManager.getSubManager("orderManager", OrderManager.class)
                            , table_remove, removeItem
                    );

                case 4:
                    int table_print;
                    System.out.print("Print order for which table: ");
                    Scanner input_table_print = new Scanner(System.in);
                    table_print = input_table_print.nextInt();

                    printOrder = new PrintOrderCommand(
                            restaurantManager.getSubManager("orderManager", OrderManager.class)
                            , table_print
                    );
                    break;
                case 5:
                    int table_close;
                    System.out.print("Close order for which table: ");
                    Scanner input_table_close = new Scanner(System.in);
                    table_close = input_table_close.nextInt();

                    deleteOrder = new DeleteOrderCommand(
                            restaurantManager.getSubManager("orderManager", OrderManager.class)
                            , restaurantManager.getSubManager("salesRevenueManager", SalesRevenueManager.class)
                            , table_close
                    );
                    System.out.println("Table " + table_close + " is now vacant");
                    break;
                case 6:
                    System.out.println("Exiting from order console");
                default:
                    System.out.println("Invalid operation! Please enter again");
            }
        }while ( choice != 5);
        System.out.println("Enter your choice");
    }


}