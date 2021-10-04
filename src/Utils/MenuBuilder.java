package Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static String fullJustify(String[] words, int maxWidth) {
        int n = words.length;
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
            justifiedText.add(i, fullJustifiedLine);
        }
        String leftJustifiedLine = getLeftJustifiedLine(justifiedText.get(justifiedText.size() - 1).trim(), maxWidth);
        justifiedText.remove(justifiedText.size() - 1);
        justifiedText.add(leftJustifiedLine);
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

    private static int calculateLongestWidth(String title, String[] options) {
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
