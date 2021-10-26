package Unused;

import Utils.MenuBuilder;

public abstract class ConsoleDisplay {
    protected String[] options;
    protected String title;
    
    public int displayMenu() {
        System.out.println(MenuBuilder.buildMenu(title, options));

        return options.length;
    }
}
