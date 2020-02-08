package com.epam.lab.view;


import org.apache.log4j.Logger;

public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class);

/*
    static {
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
    }
*/

    public static void main(String[] args) {
        try {
            Application application = new Application();
            application.start();
        } catch (Exception e) {
            LOG.error("Error happen", e);
            e.printStackTrace();
        }
    }
}
