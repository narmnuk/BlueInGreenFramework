package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ConfigReader;
import utils.Screeshot;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    private static final ThreadLocal<WebDriver> allDrivers = new ThreadLocal<>();
    private static final ThreadLocal<ExtentTest> allExtentTests = new ThreadLocal<>();
    private ExtentReports extentReports;
    private final String configPath = "src/test/data/config/config.properties";

    @BeforeMethod
    public void setUpDriver(Method method) {

        browserDriver(ConfigReader.readProperty(configPath, "browser"));
        getDriver().get(ConfigReader.readProperty(configPath, "url"));

        ExtentTest extentTest = extentReports.createTest(getTestName(method));
        allExtentTests.set(extentTest);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {

        if (result.getStatus() == ITestResult.SUCCESS) {
            getExtentTest().pass(MarkupHelper.createLabel("PASSED", ExtentColor.GREEN));
        } else if (result.getStatus() == ITestResult.FAILURE) {
            getExtentTest().fail(MarkupHelper.createLabel("FAILED", ExtentColor.RED));
            getExtentTest().fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            getExtentTest().fail(MarkupHelper.createLabel("SKIPPED", ExtentColor.ORANGE));
            getExtentTest().fail(result.getThrowable());
        }

        getDriver().quit();
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

    public void logScreenshotPic(String title) {

        getExtentTest().info(title, MediaEntityBuilder.createScreenCaptureFromBase64String(Screeshot.takeScreenshot(getDriver())).build());
    }

//    public void ScreenshotPic(){
//
//       getExtentTest().info(MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.takeScreenshot(getDriver())).build());
//    }

    public void browserDriver(String browser) {

        WebDriver driver = null;
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

        allDrivers.set(driver);
        getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        getDriver().manage().window().maximize();
    }

    public WebDriver getDriver() {

        return allDrivers.get();
    }

    public ExtentTest getExtentTest() {

        return allExtentTests.get();
    }
}