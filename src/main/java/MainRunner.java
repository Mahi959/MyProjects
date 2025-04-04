
import listeners.GlobalRetryListener;
import org.testng.TestNG;
import org.testng.xml.*;

import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.*;

public class MainRunner {

    public static final String ENVIRONMENT = "env";
    public static final String BROWSER = "browser";
    public static final String BROWSER_HEADLESS_MODE = "browser.headless.mode";
    public static final String USE_SELENIUM_GRID = "useSeleniumGrid";
    public static int threads = 1;
//    public static String RETRY_ATTEMPTS = "failedRetryAttempts";
    public static int failedRetryAttempts = 2;
    //    public static
    public static String TAGS = "tags";

    public static void main(String[] args) {
        String tag = "Mahesh";
        String browserHeadlessMode = "false";
        String env = Environment.QA.toString();
        String browserType = Browsers.chrome.toString().toLowerCase();
        String useSeleniumGrid = "N";
//        String failedRetryAttempts = "2";

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

        //Set the values globally
        System.setProperty(TAGS, tag);
        System.setProperty(ENVIRONMENT, env);
        System.setProperty(BROWSER, browserType);
        System.setProperty(BROWSER_HEADLESS_MODE, browserHeadlessMode);
        System.setProperty(USE_SELENIUM_GRID, useSeleniumGrid);
        System.setProperty("failedRetryAttempts",String.valueOf(failedRetryAttempts));

        /*
         * Runs the testNG suite with group names mentioned in testNG suite
         */
//         Create a TestNG instance and run the tests
//        TestNG testNG = new TestNG();
//        List<String> suites = new ArrayList<>();
//        suites.add("./testng.xml");
//        testNG.setTestSuites(suites);
//        testNG.run();

        // Create TestNG instance
        TestNG testNG = new TestNG();

        // Create an XmlSuite instance
        XmlSuite suite = new XmlSuite();
        suite.setName("DynamicTestSuite");

        // Set the Parallel execution mode dynamically
//        suite.setParallel(XmlSuite.ParallelMode.TESTS);

        suite.setThreadCount(1); // Force single-threaded execution

        testNG.addListener(new GlobalRetryListener());

        // Create an XmlTest instance
        XmlTest jsTest = new XmlTest(suite);
        jsTest.setName("DynamicTest");
//        jsTest.setPreserveOrder(true);

        // Create an XmlClass instance
        XmlClass jsClass = new XmlClass("com.rbc.ui.testCases.TestJavascriptActions");
        XmlClass class2 = new XmlClass("com.rbc.ui.testCases.TestActionClass");
        jsTest.setXmlClasses(Arrays.asList(jsClass,class2));

        // Second Test class for parallel execution
//        XmlTest test1 = new XmlTest(suite);
//        test1.setName("DynamicTest1");
//        XmlClass class2 = new XmlClass("com.rbc.ui.testCases.TestClass1");
//        test1.setXmlClasses(Arrays.asList(class2));

        // Include the group in the test
        jsTest.addIncludedGroup(tag);
//        test1.addIncludedGroup(tag);

        // Add both tests to the suite
//        suite.setTests(Arrays.asList(jsTest, test1));
        suite.setTests(Arrays.asList(jsTest));

//      testNG.setXmlSuites(Arrays.asList(suite));
        testNG.setXmlSuites(List.of(suite));

        // Run the suite
        testNG.run();

    }

}
