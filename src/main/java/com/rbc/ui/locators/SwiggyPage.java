package com.rbc.ui.locators;

import keywords.Keywords;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.DownloadHelper;
import util.DriverManager;
import util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Driver;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class SwiggyPage extends BasePage {

    public SwiggyPage(WebDriver driver) {
        super(driver);
    }

    private static final String ROOT_FOLDER = System.getProperty("user.dir") + File.separator + "Swiggy";
    private static final String TODAY_DATE = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    private static final String TEMP_DOWNLOAD_PATH = ROOT_FOLDER + File.separator + "tempDownloads";


    Keywords keywords = new Keywords(driver);

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    @FindBy(tagName = "A")
    List<WebElement> allLinks;

    @FindBy(xpath = "//a[text()='Checkboxes']")
    WebElement linkCheckboxes;

    @FindBy(linkText = "Forgot Password")
    WebElement linkForgotPassword;

    @FindBy(name = "email")
    WebElement inputEmail;

    @FindBy(linkText = "Drag and Drop")
    WebElement linkDragAndDrop;

    @FindBy(id = "Enter Restaurant ID / Mobile number")
    WebElement inputEnterRestaurantID;

    @FindBy(xpath = "//div[text()='Continue']")
    WebElement btnContinue;

    @FindBy(xpath = "//div[text()='Login with Password']")
    WebElement btnLogInWithPassword;

    @FindBy(id = "Enter your password")
    WebElement inputEnterYourPassword;

    @FindBy(xpath = "//div[text()='Login']")
    WebElement btnLogIn;

    @FindBy(xpath = "//a[@class='business_line_switcher']")
    WebElement linkSwitchTo;

    @FindBy(xpath = ("//span[text()='Finance']"))
    WebElement linkFinance;

    @FindBy(xpath = ("//h2[text()='Finance']"))
    WebElement headerFinance;
    @FindBy(xpath = ("//span[text()='Change Outlet']/.."))
    WebElement linkChangeOutlet;

    @FindBy(xpath = ("//button[contains(text(),'Active')]/../..//h4"))
    WebElement txtActiveOutletNumber;

//    @FindBy (xpath = ("//div[contains(@class,'PayoutList__Wrapper')]//div[@tabindex='0'][1]//div[1]//div[text()][1]"))
//    WebElement txtLatestPayoutDate;

    @FindBy(xpath = ("//div[text()='Paid'][1]//ancestor::div[@tabindex][1]//div[1]//div[text()][1]"))
    WebElement txtLatestPayoutDate;

    @FindBy(xpath = ("//div[text()='Download payout reports']"))
    WebElement headerDownloadReports;

    @FindBy(xpath = ("//div[text()='Payout Annexures']"))
    WebElement reportPayoutAnnexuresLink;

    @FindBy(xpath = ("//div[text()='Tax invoices']"))
    WebElement reportTaxInvoicesLink;

    @FindBy(xpath = ("//div[text()='Payment receipts']"))
    WebElement reportPaymentReceiptsLink;

    @FindBy(xpath = ("//div[@class='mCSB_container']//ul//li//a//span[text()]"))
    List<WebElement> sideNavbars;
    @FindBy(xpath = ("//button[@class='No thanks']"))
    WebElement btnNotneeded;

    @FindBy(xpath = ("//img[@class='icon-close']"))
    WebElement iconClose;

    @FindBy(xpath = ("//div[@id='mCSB_1_scrollbar_vertical']//div[@class='mCSB_draggerContainer']"))
    WebElement verticalScrollbar;

    @FindBy(xpath = ("//button[contains(text(),'View')]"))
    WebElement btnView;

    @FindBy(xpath = ("//div[text()='Past Payouts']"))
    WebElement headerPastPayouts;

    @FindBy(xpath = ("//i[@class='fa fa-search']/.."))
    WebElement outletSearchIcon;

    @FindBy(xpath = ("//i[@class='fa fa-search']/..//following-sibling::div//input"))
    WebElement inputOutletSearch;


    public void clickLinkCheckboxes() {

        wait.until(ExpectedConditions.elementToBeClickable(linkCheckboxes));
        keywords.scrollToElement(linkCheckboxes);
        System.out.println("click checkbox");
        wait.until(ExpectedConditions.elementToBeClickable(linkCheckboxes));
        keywords.highlightElement(linkCheckboxes);
        linkCheckboxes.click();
    }

    public void clickForgotLink() {
//        keywords.scrollToElement(linkForgotPassword);
        wait.until(ExpectedConditions.elementToBeClickable(linkForgotPassword));
//        keywords.highlightElement(linkForgotPassword);
        linkForgotPassword.click();
        System.out.println("After click frogto");
    }

    public void enterEmailInput() {
        System.out.println("Before enter");
        keywords.clearAndType(inputEmail, "Mahesh");
        System.out.println("After enter");
    }

    public void findBrokenLinks() throws IOException {
        String url = "";
        for (WebElement links : allLinks) {
            url = links.getAttribute("href");

            if (url == null || url.isEmpty()) {
                System.out.println(url + " does not contains a proper link.");
                continue;
            }
            HttpURLConnection httpsURLConnection = (HttpURLConnection) (new URL(url)).openConnection();
            httpsURLConnection.connect();

            if (httpsURLConnection.getResponseCode() >= 400) {
                System.out.println(url + " is broken.");
            } else {
                System.out.println(url + " is a valid url.");
            }

        }
    }

    public void clickDragAndDrop() {
        keywords.clickElement(linkDragAndDrop);
    }

    public void loginToSwiggy() {
        try {
            String restaurantID = FileUtil.getPropValue("swiggy_restaurantID");
            String password = FileUtil.getPropValue("swiggy_password");
//            keywords.clearAndType(inputEnterRestaurantID, restaurantID);
            inputEnterRestaurantID.sendKeys(restaurantID);
//            keywords.clickElement(btnLogInWithPassword);
            keywords.isDisplayedAndEnabled(btnContinue);

            keywords.clickElement(btnContinue);
            keywords.clickElement(btnLogInWithPassword);
//            keywords.clearAndType(inputEnterYourPassword, password);
            inputEnterYourPassword.sendKeys(password);
            keywords.isDisplayedAndEnabled(btnLogIn);
            keywords.clickElement(btnLogIn);
            Alert alert = driver.switchTo().alert();
            alert.dismiss();
            keywords.clickElement(btnNotneeded);
//           boolean isVisible = keywords.isDisplayedAndEnabled(iconClose);
//           if(isVisible){
//               keywords.clickElement(iconClose);
//           }


        } catch (Exception e) {
            System.out.println("Exception occurred " + e.getMessage() + " in loginToSwiggy method");
        }
    }

    public void switchToDelivery() {
        try {
            String businessLine = keywords.getAttributeValue(linkSwitchTo);
            if (businessLine.equals("Switch to Delivery")) {
                keywords.clickElement(linkSwitchTo);
            } else {
                System.out.println("User is on Delivery business");
            }

            // Use JavaScript to scroll to bottom of that container
            JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
            js.executeScript(
                    "document.getElementById('mCSB_1_container').style.top = '-9999px';"
            );
            Thread.sleep(1000);


        } catch (Exception e) {
            System.out.println("Exception occurred " + e.getMessage() + " in switchToDelivery method");
        }
    }

    public void navigateToFinance() {
        boolean result;
        try {
//            keywords.scrollTo(sideNavbars,"Finance");

            keywords.clickElement(linkFinance);
            wait.until(ExpectedConditions.visibilityOf(headerFinance));
            String financeHeader = keywords.getText(headerFinance);
//
//            result = (financeHeader.equals("Finance"));
//
//            return result;

        } catch (Exception e) {
            System.out.println("Exception occurred " + e.getMessage() + " in navigateToFinance method");
            throw new RuntimeException(e.getMessage());
        }
    }

    public String getActiveOutletNumber() {
        String activeOutletNumber;
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
        try {
            System.out.println("Inside get ActiveOutlet Number method : ");


//            boolean isVisible = keywords.isDisplayedAndEnabled(iconClose);
            boolean isNotNeededVisible = keywords.isDisplayedAndEnabled(btnNotneeded);
            if (isNotNeededVisible) {
                System.out.println("Inside isNotNeededVisible Condition : ");
                Thread.sleep(1000);
                keywords.clickElement(btnNotneeded);
            }

//            if(isVisible){
//                System.out.println("Inside isVisible Condition : ");
//                Thread.sleep(1000);
//                keywords.clickElement(iconClose);
//            }

            wait.until(ExpectedConditions.visibilityOf(linkChangeOutlet));
            keywords.clickElement(linkChangeOutlet);
            wait.until(ExpectedConditions.visibilityOf(txtActiveOutletNumber));
            activeOutletNumber = keywords.getText(txtActiveOutletNumber);
            System.out.println("activeOutletNumber : " + activeOutletNumber);
            keywords.clickElement(linkChangeOutlet);
            return activeOutletNumber;

        } catch (Exception e) {
            System.out.println("Exception occurred " + e.getMessage() + " in navigateToFinance method");
            throw new RuntimeException(e.getMessage() + " in navigateToFinance method");
        }
    }

    public void downloadReports() throws InterruptedException {
        try {
            String activeOutletNumber;
//            List<String> outletList = new ArrayList<>(Arrays.asList("242994", "326208","340484","355463","400603","423202","438553","455650","486475","513960","568186",
//                    "587316","644871","646056","651675","746378","756637","779863","781328","781337"));

            List<String> outletList = new ArrayList<>(Arrays.asList("242994", "326208","340484","355463","400603"));

            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(20));
//            String activeOutLetNumber = getActiveOutletNumber();
//            System.out.println("activeOutLetNumber : " + activeOutLetNumber);

            boolean isNotNeededVisible = keywords.isDisplayedAndEnabled(btnNotneeded);
            if (isNotNeededVisible) {
                System.out.println("Inside isNotNeededVisible Condition : ");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                keywords.clickElement(btnNotneeded);
            }

            for (String outlet : outletList) {
                System.out.println("Outlet_" + outlet);
                WebElement changeOutlet = DriverManager.getDriver().findElement(By.xpath("//span[text()='Change Outlet']/.."));
                wait.until(ExpectedConditions.visibilityOf(changeOutlet));
                keywords.clickElement(changeOutlet);
                wait.until(ExpectedConditions.visibilityOf(txtActiveOutletNumber));
                keywords.clickElement(outletSearchIcon);
                keywords.clickElement(inputOutletSearch);
                inputOutletSearch.clear();
                inputOutletSearch.sendKeys(outlet);
                keywords.clickElement(btnView);
                Thread.sleep(4000);

                DriverManager.getDriver().switchTo().frame("mfe-frame");
                Thread.sleep(1000);
                System.out.println("After switching into the frame ");
                wait.until(ExpectedConditions.visibilityOf(txtLatestPayoutDate));
                String innerHTML = keywords.getInnerHTML(txtLatestPayoutDate);
                System.out.println("Latest Payout date of outlet_"+outlet+" : "+ innerHTML);

                keywords.clickElement(txtLatestPayoutDate);

                keywords.getText(headerDownloadReports);
                keywords.clickElement(reportPayoutAnnexuresLink);
                Thread.sleep(4000);
                keywords.clickElement(reportTaxInvoicesLink);
                Thread.sleep(3000);
                keywords.clickElement(reportPaymentReceiptsLink);
                Thread.sleep(2000);
                DownloadHelper.waitForDownloads(TEMP_DOWNLOAD_PATH,3,30);

//                DownloadHelper.waitForDownloads(TEMP_DOWNLOAD_PATH,3,30);
                DownloadHelper.moveDownloadedFiles(outlet, innerHTML);
                DriverManager.getDriver().switchTo().defaultContent();
            }
        } catch (Exception e) {
            System.out.println("Exception occurred : " + e.getMessage());
        }


    }


}
