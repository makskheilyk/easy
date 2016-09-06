package com.khe.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Maksym Kheilyk
 */
public final class Validation {

    private static final String regex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    private Validation() {
    }

    /**
     * This method checks the incoming url address with a template
     *
     * @param url url address, the page
     * @return {@code true} if the address has been checked
     */
    public static boolean validUrl(String url) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        return matcher.lookingAt();
    }
}