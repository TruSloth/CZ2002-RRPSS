package com.CZ2002.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility class for formatting output intended to
 * be displayed as a menu in a command line interface.
 */
public class MenuBuilder {
    
    private static String formatMenuTitle(int longestWidth, String title) {
        // Returns a formatted menu title string
        // Used by buildMenu methods
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
        // Returns a formatted menu option string
        // Used by buildMenu methods for building a menu for displaying menu options and their option numbers
        return String.format("|%d. %-" + longestWidth + "s|", optionNumber, option);
    }

    private static String formatMenuOption(int longestWidth, String optionHeader, String option) {
        // Returns a formatted menu option string without option numbers
        // Used by buildMenu methods for building a menu for displaying information only
        if (optionHeader.length() + option.length() >= longestWidth) {
            option = fullJustify(option.split(" "), longestWidth);
            return String.format("| %-" + (longestWidth + 1) + "s |\n" + menuLineSpace(longestWidth + 4, ' ') + "%s", optionHeader, option);
        }
        return String.format("| %s%" + (longestWidth - optionHeader.length() + 1) + "s |", optionHeader, option);
    }

    
    /** 
     * Returns a formatted String that is fully justified
     * according to {@code maxWidth}.
     * 
     * @param words  the arrray of strings to be formatted
     * @param maxWidth  the maximum number of characters in a line
     * @return  the String after justification
     */
    public static String fullJustify(String[] words, int maxWidth) {
        // Returns a formatted string that is fully justified according to maxWidth
        int n = words.length;
        maxWidth += 1;
        List<String> justifiedText = new ArrayList<>();
        int currLineIndex = 0;
        int nextLineIndex = getNextLineIndex(currLineIndex, maxWidth, words);
        while (currLineIndex < n) {
            StringBuilder line = new StringBuilder();
            for (int i = currLineIndex; i < nextLineIndex; i++) {
                line.append(words[i] + " ");
            }
            currLineIndex = nextLineIndex;
            nextLineIndex = getNextLineIndex(currLineIndex, maxWidth, words);
            justifiedText.add(line.toString());
        }

        for (int i = 0; i < justifiedText.size() - 1; i++) {
            String fullJustifiedLine = getFullJustifiedString(justifiedText.get(i).trim(), maxWidth);
            justifiedText.remove(i);
            justifiedText.add(i, String.format("| %s |",fullJustifiedLine));
        }

        String leftJustifiedLine = getLeftJustifiedLine(justifiedText.get(justifiedText.size() - 1).trim(), maxWidth);
        justifiedText.remove(justifiedText.size() - 1);
        justifiedText.add(String.format("| %s |", leftJustifiedLine));
        return String.join("\n", justifiedText);
    }

    private static int getNextLineIndex(int currLineIndex, int maxWidth, String[] words) {
        int n = words.length;
        int width = 0;
        while (currLineIndex < n && width < maxWidth) {
            width += words[currLineIndex++].length() + 1;
        }
        if (width > maxWidth + 1)
            currLineIndex--;
        return currLineIndex;
    }

    private static String getFullJustifiedString(String line, int maxWidth) {
        StringBuilder justifiedLine = new StringBuilder();
        String[] words = line.split(" ");
        int occupiedCharLength = 0;
        for (String word : words) {
            occupiedCharLength += word.length();
        }
        int remainingSpace = maxWidth - occupiedCharLength;
        int spaceForEachWordSeparation = words.length > 1 ? remainingSpace / (words.length - 1) : remainingSpace;
        int extraSpace = remainingSpace - spaceForEachWordSeparation * (words.length - 1);
        for (int j = 0; j < words.length - 1; j++) {
            justifiedLine.append(words[j]);
            for (int i = 0; i < spaceForEachWordSeparation; i++)
                justifiedLine.append(" ");
            if (extraSpace > 0) {
                justifiedLine.append(" ");
                extraSpace--;
            }
        }

        justifiedLine.append(words[words.length - 1]);
        for (int i = 0; i < extraSpace; i++)
            justifiedLine.append(" ");
        return justifiedLine.toString();
    }

    private static String getLeftJustifiedLine(String line, int maxWidth) {
        int lineWidth = line.length();
        StringBuilder justifiedLine = new StringBuilder(line);
        for (int i = 0; i < maxWidth - lineWidth; i++)
            justifiedLine.append(" ");
        return justifiedLine.toString();
    }

    
    /**
     * Returns a formatted String that represents a
     * menu for displaying menu options.
     * <p>
     * Example Menu Output:
     * <pre>
     *
     * ================================
     * |         Reservations         |
     * ================================
     * |1. Create Reservation Booking |
     * |2. Check Reservation Booking  |
     * |3. Update Reservation Booking |
     * |4. Remove Reservation Booking |
     * |5. Back                       |
     * ================================
     * </pre>
     * 
     * @param title  the title of the menu
     * @param options  an array of strings representing the options displayed
     * @return  the menu as a formatted String
     */
    public static String buildMenu(String title, String[] options) {
        StringBuilder menu = new StringBuilder();
        int longestWidth = calculateLongestWidth(title, options);

        menu.append(formatMenuTitle(longestWidth, title));
        for (int i = 0; i < options.length; i++) {
            menu.append(formatMenuOption(longestWidth, i + 1, options[i]) + '\n');
        }
        menu.append("=".repeat(longestWidth + 5));
        return menu.toString();
    }

    
    /** 
     * Returns a formatted String that represents a
     * title for a menu.
     * 
     * @param title  the title of the menu
     * @param longestWidth  the width of the menu
     * @return  the menu as a formatted String
     */
    public static String buildMenu(String title, int longestWidth) {
        return formatMenuTitle(longestWidth, title);
    }

    
    /**
     * Returns a formatted String that represents a
     * menu for displaying menu options.
     * <p>
     * Example Menu Output:
     * <pre>
     *
     * ================================
     * |         Reservations         |
     * ================================
     * |1. Create Reservation Booking |
     * |2. Check Reservation Booking  |
     * |3. Update Reservation Booking |
     * |4. Remove Reservation Booking |
     * |5. Back                       |
     * ================================
     * </pre>
     * 
     * @param title  the title of the menu
     * @param options  an array of strings representing the options displayed
     * @param longestWidth  the width of the menu
     * @return  the menu as a formatted String
     * @see #buildMenu(String, String[])
     */
    public static String buildMenu(String title, String[] options, int longestWidth) {
        // Returns a formatted menu for displaying menu options as a string
        /* Example Menu Output
         *
         * ================================
         * |         Reservations         |
         * ================================
         * |1. Create Reservation Booking |
         * |2. Check Reservation Booking  |
         * |3. Update Reservation Booking |
         * |4. Remove Reservation Booking |
         * |5. Back                       |
         * ================================
         *
         *
         */

        StringBuilder menu = new StringBuilder();

        menu.append(formatMenuTitle(longestWidth, title));
        for (int i = 0; i < options.length; i++) {
            menu.append(formatMenuOption(longestWidth, i + 1, options[i]) + '\n');
        }
        menu.append("=".repeat(longestWidth + 5));
        return menu.toString();
    }

    
    /** 
     * Returns a formatted String that represents a
     * menu for displaying menu options without a title
     * <p>
     * Used for adding options to a menu without options.
     * <p>
     * Example Menu Output:
     * <pre>
     * 
     *  |1. Edit Name           |
     *  |2. Edit Price          |
     *  |3. Edit Description    |
     *  |4. Back                |
     *  =========================
     * </pre>
     * 
     * @param longestWidth  the width of the menu
     * @param options  the options to be displayed
     * @return  the menu as a formatted String
     */
    public static String buildMenu(int longestWidth, String[] options) {
        StringBuilder menu = new StringBuilder();

        for (int i = 0; i < options.length; i++) {
            menu.append(formatMenuOption(longestWidth, i + 1, options[i]) + '\n');
        }
        menu.append("=".repeat(longestWidth + 5));
        return menu.toString();
    }

    
    /** 
     * Returns a formatted String that represents a
     * menu for displaying information.
     * <p>
     * If a {@code option} length exceeds {@code longestWidth},
     * it will be considered a multiline option and be
     * justified accordingly.
     * <p>
     * Example Menu Output:
     *
     * <pre>
     * =========================
     * |         Steak         |
     * =========================
     * | Price          $30.50 |
     * |-----------------------|
     * | Description           |
     * |                       |
     * | Our   House   Classic |
     * | Tenderloin      Steak |
     * | since   1966.  Tender |
     * | eye   fillet   topped |
     * | with  white asparagus |
     * | and   black  mushroom |
     * | sauce                 |
     * =========================
     * </pre>
     *
     * @param title  the title of the menu
     * @param optionHeaders  the matching header names that identify the options
     * @param options  the options to be displayed
     * @param longestWidth  the width of the menu, beyond which a string will be justified
     * @return  the menu as a formatted String
     */
    public static String buildMenu(String title, String[] optionHeaders, String[] options, int longestWidth) {
        // Unlike BuildMenu above, this method does not automatically calculate the longestWidth and longestWidth
        // has to be provided.

        StringBuilder menu = new StringBuilder();

        menu.append(formatMenuTitle(longestWidth, title));
        for (int i = 0; i < options.length - 1; i++) {
            menu.append(formatMenuOption(longestWidth, optionHeaders[i], options[i]) + "\n");
            menu.append(menuLineSpace(longestWidth + 4, '-'));
        }
        menu.append(formatMenuOption(longestWidth, optionHeaders[optionHeaders.length - 1], options[options.length - 1]) + "\n");
        menu.append("=".repeat(longestWidth + 5));
        return menu.toString();
    }

    
    /** 
     * Returns a formatted String representing a
     * menu with option headers but without a title.
     * 
     * Used to append to a fully formatted menu to create a compound menu
     * 
     * @param longestWidth  the width of the menu
     * @param options  the options to be displayed
     * @param optionHeaders  the matching header names that identify the options
     * @param sepChar  the character, as a String, to be used as the ending line seperator
     * @return  the menu as a formatted String
     */
    public static String buildMenu(int longestWidth,String[] options, String[] optionHeaders, String sepChar) {
        // The ending line seperator may be specified using sepChar

        StringBuilder menu = new StringBuilder();

        for (int i = 0; i < options.length; i++) {
            menu.append(formatMenuOption(longestWidth, optionHeaders[i], options[i]) + '\n');
        }
        menu.append(sepChar.repeat(longestWidth + 5));
        return menu.toString();
    }

    private static String menuLineSpace(int width, char spaceCharacter) {
        // Returns a formatted string to function as a line space. The spaceCharacter can be specified.
        // Used by BuildMenu when creating a compound menu
        return String.format("|%" + width + "s\n", "|").replace(' ', spaceCharacter);
    }

    private static int calculateLongestWidth(String title, String[] options) {
        // Returns the length of the string (amongst the options and title)
        // Used by buildMenu to automatically calculate the longestWidth
        int longestWidth = 0;
        for (String option : options) {
            if (option.length() > longestWidth) {
                longestWidth = option.length();
            }
        }

        if (title.length() > longestWidth) {
            longestWidth = title.length();
        }

        return longestWidth + 1;
    }
}