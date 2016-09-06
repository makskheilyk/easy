package com.khe.test;

import org.apache.log4j.Logger;

/**
 * @author Maksym Kheilyk
 */
public class Main {

    private final static Logger LOG = Logger.getLogger(Main.class);

    public static void main(String[] args) {

        Integer countPage = Integer.parseInt(args[0]);

        String url = "https://www.google.com.ua";

        if (countPage > 0) {
            Loader.load(countPage, url);
        } else {
            LOG.info("The page is not loaded.");
        }
    }
}