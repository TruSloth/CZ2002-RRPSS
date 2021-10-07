package Display;

import Utils.MenuBuilder;

public abstract class ConsoleDisplay {
    String[] options;
    String title;
    
    public int displayMenu() {
        System.out.println(MenuBuilder.buildMenu(title, options));

        return options.length;
    }
}
