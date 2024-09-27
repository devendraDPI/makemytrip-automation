package xmmtsearch;

import java.io.File;
import java.time.Duration;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

public class TestCases {
    WebDriver driver;

    public TestCases() {
        System.out.println("Constructor: TestCases");
        System.out.println("Start Tests: TestCases");

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        // Set log level and type
        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");

        // Set path for log file
        File theDir = new File("logs");
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "logs" + File.separator + "chromedriver.log");

        driver = new ChromeDriver(options);

        // Implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public void endTest() {
        System.out.println("End Tests: TestCases");
        driver.quit();
    }

    /** <STRONG>Test Case 01</STRONG>: Verify Make My Trip Homepage URL <p>
     *  <STRONG>Description</STRONG>: Verify that the Make My Trip homepage URL contains "makemytrip" <p>
     *  <STRONG>Expected Result</STRONG>: The URL of the Make My Trip homepage contains "makemytrip" <p>
     *
     *  1. Launch chrome browser <p>
     *  2. Navigate to Make My Trip website https://www.makemytrip.com/ <p>
     *  3. Verify that the URL contains "makemytrip" <p>
     */
    public void testCase01() {
        System.out.println("\nTestCase01: START");
        // Launch chrome browser
        // Navigate to Make My Trip website https://www.makemytrip.com/
        driver.get("https://www.makemytrip.com");

        // Verify that the URL contains "makemytrip"
        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = "makemytrip";

        if (actualUrl.contains(expectedUrl)) {
            System.out.println("TestCase01: PASS");
        } else {
            System.out.println("TestCase01: FAIL");
        }

        System.out.println("TestCase01: END\n");
    }

    /** <STRONG>Test Case 02</STRONG>: Get Flight Details from Bangalore to New Delhi <p>
     *  <STRONG>Description</STRONG>: Get flight details for a specific route and date <p>
     *  <STRONG>Expected Result</STRONG>: The correct flight details are obtained and the price per adult is stored <p>
     *
     *  1. Launch chrome browser. <p>
     *  2. Navigate to Make My Trip website https://www.makemytrip.com/ <p>
     *  3. Select BLR (Bangalore) as the departure location. <p>
     *  4. Select DEL (New Delhi) as the arrival location. <p>
     *  5. Select the correct date (28th of next Month). <p>
     *  6. Click on the search button. <p>
     *  7. Store the flight price (per adult). <p>
     */
    public void testCase02() throws InterruptedException {
        System.out.println("\nTestCase02: START");
        // Launch chrome browser
        // Navigate to Make My Trip website https://www.makemytrip.com/
        driver.get("https://www.makemytrip.com");
        Thread.sleep(5000);

        // Close iframe popup
        try {
            WebElement iframe = driver.findElement(By.xpath("//iframe[contains(@title, 'notification')]"));
            driver.switchTo().frame(iframe);
            driver.findElement(By.xpath("//a[contains(@id, 'notification-close')]")).click();
            driver.switchTo().defaultContent();
        } catch (Exception e) {}

        // Close popup
        try {
            WebElement closeButton = driver.findElement(By.xpath("//span[contains(@class, 'commonModal__close')]"));
            closeButton.click();
        } catch (Exception e) {}

        // Click on Flights menu
        WebElement flightButton = driver.findElement(By.xpath("//li[contains(@data-cy, 'menu_Flights')]"));
        flightButton.click();
        Thread.sleep(5000);

        // Click on from button
        WebElement fromButton = driver.findElement(By.xpath("//input[contains(@id, 'fromCity')]"));
        fromButton.click();

        // Click on from text box
        WebElement fromTextBox = driver.findElement(By.xpath("//input[contains(@placeholder, 'From')]"));
        fromTextBox.sendKeys("blr");
        Thread.sleep(2000);

        // Select BLR (Bangalore) as the departure location.
        WebElement selectFromOption = driver.findElement(By.xpath("(//li[contains(@role, 'option')])[1]"));
        selectFromOption.click();
        Thread.sleep(2000);

        // Click on to button
        WebElement toButton = driver.findElement(By.xpath("//input[contains(@id, 'toCity')]"));
        toButton.click();

        // Click on to text box
        WebElement toTextBox = driver.findElement(By.xpath("//input[contains(@placeholder, 'To')]"));
        toTextBox.sendKeys("del");
        Thread.sleep(2000);

        // Select DEL (New Delhi) as the arrival location.
        WebElement selectToOption = driver.findElement(By.xpath("(//li[contains(@role, 'option')])[1]"));
        selectToOption.click();
        Thread.sleep(2000);

        // Click on calendar
        // WebElement calendar = driver.findElement(By.xpath("//label[contains(@for, 'departure')]"));
        // calendar.click();
        // Thread.sleep(2000);

        // Select the correct date (28th of next Month)
        WebElement date = driver.findElement(By.xpath("(//div[contains(@class, 'DayPicker-Month')])[3]//div[contains(@aria-label, '28')]"));
        date.click();

        // Click on the search button.
        WebElement searchButton = driver.findElement(By.xpath("//a[contains(@class, 'SearchBtn')]"));
        searchButton.click();
        Thread.sleep(5000);

        // Close popup
        try {
            WebElement closePayLaterButton = driver.findElement(By.xpath("//div[contains(@class, 'commonOverlay')]/span"));
            closePayLaterButton.click();
        } catch (Exception e) {}

        // Close popup: Flight comparison
        try {
            WebElement closeFlightComparisonButton = driver.findElement(By.xpath("//div[contains(@class, 'fliCompCoachmark')]/span"));
            closeFlightComparisonButton.click();
        } catch (Exception e) {}

        // Store the flight price (per adult).
        WebElement flightPrice = driver.findElement(By.xpath("(//div[contains(@class, 'clusterViewPrice')])[1]"));
        System.out.println("Flight Price: " + flightPrice.getText().replaceAll("[^0-9]", "") + " per adult");

        System.out.println("TestCase02: END\n");
    }

    /** <STRONG>Test Case 03</STRONG>: Get Train Details from Bangalore to New Delhi <p>
     *  <STRONG>Description</STRONG>: Get train details for a specific route and date <p>
     *  <STRONG>Expected Result</STRONG>: The correct train details are obtained <p>
     *
     *  1. Launch chrome browser. <p>
     *  2. Navigate to Make My Trip website https://www.makemytrip.com/ <p>
     *  3. Select YPR (Bangalore) as the departure location for the train. <p>
     *  4. Select NDLS (New Delhi) as the arrival location for the train. <p>
     *  5. Select the correct date (28th of next Month) for the train. <p>
     *  6. Select the class as 3AC <p>
     *  7. Click on the search button for the train. <p>
     *  8. Store the train price for 3AC. <p>
     */
    public void testCase03() throws InterruptedException {
        System.out.println("\nTestCase03: START");
        // Launch chrome browser
        // Navigate to Make My Trip website https://www.makemytrip.com/
        driver.get("https://www.makemytrip.com");
        Thread.sleep(5000);

        // Close iframe popup
        try {
            WebElement iframe = driver.findElement(By.xpath("//iframe[contains(@title, 'notification')]"));
            driver.switchTo().frame(iframe);
            driver.findElement(By.xpath("//a[contains(@id, 'notification-close')]")).click();
            driver.switchTo().defaultContent();
        } catch (Exception e) {}

        // Close popup
        try {
            WebElement closeButton = driver.findElement(By.xpath("//span[contains(@class, 'commonModal__close')]"));
            closeButton.click();
        } catch (Exception e) {}

        // Click on Trains menu
        WebElement trainButton = driver.findElement(By.xpath("//li[contains(@data-cy, 'menu_Trains')]"));
        trainButton.click();
        Thread.sleep(5000);

        // Click on from button
        WebElement fromButton = driver.findElement(By.xpath("//input[contains(@id, 'fromCity')]"));
        fromButton.click();

        // Click on from text box
        WebElement fromTextBox = driver.findElement(By.xpath("//input[contains(@placeholder, 'From')]"));
        fromTextBox.sendKeys("ypr");
        Thread.sleep(2000);

        // Select YPR (Bengaluru - All Stations) as the departure location for the train.
        WebElement selectFromOption = driver.findElement(By.xpath("(//li[contains(@role, 'option')])[1]"));
        selectFromOption.click();
        Thread.sleep(2000);

        // Click on to button
        if (!driver.findElement(By.xpath("//input[contains(@id, 'toCity')]")).isDisplayed()) {
            WebElement toButton = driver.findElement(By.xpath("//input[contains(@id, 'toCity')]"));
            toButton.click();
        }

        // Click on to text box
        WebElement toTextBox = driver.findElement(By.xpath("//input[contains(@placeholder, 'To')]"));
        toTextBox.sendKeys("ndls");
        Thread.sleep(2000);

        // Select NDLS (Delhi - All Stations) as the arrival location for the train.
        WebElement selectToOption = driver.findElement(By.xpath("(//li[contains(@role, 'option')])[1]"));
        selectToOption.click();
        Thread.sleep(2000);

        // Click on calendar
        if (!driver.findElement(By.xpath("//label[contains(@for, 'travelDate')]")).isDisplayed()) {
            WebElement calendar = driver.findElement(By.xpath("//label[contains(@for, 'travelDate')]"));
            calendar.click();
            Thread.sleep(2000);
        }

        // Select the correct date (29th of next Month) for the train.
        WebElement date = driver.findElement(By.xpath("(//div[contains(@class, 'DayPicker-Month')])[3]//div[contains(@aria-label, '28')]"));
        date.click();

        // Click on class
        if (!driver.findElement(By.xpath("//label[contains(@for, 'travelClass')]")).isDisplayed()) {
            WebElement trainClass = driver.findElement(By.xpath("//label[contains(@for, 'travelClass')]"));
            trainClass.click();
        }

        // Select the class as 3AC
        WebElement selectThreeAC = driver.findElement(By.xpath("//li[contains(@data-cy, '3A')]"));
        selectThreeAC.click();

        // Click on the search button for the train.
        WebElement searchButton = driver.findElement(By.xpath("//a[contains(@class, 'SearchBtn')]"));
        searchButton.click();
        Thread.sleep(5000);

        // Close popup
        // if (!driver.findElement(By.xpath("//div[contains(@class, 'commonOverlay')]/span")).isDisplayed()) {
        //     WebElement closePayLaterButton = driver.findElement(By.xpath("//div[contains(@class, 'commonOverlay')]/span"));
        //     closePayLaterButton.click();
        // }

        // Store the train price for 3AC.
        WebElement trainPrice = driver.findElement(By.xpath("(//div[contains(@class, 'ListingCard_listingTopInfo')]//p[contains(@class, 'Cards_total')])[1]"));
        System.out.println("Train Price: " + trainPrice.getText().replaceAll("[^0-9]", "") + " per adult");

        System.out.println("TestCase03: END\n");
    }

    /** <STRONG>Test Case 04</STRONG>: Verify that there are no buses from Bangalore to Kathmandu <p>
     *  <STRONG>Description</STRONG>: Verify that no buses are found for a specific route and date <p>
     *  <STRONG>Expected Result</STRONG>: The message "No buses found" is displayed for the specified route and date <p>
     *
     *  1. Launch chrome browser. <p>
     *  2. Navigate to Make My Trip website https://www.makemytrip.com/ <p>
     *  3. Select Bangalore (search for bangl) as the departure location for buses. <p>
     *  4. Select Kathmandu (search for kathma) as the arrival location for buses. <p>
     *  5. Select the correct date (28th of next Month) for buses. <p>
     *  6. Click on the search button for buses. <p>
     *  7. Verify that text displayed contains No buses found for 29th of next Month. <p>
     */
    public void testCase04() throws InterruptedException {
        System.out.println("\nTestCase04: START");
        // Launch chrome browser
        // Navigate to Make My Trip website https://www.makemytrip.com/
        driver.get("https://www.makemytrip.com");
        Thread.sleep(5000);

        // Close iframe popup
        try {
            WebElement iframe = driver.findElement(By.xpath("//iframe[contains(@title, 'notification')]"));
            driver.switchTo().frame(iframe);
            driver.findElement(By.xpath("//a[contains(@id, 'notification-close')]")).click();
            driver.switchTo().defaultContent();
        } catch (Exception e) {}

        // Close popup
        try {
            WebElement closeButton = driver.findElement(By.xpath("//span[contains(@class, 'commonModal__close')]"));
            closeButton.click();
        } catch (Exception e) {}

        // Click on Buses menu
        WebElement busButton = driver.findElement(By.xpath("//li[contains(@data-cy, 'menu_Buses')]"));
        busButton.click();
        Thread.sleep(2000);

        // Click on from button
        WebElement fromButton = driver.findElement(By.xpath("//input[contains(@id, 'fromCity')]"));
        fromButton.click();

        // Click on from text box
        WebElement fromTextBox = driver.findElement(By.xpath("//input[contains(@placeholder, 'From')]"));
        fromTextBox.sendKeys("bang");
        Thread.sleep(2000);

        // Select Bangalore (search for bang) as the departure location for buses.
        WebElement selectFromOption = driver.findElement(By.xpath("(//li[contains(@role, 'option')])[1]"));
        selectFromOption.click();
        Thread.sleep(2000);

        // Click on to button
        if (!driver.findElement(By.xpath("//input[contains(@id, 'toCity')]")).isDisplayed()) {
            WebElement toButton = driver.findElement(By.xpath("//input[contains(@id, 'toCity')]"));
            toButton.click();
        }

        // Click on to text box
        WebElement toTextBox = driver.findElement(By.xpath("//input[contains(@placeholder, 'To')]"));
        toTextBox.sendKeys("kathma");
        Thread.sleep(2000);

        // Select Kathmandu (search for kathma) as the arrival location for buses.
        WebElement selectToOption = driver.findElement(By.xpath("(//li[contains(@role, 'option')])[1]"));
        selectToOption.click();
        Thread.sleep(2000);

        // Click on calendar
        if (!driver.findElement(By.xpath("//label[contains(@for, 'travelDate')]")).isDisplayed()) {
            WebElement calendar = driver.findElement(By.xpath("//label[contains(@for, 'travelDate')]"));
            calendar.click();
            Thread.sleep(2000);
        }

        // Select the correct date (29th of next Month) for buses.
        WebElement date = driver.findElement(By.xpath("(//div[contains(@class, 'DayPicker-Month')])[3]//div[contains(@aria-label, '28')]"));
        date.click();

        // Click on the search button for buses.
        WebElement searchButton = driver.findElement(By.xpath("//button[contains(@class, 'SearchBtn')]"));
        searchButton.click();
        Thread.sleep(5000);

        // Verify that text displayed contains No buses found for 29th of next Month.
        String actualUrl = driver.findElement(By.xpath("//div[contains(@class, 'error-view')]/span")).getText();
        String expectedUrl = "No buses found for 28";

        if (actualUrl.contains(expectedUrl)) {
            System.out.println("TestCase04: PASS");
        } else {
            System.out.println("TestCase04: FAIL");
        }

        System.out.println("TestCase04: END\n");
    }
}
