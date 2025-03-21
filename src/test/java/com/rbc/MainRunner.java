package com.rbc;

import com.rbc.ui.testCases.TestClass;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.LoggerFactory;
import org.testng.TestNG;
import org.testng.internal.XmlMethodSelector;
import org.testng.xml.*;
//import org.testng.xml.internal.Parser;
//import org.testng.xml.XmlMethodSelector;
//import org.testng.xml.Parser;

import java.io.File;
import java.util.Arrays;
import java.util.*;
import java.util.logging.Logger;

import static org.testng.TestNG.*;


import org.testng.TestNG;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import org.testng.xml.XmlGroups;
import org.testng.xml.XmlRun;
import org.testng.xml.XmlInclude;

import java.util.ArrayList;
import java.util.List;

public class MainRunner {

    public static final String ENVIRONMENT = "env";
    public static final String BROWSER = "browser";
    public static final String BROWSER_HEADLESS_MODE = "browser.headless.mode";
    public static final String USE_SELENIUM_GRID = "useSeleniumGrid";
    public static int threads = 1;
    public static int failedRetryAttempts = 2;
    public static String TAGS = "tags";

    public static void main(String[] args) {
        String tag = "sanity";
        String browserHeadlessMode = "false";
        String env = Environment.QA.toString();
        String browserType = Browsers.chrome.toString().toLowerCase();
        String useSeleniumGrid = "N";


        for (int i = 0; i < args.length; i++) {
            switch (i) {
                case CommandLineConstants.NUMBER_ZERO -> {
                    tag = args[CommandLineConstants.NUMBER_ZERO];
                    System.out.println("tags : " + tag);
                }
                case CommandLineConstants.NUMBER_ONE -> {
                    env = args[CommandLineConstants.NUMBER_ONE];
                    System.out.println("Env : " + env);
                }
                case CommandLineConstants.NUMBER_TWO -> {
                    threads = Integer.parseInt(args[CommandLineConstants.NUMBER_TWO]);
                    System.out.println("Threads : " + threads);
                }
                case CommandLineConstants.NUMBER_THREE -> {
                    browserType = args[CommandLineConstants.NUMBER_THREE];
                    System.out.println("Browser : " + browserType);
                }
                case CommandLineConstants.NUMBER_FOUR -> {
                    browserHeadlessMode = args[CommandLineConstants.NUMBER_FOUR];
                    System.out.println("Browser headless mode : " + browserHeadlessMode);
                }
                case CommandLineConstants.NUMBER_FIVE -> {
                    failedRetryAttempts = Integer.parseInt(args[CommandLineConstants.NUMBER_FIVE]);
                    System.out.println("Failed Retry Count : " + failedRetryAttempts);
                }
                case CommandLineConstants.NUMBER_SIX -> {
                    useSeleniumGrid = args[CommandLineConstants.NUMBER_SIX];
                    System.out.println("Use Selenium Grid : " + useSeleniumGrid);
                }

                default ->
                        System.out.println("WARNING: unknown args position(0 base index ): " + i + "values: " + args[i]);

            }
        }

        System.setProperty(TAGS, tag);
        System.setProperty(ENVIRONMENT, env);
        System.setProperty(BROWSER, browserType);
        System.setProperty(BROWSER_HEADLESS_MODE, browserHeadlessMode);
        System.setProperty(USE_SELENIUM_GRID, useSeleniumGrid);

        // Create a TestNG instance and run the tests
//        TestNG testNG = new TestNG();
//        List<String> suites = new ArrayList<>();
//        suites.add(".//src//test//testng.xml"); // Reference to your TestNG XML or define it dynamically
//        testNG.setTestSuites(suites);
//        testNG.run();


        // Create an XmlSuite instance
        String groupToInclude = "dynamicGroup1";
        XmlSuite suite = new XmlSuite();
        suite.setName("DynamicTestSuite");

        // Create an XmlTest instance
        XmlTest test = new XmlTest(suite);
        test.setName("DynamicTest");
        test.setXmlClasses(Collections.singletonList(new org.testng.xml.XmlClass("com.rbc.ui.testCases.TestClass")));

        // Include the group in the test
        test.addIncludedGroup(tag);
//      test.addIncludedGroup(groupToInclude);

        // Add the test to the suite
        suite.setTests(Collections.singletonList(test));

        // Create TestNG instance
        TestNG testNG = new TestNG();
        testNG.setXmlSuites(Collections.singletonList(suite));

        // Run the suite
        testNG.run();

    }

}
