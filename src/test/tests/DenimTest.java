package tests;

import base.BaseTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.DenimPage;

public class DenimTest extends BaseTest {

    DenimPage denimPage;

    @BeforeMethod
    public void setUp() {

        denimPage = new DenimPage(getDriver());
    }

    @Test(testName = "Denim Test", description = "Verify Title & Print Text")
    public void test01() {

        getExtentTest().assignAuthor("Jackie Natt");
        getExtentTest().assignDevice("OS: Mac");
        denimPage.click(denimPage.denimBtn);
        denimPage.threadSleepMethod(3000);

        String expected = "Denim – BLUE IN GREEN SOHO";
        String actual = denimPage.getTitleMethod();

        if (actual.equals(expected)) {
            getExtentTest().pass(MarkupHelper.createLabel("PASSED: " + actual, ExtentColor.GREEN));
        } else {
            getExtentTest().fail(MarkupHelper.createLabel("FAILED: " + actual, ExtentColor.RED));
        }

        Assert.assertEquals(actual, expected);
        logScreenshotPic("Denim Page");

        System.out.println(denimPage.denimText.getText());
    }
}