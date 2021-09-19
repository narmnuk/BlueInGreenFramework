package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import utils.ConfigReader;
import utils.ScreenShot;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    public WebDriver driver;
    protected ExtentReports extentReports;
    protected ExtentTest extentTest;
    String configPath = "src/test/data/config/config.properties";

    @BeforeMethod
    public void setUpDriver(Method method) {

        browserDriver(ConfigReader.readProperty(configPath, "browser"));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(ConfigReader.readProperty(configPath, "url"));

        extentTest = extentReports.createTest(getTestName(method));
    }

    @AfterMethod
    public void tearDown() {

        driver.close();
    }

    @BeforeSuite
    public void startReporter() {

        extentReports = new ExtentReports();
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("reports.html");

        sparkReporter.config().setDocumentTitle("My Report");
        sparkReporter.config().setReportName("BIG Tests");
        extentReports.attachReporter(sparkReporter);
    }

    @AfterSuite
    public void closeReporter() {

        extentReports.flush();
    }

    public String getTestName(Method method) {

        Test testClass = method.getAnnotation(Test.class);
        if (testClass.testName().length() > 0)
            return testClass.testName();
        return method.getName();
    }

    public void logScreenShot(String title) {

        extentTest.info(title, MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenShot.takeScreenshot(driver)).build());
    }

//    public void ScreenShotPic(){
//
//        extentTest.info(MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenShot.takeScreenshot(driver)).build());
//    }

    public void browserDriver(String browser) {

        switch (browser) {
            case "Chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;

            case "Firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
        }
    }
}