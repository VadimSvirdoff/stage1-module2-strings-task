package com.epam.mjc;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

public class StringSplitter {

    /**
     * Splits given string applying all delimeters to it. Keeps order of result substrings as in source string.
     *
     * @param source source string
     * @param delimiters collection of delimiter strings
     * @return List of substrings
     */
    public List<String> splitByDelimiters(String source, Collection<String> delimiters) {
        List<String> substrings = new ArrayList<>();

        // Start index for substring extraction
        int startIndex = 0;

        // Iterate through the source string
        for (int i = 0; i < source.length(); i++) {
            // Check if any delimiter matches at the current index
            for (String delimiter : delimiters) {
                if (source.startsWith(delimiter, i)) {
                    // Extract the substring from startIndex to i
                    String substring = source.substring(startIndex, i);
                    if (!substring.isEmpty()) {
                        substrings.add(substring);
                    }

                    // Update the startIndex for the next substring extraction
                    startIndex = i + delimiter.length();
                    i = startIndex - 1; // Decrement i to recheck the current character
                    break;
                }
            }
        }

        // Add the remaining substring after the last delimiter
        String lastSubstring = source.substring(startIndex);
        if (!lastSubstring.isEmpty()) {
            substrings.add(lastSubstring);
        }

        return substrings;
    }
}
