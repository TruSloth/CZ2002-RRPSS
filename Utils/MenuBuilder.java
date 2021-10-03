package Utils;

public class MenuBuilder {
    private static String formatMenuTitle(int longestWidth, String title) {
        int width = longestWidth + 3;
        StringBuilder output = new StringBuilder();
        output.append("=".repeat(longestWidth + 5));
        output.append('\n');
        output.append(String.format("|%-" + width  + "s|\n", String.format("%" + (title.length() + (width - title.length()) / 2) + "s", title)));
        output.append("=".repeat(longestWidth + 5));
        output.append('\n');
        return output.toString();
    }


    private static String formatMenuOption(int longestWidth, int optionNumber, String option) {
        return String.format("|%d. %-" + longestWidth + "s|", optionNumber, option);
    }

    public static String buildMenu(int longestWidth, String title, String[] options) {
        StringBuilder menu = new StringBuilder();
        menu.append(formatMenuTitle(longestWidth, title));
        for (int i = 0; i < options.length; i++) {
            menu.append(formatMenuOption(longestWidth, i + 1, options[i]) + '\n');
        }
        menu.append("=".repeat(longestWidth + 5));
        return menu.toString();
    }
}
